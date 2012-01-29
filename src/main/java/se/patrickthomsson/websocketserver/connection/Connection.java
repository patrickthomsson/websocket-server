package se.patrickthomsson.websocketserver.connection;

import java.net.Socket;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Connection {
	
	private enum ConnectionState {
		AWAITING_HANDSHAKE, OPEN;
	}
	
	private final Socket socket;
	private ConnectionState state;
	private final String id;

	public Connection(Socket socket, String id) {
		this.socket = socket;
		this.id = id;
		this.state = ConnectionState.AWAITING_HANDSHAKE;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public String getId() {
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

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(id)
			.hashCode();
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
