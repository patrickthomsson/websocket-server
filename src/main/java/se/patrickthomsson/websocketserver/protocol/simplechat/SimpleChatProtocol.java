package se.patrickthomsson.websocketserver.protocol.simplechat;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import se.patrickthomsson.websocketserver.protocol.CommunicationProtocol;
import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;

public class SimpleChatProtocol implements CommunicationProtocol {

	private static final Logger LOG = Logger.getLogger(SimpleChatProtocol.class);
	
	private static final String NEW_USER_MESSAGE_PREFIX = "META:NEW_USER:";
	private static final String USERLIST_MESSAGE_PREFIX = "META:USERLIST:";
	private static final String DELIMITER = ",";
	
	private Map<String, String> connections = new HashMap<String, String>();

	@Override
	public void addConnection(String id) {
		if (connections.containsKey(id)) {
			throw new RuntimeException("Connection already exists!");
		}
		connections.put(id, "Anonymous");
	}

	@Override
	public Response respond(Request request) {
		String message = request.getMessage();
		if (isNewUserName(message)) {
			saveUserName(request);
			return createUserListResponse();
		}

		return new ResponseImpl(message, connections.keySet());
	}

	private boolean isNewUserName(String message) {
		return message.contains(NEW_USER_MESSAGE_PREFIX);
	}

	private void saveUserName(Request request) {
		String userName = request.getMessage().split(NEW_USER_MESSAGE_PREFIX)[1];
		connections.put(request.getConnectionId(), userName);
	}
	
	private Response createUserListResponse() {
		String userlistMessage = createUserListMessage();
		LOG.debug(userlistMessage);
		return new ResponseImpl(userlistMessage, connections.keySet());
	}

	private String createUserListMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(USERLIST_MESSAGE_PREFIX);
		for(String userName : connections.values()) {
			sb.append(userName).append(DELIMITER);
		}
		sb.delete(sb.length()-DELIMITER.length(), sb.length());
		return sb.toString();
	}

}
