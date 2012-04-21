package se.patrickthomsson.websocketserver;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.patrickthomsson.websocketserver.connection.Connection;
import se.patrickthomsson.websocketserver.exception.ConnectionClosedException;
import se.patrickthomsson.websocketserver.frame.Frame;
import se.patrickthomsson.websocketserver.frame.FrameBuilder;
import se.patrickthomsson.websocketserver.frame.FrameInterpreter;
import se.patrickthomsson.websocketserver.frame.FrameType;
import se.patrickthomsson.websocketserver.message.FrameRequest;
import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;

@Service
public class Communicator {

	private static final Logger LOG = Logger.getLogger(Communicator.class);
	
	@Autowired
	private WebSocketIO webSocketIO;
	@Autowired
	private FrameBuilder frameBuilder;
	@Autowired
	private FrameInterpreter frameInterpreter;

	public Request readRequest(Connection connection) throws ConnectionClosedException {
		SocketChannel socketChannel = connection.getSocket().getChannel();
		byte[] requestBytes = webSocketIO.read(socketChannel);

		if(requestBytes.length > 0) {
			Frame frame = frameInterpreter.interpret(requestBytes);
			if(frame.getType() == FrameType.CONNECTION_CLOSE) {
				throw new ConnectionClosedException("Received CONNECTION_CLOSE frame.");
			}
			return new FrameRequest(frame, connection.getId());
		}
		throw new ConnectionClosedException("Empty input from connection, assuming connection closed.");
	}

	public void sendResponse(Response response, Collection<Connection> receivers) throws IOException {
		if (!receivers.isEmpty()) {
			Frame maskedFrame = frameBuilder.buildMaskedFrame(response.getMessage());
			for (Connection receiver : receivers) {
				SocketChannel socketChannel = receiver.getSocket().getChannel();
				webSocketIO.write(maskedFrame.getRawFrame(), socketChannel);
			}
			LOG.debug(String.format("Outgoing frame: [%s] sent to [%s]", maskedFrame.toString(), receivers.toString()));
		}
	}

}
