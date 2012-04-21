package se.patrickthomsson.websocketserver.protocol;

import java.util.Collection;

import se.patrickthomsson.websocketserver.connection.ConnectionId;

public interface Response {
	
	public String getMessage();
	public Collection<ConnectionId> getReceiverIds();

}
