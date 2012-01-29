package se.patrickthomsson.websocketserver.message;

public class TextMessage extends Message {

	public TextMessage(String message) {
		super(wrap(message));
	}
	
}
