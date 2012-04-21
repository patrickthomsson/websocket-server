package se.patrickthomsson.websocketserver.protocol.connectfour.event;

import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;
import se.patrickthomsson.websocketserver.protocol.connectfour.ConnectFourException;
import se.patrickthomsson.websocketserver.protocol.connectfour.PlayerManager;

public class NewUserNameEvent extends BroadcastEvent {
	
	private static final String NEW_USER_PREFIX = "NEW_USER:";	
	
	public NewUserNameEvent(PlayerManager playerManager, Request request) {
		super(playerManager, request);
	}

	@Override
	public Response process() {
		String userName = readUserName(request);
		if(playerManager.getPlayerNames().contains(userName)) {
			throw new ConnectFourException(String.format("User names must be unique! %s is already taken!", userName));
		}
		playerManager.getPlayerByConnectionId(request.getConnectionId()).setName(userName);
		return new UserListEvent(playerManager).process();
	}
	
	private String readUserName(Request request) {
		return request.getMessage().split(NEW_USER_PREFIX)[1];
	}
	
}
