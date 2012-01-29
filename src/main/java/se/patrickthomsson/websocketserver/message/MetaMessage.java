package se.patrickthomsson.websocketserver.message;

public class MetaMessage extends Message {

	public MetaMessage(String message) {
		super(wrap(message));
	}

}
