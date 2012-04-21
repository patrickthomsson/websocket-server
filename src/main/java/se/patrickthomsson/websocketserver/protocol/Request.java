package se.patrickthomsson.websocketserver.protocol;

import se.patrickthomsson.websocketserver.connection.ConnectionId;

public interface Request {

	public ConnectionId getConnectionId();
	public String getMessage();

}
