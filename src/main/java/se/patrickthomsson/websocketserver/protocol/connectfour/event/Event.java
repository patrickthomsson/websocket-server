package se.patrickthomsson.websocketserver.protocol.connectfour.event;

import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;
import se.patrickthomsson.websocketserver.protocol.connectfour.PlayerManager;

public abstract class Event {

	protected PlayerManager playerManager;
	protected Request request;
	
	public Event(PlayerManager playerManager, Request request) {
		this.playerManager = playerManager;
		this.request = request;
	}
	
	public abstract Response process();
	
}
