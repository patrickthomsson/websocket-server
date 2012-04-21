package se.patrickthomsson.websocketserver.protocol.connectfour.event;

import se.patrickthomsson.util.CollectionUtil;
import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.RequestImpl;
import se.patrickthomsson.websocketserver.protocol.connectfour.PlayerManager;

public class UserListEvent extends BroadcastEvent {

	private static final String USERLIST_PREFIX = "USERLIST:";
	
	public UserListEvent(PlayerManager playerManager) {
		super(playerManager, createUserListRequest(playerManager));
	}

	private static Request createUserListRequest(PlayerManager playerManager) {
		return new RequestImpl(createUserListMessage(playerManager), null);
	}

	private static String createUserListMessage(PlayerManager playerManager) {
		StringBuilder sb = new StringBuilder();
		sb.append(USERLIST_PREFIX);
		sb.append(CollectionUtil.asDelimitedString(playerManager.getPlayerNames()));
		return sb.toString();
	}
	
}
