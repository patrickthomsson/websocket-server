package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import org.junit.Test;

import se.patrickthomsson.websocketserver.connection.ConnectionIdImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CellTest {
	
	@Test
	public void shouldNotBeOccpiedIfNew() {
		assertFalse(new Cell().isOccupied());
	}
	
	@Test
	public void shouldBeOccupiedAfterPlayerOccupies() {
		Cell c = new Cell();
		Player player = createPlayer("p1");
		c.occupy(player);
		assertTrue(c.isOccupied());
	}

	@Test
	public void shouldBeOccupiedByOccupier() {
		Cell c = new Cell();
		Player player = createPlayer("p1");
		c.occupy(player);
		assertEquals(player, c.getOccupier());
	}
	
	@Test
	public void shouldOnlyBeOccupiedByOccupier() {
		Cell c = new Cell();
		Player player1 = createPlayer("p1");
		Player player2 = createPlayer("p2");
		c.occupy(player1);
		assertTrue(c.isOccupiedBy(player1));
		assertFalse(c.isOccupiedBy(player2));
	}
	
	private Player createPlayer(String id) {
		return new Player(new ConnectionIdImpl(id));
	}

}
