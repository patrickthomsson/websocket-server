package se.patrickthomsson.websocketserver.connection;

import java.io.IOException;
import java.net.Socket;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import se.patrickthomsson.websocketserver.exception.WebSocketServerException;

public class Connection {
	
	private enum ConnectionState {
		AWAITING_HANDSHAKE, OPEN, CLOSED;
	}
	
	private final Socket socket;
	private ConnectionState state;
	private final ConnectionId id;

	public Connection(Socket socket, ConnectionId id) {
		this.socket = socket;
		this.id = id;
		this.state = ConnectionState.AWAITING_HANDSHAKE;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public ConnectionId getId() {
		return id;
	}

	public boolean isAwaitingHandshake() {
		return state == ConnectionState.AWAITING_HANDSHAKE;
	}

	public boolean isOpen() {
		return state == ConnectionState.OPEN;
	}
	
	public void open() {
		state = ConnectionState.OPEN;
	}

	public boolean isActive() {
		return socket.getChannel().isOpen();
	}
	
	public void close() {
		state = ConnectionState.CLOSED;
		try {
			socket.getChannel().close();
			socket.close();
		} catch (IOException e) {
			throw new WebSocketServerException("Failed to close connection", e);
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(id)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connection other = (Connection) obj;
		
		return new EqualsBuilder()
			.append(id, other.getId())
			.isEquals();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append(id)
			.append(state)
			.append(socket)
			.toString();
	}

}
