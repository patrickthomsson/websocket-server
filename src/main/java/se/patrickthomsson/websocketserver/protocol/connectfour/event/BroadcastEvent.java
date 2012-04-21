package se.patrickthomsson.websocketserver.protocol.connectfour.event;

import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;
import se.patrickthomsson.websocketserver.protocol.ResponseImpl;
import se.patrickthomsson.websocketserver.protocol.connectfour.PlayerManager;

public class BroadcastEvent extends Event {

	public BroadcastEvent(PlayerManager playerManager, Request request) {
		super(playerManager, request);
	}

	@Override
	public Response process() {
		return broadcast(request.getMessage());
	}
	
	protected Response broadcast(String message) {
		return new ResponseImpl(message, playerManager.getPlayerIds());
	}
	
}
