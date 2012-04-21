package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import se.patrickthomsson.websocketserver.protocol.connectfour.ConnectFourException;
import se.patrickthomsson.websocketserver.protocol.connectfour.InARowCounter;

public class Board implements Grid {
	
	private final InARowCounter inARowCounter = new InARowCounter();
	private final List<Column> columns = new ArrayList<Column>();
	private final Dimension dimension;
	
	public Board(final Dimension dimension) {
		this.dimension = dimension;
		init();
	}
	
	public boolean isPlayable(int columnIndex) {
		return columns.get(columnIndex).hasFreeCell();
	}
	
	public void play(Player player, int columnIndex) {
		Column column = columns.get(columnIndex);
		if(!column.hasFreeCell()) {
			throw new ConnectFourException("Column is full!");
		}
		column.occupyCell(player);
	}
	
	public int countCellsInARow(Player player) {
		return inARowCounter.countCellsInARow(player, this);
	}
	
	@Override
	public Dimension getDimension() {
		return dimension;
	}
	
	@Override
	public Cell cellAt(Coordinate coord) {
		return columns.get(coord.x).getCells().get(coord.y);
	}
	
	@Override
	public List<Cell> cellsAt(List<Coordinate> coordinates) {
		List<Cell> cells = new ArrayList<Cell>();
		for(Coordinate coord : coordinates) {
			cells.add(cellAt(coord));
		}
		return cells;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	private void init() {
		for(int i=0; i< dimension.width; i++) {
			columns.add(new Column(dimension.height));
		}
	}

}
