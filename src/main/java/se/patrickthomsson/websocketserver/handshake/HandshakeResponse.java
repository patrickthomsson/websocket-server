package se.patrickthomsson.websocketserver.handshake;

import se.patrickthomsson.websocketserver.message.Message;

public class HandshakeResponse extends Message {

	public HandshakeResponse(String message) {
		super(message);
	}

}
