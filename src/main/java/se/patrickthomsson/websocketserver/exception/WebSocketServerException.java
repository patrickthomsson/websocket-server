package se.patrickthomsson.websocketserver.exception;

public class WebSocketServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public WebSocketServerException(String message) {
		super(message);
	}
	
	public WebSocketServerException(String message, Throwable cause) {
		super(message, cause);
	}

}
