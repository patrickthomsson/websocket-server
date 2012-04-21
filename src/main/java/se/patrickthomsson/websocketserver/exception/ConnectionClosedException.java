package se.patrickthomsson.websocketserver.exception;

public class ConnectionClosedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ConnectionClosedException(String message) {
		super(message);
	}

}
