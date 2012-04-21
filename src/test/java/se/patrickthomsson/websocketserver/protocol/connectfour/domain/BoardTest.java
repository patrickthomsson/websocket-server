package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import org.junit.Before;
import org.junit.Test;

import se.patrickthomsson.websocketserver.connection.ConnectionIdImpl;
import static org.junit.Assert.assertEquals;

public class BoardTest {

	private Player p1 = new Player(new ConnectionIdImpl("p1"));
	private Player p2 = new Player(new ConnectionIdImpl("p2"));
	
	private Board board;

	@Before
	public void setUp() {
		board = new Board(new Dimension(6, 7));
	}

	@Test
	public void shouldHaveZeroInARowWhenEmpty() {
		assertEquals(0, board.countCellsInARow(p1));
	}
	
	@Test
	public void shouldBeOneInARowWhenOneBrickAdded() {
		board.play(p1, 0);
		assertEquals(1, board.countCellsInARow(p1));
	}
	
	@Test
	public void shouldHaveTwoInARowWhenTwoAddedInTheSameColumn() {
		board.play(p1, 0);
		board.play(p1, 0);
		assertEquals(2, board.countCellsInARow(p1));
	}
	
	@Test
	public void shouldHaveThreeInARowWhenThreeAddedToTheSameColumn() {
		board.play(p1, 0);
		board.play(p1, 0);
		board.play(p1, 0);
		assertEquals(3, board.countCellsInARow(p1));
	}
	
	@Test
	public void shouldHaveFourInARowWhenFourAddedToTheSameColumn() {
		board.play(p1, 0);
		board.play(p1, 0);
		board.play(p1, 0);
		board.play(p1, 0);
		assertEquals(4, board.countCellsInARow(p1));
	}
	
	@Test
	public void shouldHaveFourInARowWhenFourAddedToTheSameColumn2() {
		board.play(p1, 1);
		board.play(p1, 1);
		board.play(p1, 1);
		board.play(p1, 1);
		assertEquals(4, board.countCellsInARow(p1));
	}
	
	@Test
	public void shouldOnlyCountTheCellsOwnedByPlayer() {
		board.play(p1, 0);
		board.play(p1, 0);
		board.play(p2, 0);
		board.play(p2, 0);
		assertEquals(2, board.countCellsInARow(p1));
		assertEquals(2, board.countCellsInARow(p2));
	}
	
	@Test
	public void shouldOnlyCountTheCellsOwnedByPlayer2() {
		// - 2 -
		// - 2 -
		// - 1 -
		// - 1 -
		// - 1 -
		// - 1 - - - -
		
		board.play(p1, 1);
		board.play(p1, 1);
		board.play(p1, 1);
		board.play(p1, 1);
		board.play(p2, 1);
		board.play(p2, 1);
		assertEquals(4, board.countCellsInARow(p1));
		assertEquals(2, board.countCellsInARow(p2));
	}
	
	@Test
	public void shouldHaveFourInARowIfFourCellsAtTheBottomOfFourColumns() {
		// - - - - - -
		// 1 1 1 1 - -
		
		board.play(p1, 0);
		board.play(p1, 1);
		board.play(p1, 2);
		board.play(p1, 3);
		assertEquals(4, board.countCellsInARow(p1));
	}
	
	@Test
	public void shouldNotMixColumnsWhenCounting() {
		// 1 - - 
		// 1 - -
		// 2 - -
		// 1 - -
		// 2 1 -
		// 1 1 -
		
		board.play(p1, 0);
		board.play(p2, 0);
		board.play(p1, 0);
		board.play(p2, 0);
		
		board.play(p1, 0);
		board.play(p1, 0);
		board.play(p1, 1);
		board.play(p1, 1);
		assertEquals(2, board.countCellsInARow(p1));
		assertEquals(1, board.countCellsInARow(p2));
	}
	
	@Test
	public void shouldCountCellsInARowDiagonally() {
		// 2 - - - -
		// 1 2 - - -
		// 1 1 2 - -
		// 1 1 1 2 -
		
		board.play(p1, 0);
		board.play(p1, 0);
		board.play(p1, 0);

		board.play(p1, 1);
		board.play(p1, 1);

		board.play(p1, 2);
		
		board.play(p2, 0);
		board.play(p2, 1);
		board.play(p2, 2);
		board.play(p2, 3);
		
		assertEquals(3, board.countCellsInARow(p1));
		assertEquals(4, board.countCellsInARow(p2));
	}
	
	@Test
	public void shouldCountCellsInARowDiagonally2() {
		// - - - 2
		// - - 2 1
		// - 2 1 1 
		// 2 1 1 1
		
		board.play(p1, 1);
		board.play(p1, 2);
		board.play(p1, 3);

		board.play(p1, 2);
		board.play(p1, 3);

		board.play(p1, 3);
		
		board.play(p2, 0);
		board.play(p2, 1);
		board.play(p2, 2);
		board.play(p2, 3);
		
		assertEquals(3, board.countCellsInARow(p1));
		assertEquals(4, board.countCellsInARow(p2));
	}
	
}
