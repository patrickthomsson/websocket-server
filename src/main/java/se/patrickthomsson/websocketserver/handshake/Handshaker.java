package se.patrickthomsson.websocketserver.handshake;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.patrickthomsson.websocketserver.WebSocketIO;

@Service
public class Handshaker {
	
	@Autowired
	private WebSocketIO communicator;
	@Autowired
	private RequestParser handshakeRequestParser;
	@Autowired
	private HandshakeResolver handshakeResolver;
	
	public void initiateConnection(SocketChannel socketChannel) throws IOException {
		byte[] handshakeRequestBytes = communicator.read(socketChannel);
		
		HandshakeRequest handshakeRequest = handshakeRequestParser.parse(handshakeRequestBytes);
		HandshakeResponse response = handshakeResolver.buildResponse(handshakeRequest);
		
		communicator.write(response.getMessage().getBytes(), socketChannel);
	}

}
