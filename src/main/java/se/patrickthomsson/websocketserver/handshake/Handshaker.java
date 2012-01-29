package se.patrickthomsson.websocketserver.handshake;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import se.patrickthomsson.websocketserver.WebSocketIO;

public class Handshaker {
	
	private WebSocketIO communicator;
	private RequestParser handshakeRequestParser;
	private HandshakeResolver handshakeResolver;
	
	public Handshaker() {
		communicator = new WebSocketIO();
		handshakeRequestParser = new RequestParser();
		handshakeResolver = new HandshakeResolver();
	}
	
	public void initiateConnection(SocketChannel socketChannel) throws IOException {
		byte[] handshakeRequestBytes = communicator.read(socketChannel);
		
		HandshakeRequest handshakeRequest = handshakeRequestParser.parse(handshakeRequestBytes);
		HandshakeResponse response = handshakeResolver.buildResponse(handshakeRequest);
		
		communicator.write(response.getMessage().getBytes(), socketChannel);
	}

}
