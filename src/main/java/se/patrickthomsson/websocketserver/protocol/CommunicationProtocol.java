package se.patrickthomsson.websocketserver.protocol;


public interface CommunicationProtocol {
	
	public Response respond(Request request);

	public void addConnection(String id);

}
