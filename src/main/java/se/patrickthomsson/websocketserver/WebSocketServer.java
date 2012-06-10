package se.patrickthomsson.websocketserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import se.patrickthomsson.websocketserver.connection.Connection;
import se.patrickthomsson.websocketserver.connection.ConnectionManager;
import se.patrickthomsson.websocketserver.exception.ConnectionClosedException;
import se.patrickthomsson.websocketserver.exception.WebSocketServerException;
import se.patrickthomsson.websocketserver.handshake.Handshaker;
import se.patrickthomsson.websocketserver.protocol.CommunicationProtocol;
import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;

public class WebSocketServer implements Server {

    private static final Logger LOG = Logger.getLogger(WebSocketServer.class);
    private static final ApplicationContext context = new ClassPathXmlApplicationContext(
            "application-context.xml");
    private static final int DEFAULT_PORT = 8082;
    private static int port;

    private ConnectionManager connectionManager = context
            .getBean(ConnectionManager.class);
    private Communicator communicator = context.getBean(Communicator.class);
    private Handshaker handshaker = context.getBean(Handshaker.class);

    private CommunicationProtocol protocol;

    public WebSocketServer(CommunicationProtocol protocol) {
        this(protocol, DEFAULT_PORT);
    }

    public WebSocketServer(CommunicationProtocol protocol, int port) {
        this.protocol = protocol;
        WebSocketServer.port = port;
    }

    public static int getPort() {
        return port;
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.patrickthomsson.websocketserver.Server#start()
     */
    @Override
    public void start() throws IOException {
        Selector selector = Selector.open();
        ServerSocket serverSocket = configureServerSocketChannel(selector);
        LOG.info("Listening on port " + port);

        while (true) {
            if (selector.select() == 0) {
                continue;
            }
            Set<SelectionKey> keys = selector.selectedKeys();

            for (SelectionKey selectionKey : keys) {
                switch (selectionKey.readyOps()) {
                case SelectionKey.OP_ACCEPT:
                    openConnection(serverSocket, selector);
                    break;
                case SelectionKey.OP_READ:
                    communicate(selectionKey);
                    break;
                }
            }

            keys.clear();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * se.patrickthomsson.websocketserver.Server#processResponse(se.patrickthomsson
     * .websocketserver.protocol.Response)
     */
    @Override
    public void processResponse(Response response) {
        Collection<Connection> receivers = connectionManager
                .getConnections(response.getReceiverIds());
        try {
            communicator.sendResponse(response, receivers);
        } catch (IOException e) {
            LOG.error("Failed to send response", e);
            throw new WebSocketServerException("Failed to send response", e);
        }
    }

    private ServerSocket configureServerSocketChannel(Selector selector)
            throws IOException, ClosedChannelException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);

        ServerSocket serverSocket = channel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        return serverSocket;
    }

    private void openConnection(ServerSocket serverSocket, Selector selector)
            throws IOException {
        Socket socket = acceptConnection(serverSocket, selector);
        Connection connection = connectionManager.addConnection(socket);
        protocol.addConnection(connection.getId());
    }

    private Socket acceptConnection(ServerSocket serverSocket, Selector selector)
            throws IOException {
        Socket socket = serverSocket.accept();
        LOG.info("Got connection from " + socket);
        configureSocketChannel(socket.getChannel(), selector);
        return socket;
    }

    private void configureSocketChannel(SocketChannel socketChannel,
            Selector selector) throws IOException, ClosedChannelException {
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void communicate(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        Connection connection = connectionManager.getConnection(socketChannel
                .socket());
        LOG.debug("Communicating with connection: " + connection);

        if (connection.isAwaitingHandshake()) {
            handshaker.initiateConnection(socketChannel);
            connection.open();
        } else if (connection.isOpen()) {
            handleRequest(selectionKey, connection);
        } else {
            throw new WebSocketServerException("Connection is closed");
        }
    }

    private void handleRequest(SelectionKey selectionKey, Connection connection) {
        try {
            Request request = communicator.readRequest(connection);
            protocol.processRequest(request);
        } catch (ConnectionClosedException e) {
            LOG.info("Connection was closed. Removing connection with id: "
                    + connection.getId());
            selectionKey.cancel();
            closeConnection(connection);
        }
    }

    private void closeConnection(Connection connection) {
        connectionManager.removeConnection(connection.getSocket());
        protocol.removeConnection(connection.getId());
        connection.close();
    }

}
