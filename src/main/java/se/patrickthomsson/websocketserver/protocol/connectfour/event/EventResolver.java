package se.patrickthomsson.websocketserver.protocol.connectfour.event;

import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.connectfour.ConnectFourException;
import se.patrickthomsson.websocketserver.protocol.connectfour.PlayerManager;
import se.patrickthomsson.websocketserver.protocol.connectfour.RequestUtil;

public class EventResolver {

	private PlayerManager playerManager;

	public EventResolver(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	public Event resolve(Request request) {
		EventType eventType = RequestUtil.getEventType(request);
		switch (eventType) {
		case NEW_USER:
			return new NewUserNameEvent(playerManager, request);
		case INVITATION:
			return new InvitationEvent(playerManager, request);
		case BROADCAST:
			return new BroadcastEvent(playerManager, request);
		default:
			throw new ConnectFourException("Unknown eventType: " + eventType);
		}
	}

}
