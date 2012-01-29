package se.patrickthomsson.websocketserver;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.patrickthomsson.websocketserver.connection.Connection;
import se.patrickthomsson.websocketserver.frame.Frame;
import se.patrickthomsson.websocketserver.frame.FrameBuilder;
import se.patrickthomsson.websocketserver.frame.FrameInterpreter;
import se.patrickthomsson.websocketserver.message.FrameRequest;
import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;

@Service
public class Communicator {

	@Autowired
	private WebSocketIO webSocketIO;
	
	@Autowired
	private FrameBuilder frameBuilder;

	public Request readRequest(Connection connection) {
		SocketChannel socketChannel = connection.getSocket().getChannel();
		byte[] requestBytes = webSocketIO.read(socketChannel);

		Frame frame = new FrameInterpreter().interpret(requestBytes);
		FrameRequest frameRequest = new FrameRequest(frame, connection.getId());

		return frameRequest;
	}

	public void sendResponse(Response response, Collection<Connection> receivers) throws IOException {
		Frame maskedFrame = frameBuilder.buildMaskedFrame(response.getMessage());
		for(Connection receiver : receivers) {
			SocketChannel socketChannel = receiver.getSocket().getChannel();
			webSocketIO.write(maskedFrame.getRawFrame(), socketChannel);
		}
	}

}
