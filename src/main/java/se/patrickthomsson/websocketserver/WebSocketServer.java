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

import se.patrickthomsson.websocketserver.connection.Connection;
import se.patrickthomsson.websocketserver.connection.ConnectionManager;
import se.patrickthomsson.websocketserver.handshake.Handshaker;
import se.patrickthomsson.websocketserver.protocol.CommunicationProtocol;
import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;
import se.patrickthomsson.websocketserver.protocol.simplechat.SimpleChatProtocol;

public class WebSocketServer {

	private static final Logger LOG = Logger.getLogger(WebSocketServer.class);

	public static int port = 8082;

	private Communicator communicator;
	private ConnectionManager connectionManager;
	private Handshaker handshaker;

	private CommunicationProtocol protocol;

	private WebSocketServer(CommunicationProtocol protocol) {
		this.protocol = protocol;
		connectionManager = new ConnectionManager();
		communicator = new Communicator();
		handshaker = new Handshaker();
	}

	public static void main(String[] args) throws IOException {
		LOG.info("Starting WebSocketServer...");
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-port")) {
				port = Integer.parseInt(args[++i]);
			}
		}
		new WebSocketServer(new SimpleChatProtocol()).start();
	}

	private void start() throws IOException {
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

	private ServerSocket configureServerSocketChannel(Selector selector) throws IOException, ClosedChannelException {
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_ACCEPT);
		
		ServerSocket serverSocket = channel.socket();
		serverSocket.bind(new InetSocketAddress(port));
		
		return serverSocket;
	}
	
	private void openConnection(ServerSocket serverSocket, Selector selector) throws IOException {
		Socket socket = acceptConnection(serverSocket, selector);
		Connection connection = connectionManager.addConnection(socket);
		protocol.addConnection(connection.getId());
	}

	private Socket acceptConnection(ServerSocket serverSocket, Selector selector) throws IOException {
		Socket socket = serverSocket.accept();
		LOG.info("Got connection from " + socket);
		configureSocketChannel(socket.getChannel(), selector);
		return socket;
	}

	private void configureSocketChannel(SocketChannel socketChannel, Selector selector) throws IOException, ClosedChannelException {
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);
	}

	private void communicate(SelectionKey selectionKey) throws IOException {
		SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
		Connection connection = connectionManager.getConnection(socketChannel.socket());
		LOG.debug("Communicating with connection: " + connection);
		
		if (connection.isAwaitingHandshake()) {
			handshaker.initiateConnection(socketChannel);
			connection.open();
		} else if (connection.isOpen()) {
			Request request = communicator.readRequest(connection);
			Response response = protocol.respond(request);
			Collection<Connection> receivers = connectionManager.getConnections(response.getReceiverIds());
			communicator.sendResponse(response, receivers);
		}
	}

}
