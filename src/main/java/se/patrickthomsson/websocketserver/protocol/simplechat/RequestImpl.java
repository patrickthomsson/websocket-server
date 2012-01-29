package se.patrickthomsson.websocketserver.protocol.simplechat;

import se.patrickthomsson.websocketserver.protocol.Request;

public class RequestImpl implements Request {

	private String message;
	private String connectionId;
	
	public RequestImpl(String connectionId, String message) {
		this.message = message;
		this.connectionId = connectionId;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getConnectionId() {
		return connectionId;
	}

}
