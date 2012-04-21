package se.patrickthomsson.websocketserver.protocol;

import se.patrickthomsson.websocketserver.connection.ConnectionId;


public interface CommunicationProtocol {
	
	public void processRequest(Request request);
	public void addConnection(ConnectionId id);
	public void removeConnection(ConnectionId id);

}
