package se.patrickthomsson.websocketserver.protocol.connectfour.event;

import se.patrickthomsson.websocketserver.connection.ConnectionId;
import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;
import se.patrickthomsson.websocketserver.protocol.ResponseImpl;
import se.patrickthomsson.websocketserver.protocol.connectfour.PlayerManager;
import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Player;

public class InvitationEvent extends Event {
	
	private static final String INVITATION_PREFIX = "INVITATION:";

	public InvitationEvent(PlayerManager playerManager, Request request) {
		super(playerManager, request);
	}

	@Override
	public Response process() {
		return createInvitationResponse();
	}
	
	private Response createInvitationResponse() {
		if(isResponseToEarlierInvitation()) {
			return createResponseToEarlierInvitation();
		}
		return createInvitation();
	}

	private Response createInvitation() {
		String senderName = getUserNameFromRequest();
		String invitationResponse = INVITATION_PREFIX + "FROM:" + senderName;
		
		String inviteeName = request.getMessage().split(INVITATION_PREFIX)[1];
		ConnectionId inviteeConnectionId = playerManager.getPlayerByName(inviteeName).getId();
		
		return new ResponseImpl(invitationResponse, inviteeConnectionId);
	}

	private Response createResponseToEarlierInvitation() {
		String invitationResponse = invitationAccepted() ? ":ACCEPTED" : ":DECLINED";
		String message = INVITATION_PREFIX + "TO:" + getUserNameFromRequest() + invitationResponse;
		String respondingUsersName = request.getMessage().split(":")[2];
		Player respondingPlayer = playerManager.getPlayerByName(respondingUsersName);
		return new ResponseImpl(message, respondingPlayer.getId());
	}

	private String getUserNameFromRequest() {
		return playerManager.getPlayerByConnectionId(request.getConnectionId()).getName();
	}

	private boolean isResponseToEarlierInvitation() {
		return invitationAccepted() || invitationDeclined();
	}

	private boolean invitationDeclined() {
		return request.getMessage().endsWith(":DECLINED");
	}

	private boolean invitationAccepted() {
		return request.getMessage().endsWith(":ACCEPTED");
	}
	
}
