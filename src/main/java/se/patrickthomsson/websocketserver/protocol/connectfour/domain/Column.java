package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import java.util.ArrayList;
import java.util.List;

import se.patrickthomsson.websocketserver.protocol.connectfour.ConnectFourException;

public class Column {
	
	private List<Cell> cells = new ArrayList<Cell>();
	
	public Column(final int rows) {
		for(int i=0; i<rows; i++) {
			cells.add(new Cell());
		}
	}
	
	public List<Cell> getCells() {
		return cells;
	}

	public boolean hasFreeCell() {
		return findFirstUnoccupiedCell() != null;
	}
	
	public void occupyCell(Player player) {
		Cell cell = findFirstUnoccupiedCell();
		if(cell == null) {
			throw new ConnectFourException("No available cell in column!");
		}
		cell.occupy(player);
	}

	private Cell findFirstUnoccupiedCell() {
		for(Cell cell : cells) {
			if(!cell.isOccupied()) {
				return cell;
			}
		}
		return null;
	}
	
}
