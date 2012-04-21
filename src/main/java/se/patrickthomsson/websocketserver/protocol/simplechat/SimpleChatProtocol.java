package se.patrickthomsson.websocketserver.protocol.simplechat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import se.patrickthomsson.util.CollectionUtil;
import se.patrickthomsson.websocketserver.WebSocketServer;
import se.patrickthomsson.websocketserver.connection.ConnectionId;
import se.patrickthomsson.websocketserver.protocol.CommunicationProtocol;
import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;
import se.patrickthomsson.websocketserver.protocol.ResponseImpl;

public class SimpleChatProtocol implements CommunicationProtocol {

	private static final Logger LOG = Logger.getLogger(SimpleChatProtocol.class);
	
	private static final String NEW_USER_MESSAGE_PREFIX = "META:NEW_USER:";
	private static final String USERLIST_MESSAGE_PREFIX = "META:USERLIST:";
	
	private Map<ConnectionId, String> connections = new HashMap<ConnectionId, String>();
	private WebSocketServer webSocketServer;
	
	public SimpleChatProtocol() {
		webSocketServer = new WebSocketServer(this);
	}

	@Override
	public void addConnection(ConnectionId id) {
		if (connections.containsKey(id)) {
			throw new RuntimeException("Connection already exists!");
		}
		connections.put(id, "Anonymous");
	}
	
	@Override
	public void removeConnection(ConnectionId id) {
		connections.remove(id);
		webSocketServer.processResponse(broadcast(createUserListMessage()));
	}

	@Override
	public void processRequest(Request request) {
		LOG.debug(String.format("Request; Message=%s, Sender=%s", request.getMessage(), request.getConnectionId()));
		Response response = createResponse(request);
		LOG.debug(String.format("Response; Message=%s, Receivers: %s", response.getMessage(), response.getReceiverIds()));
		webSocketServer.processResponse(response);
	}

	private Response createResponse(Request request) {
		if (isNewUserName(request.getMessage())) {
			saveUserName(request);
			return createUserListResponse();
		} else {
			return broadcast(request.getMessage());
		}
	}

	private Response broadcast(String message) {
		return new ResponseImpl(message, connections.keySet());
	}

	private boolean isNewUserName(String message) {
		return message.startsWith(NEW_USER_MESSAGE_PREFIX);
	}

	private void saveUserName(Request request) {
		String userName = request.getMessage().split(NEW_USER_MESSAGE_PREFIX)[1];
		connections.put(request.getConnectionId(), userName);
	}
	
	private Response createUserListResponse() {
		String userlistMessage = createUserListMessage();
		LOG.debug(userlistMessage);
		return broadcast(userlistMessage);
	}

	private String createUserListMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(USERLIST_MESSAGE_PREFIX);
		sb.append(CollectionUtil.asDelimitedString(connections.values()));
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		LOG.info("Starting the SimpleChat protocol");
		SimpleChatProtocol simpleChatProtocol = new SimpleChatProtocol();
		simpleChatProtocol.webSocketServer.start();
	}

}
