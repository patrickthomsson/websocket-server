package se.patrickthomsson.websocketserver.protocol;

import se.patrickthomsson.websocketserver.connection.ConnectionId;


public class RequestImpl implements Request {

	private final String message;
	private final ConnectionId connectionId;
	
	public RequestImpl(String message, ConnectionId connectionId) {
		this.message = message;
		this.connectionId = connectionId;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public ConnectionId getConnectionId() {
		return connectionId;
	}

}
