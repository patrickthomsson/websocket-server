package se.patrickthomsson.websocketserver.connection;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManager {

	@Autowired
	private IdentityGenerator identityGenerator;
	
	private Map<SocketAddress, Connection> connections;
	
	public ConnectionManager() {
		connections = new HashMap<SocketAddress, Connection>();
	}
	
	public Connection getConnection(Socket socket) {
		return connections.get(socket.getRemoteSocketAddress());
	}
	
	public Collection<Connection> getConnections() {
		return connections.values();
	}
	
	public Collection<Socket> getSockets() {
		Collection<Socket> sockets = new ArrayList<Socket>();
		for(Connection c : getConnections()) {
			sockets.add(c.getSocket());
		}
		return sockets;
	}
	
	public Connection addConnection(Socket s) {
		if(connections.containsKey(s.getRemoteSocketAddress())) {
			connections.remove(s.getRemoteSocketAddress());
		}
		Connection connection = createConnection(s);
		connections.put(s.getRemoteSocketAddress(), connection);
		return connection;
	}

	private Connection createConnection(Socket s) {
		String id = identityGenerator.generateId();
		return new Connection(s, id);
	}

	public void removeConnection(Socket socket) {
		connections.remove(connections.get(socket.getRemoteSocketAddress()));
	}

	public Collection<Connection> getConnections(Collection<String> connectionIds) {
		Collection<Connection> connections = new ArrayList<Connection>(); 
		for(Connection c : getConnections()) {
			String id = c.getId();
			if(connectionIds.contains(id)) {
				connections.add(c);
			}
		}
		return connections;
	}
	
}
