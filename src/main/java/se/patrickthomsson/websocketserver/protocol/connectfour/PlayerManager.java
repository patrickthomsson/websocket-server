package se.patrickthomsson.websocketserver.protocol.connectfour;

import java.util.ArrayList;
import java.util.List;

import se.patrickthomsson.websocketserver.connection.ConnectionId;
import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Player;

public class PlayerManager {
	
	private List<Player> players = new ArrayList<Player>();
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(ConnectionId id) {
		players.remove(getPlayerByConnectionId(id));
	}

	public boolean playerExists(ConnectionId id) {
		for(Player p : players) {
			if(p.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public List<ConnectionId> getPlayerIds() {
		List<ConnectionId> ids = new ArrayList<ConnectionId>();
		for(Player p : players) {
			ids.add(p.getId());
		}
		return ids;
	}

	public Player getPlayerByConnectionId(ConnectionId connectionId) {
		for(Player p : players) {
			if(p.getId().equals(connectionId)) {
				return p;
			}
		}
		throw new ConnectFourException("Could not find player with id: " + connectionId);
	}
	
	public Player getPlayerByName(String name) {
		for(Player p : players) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		throw new ConnectFourException("Could not find player with name: " + name);
	}

	public List<String> getPlayerNames() {
		List<String> names = new ArrayList<String>();
		for(Player p : players) {
			names.add(p.getName());
		}
		return names;
	}

}
