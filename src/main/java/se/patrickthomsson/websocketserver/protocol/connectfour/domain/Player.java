package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import se.patrickthomsson.websocketserver.connection.ConnectionId;

public class Player {

	private ConnectionId id;
	private String name;
	
	public Player(ConnectionId id) {
		this.id = id;
		this.name = "Anonymous";
	}
	
	public ConnectionId getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
