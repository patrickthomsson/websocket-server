package se.patrickthomsson.websocketserver.protocol.simplechat;

import java.util.Collection;

import se.patrickthomsson.websocketserver.protocol.Response;

public class ResponseImpl implements Response {
	
	private Collection<String> receiverids;
	private String message;

	public ResponseImpl(String message, Collection<String> receiverids) {
		this.message = message;
		this.receiverids = receiverids;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Collection<String> getReceiverIds() {
		return receiverids;
	}

}
