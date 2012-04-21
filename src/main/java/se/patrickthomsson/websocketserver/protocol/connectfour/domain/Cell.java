package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Cell {
	
	private Player occupier;
	
	public void occupy(Player occupier) {
		this.occupier = occupier;
	}
	
	public boolean isOccupied() {
		return occupier != null;
	}
	
	public boolean isOccupiedBy(Player player) {
		return player.equals(occupier);
	}
	
	public Player getOccupier() {
		return occupier;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
