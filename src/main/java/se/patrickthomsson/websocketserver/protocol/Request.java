package se.patrickthomsson.websocketserver.protocol;

public interface Request {

	public String getConnectionId();
	public String getMessage();

}
