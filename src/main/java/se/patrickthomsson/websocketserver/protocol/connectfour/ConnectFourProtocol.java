package se.patrickthomsson.websocketserver.protocol.connectfour;

import java.io.IOException;

import org.apache.log4j.Logger;

import se.patrickthomsson.websocketserver.WebSocketServer;
import se.patrickthomsson.websocketserver.connection.ConnectionId;
import se.patrickthomsson.websocketserver.protocol.CommunicationProtocol;
import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.Response;
import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Player;
import se.patrickthomsson.websocketserver.protocol.connectfour.event.EventResolver;
import se.patrickthomsson.websocketserver.protocol.connectfour.event.UserListEvent;

public class ConnectFourProtocol implements CommunicationProtocol {
	
	private static final Logger LOG = Logger.getLogger(ConnectFourProtocol.class);

	private PlayerManager playerManager = new PlayerManager();
	private EventResolver eventResolver = new EventResolver(playerManager);
	
	private WebSocketServer webSocketServer;
	
	public ConnectFourProtocol() {
		webSocketServer = new WebSocketServer(this);
	}

	@Override
	public void addConnection(ConnectionId id) {
		if (playerManager.playerExists(id)) {
			throw new ConnectFourException("Connection already exists!");
		}
		playerManager.addPlayer(new Player(id));
	}
	
	@Override
	public void removeConnection(ConnectionId id) {
		playerManager.removePlayer(id);
		Response userList = new UserListEvent(playerManager).process();
		webSocketServer.processResponse(userList);
	}

	@Override
	public void processRequest(Request request) {
		Response response = eventResolver.resolve(request).process();
		webSocketServer.processResponse(response);
	}

	public static void main(String[] args) throws IOException {
		LOG.info("Starting the ConnectFour protocol");
		ConnectFourProtocol protocol = new ConnectFourProtocol();
		protocol.webSocketServer.start();
	}

}
