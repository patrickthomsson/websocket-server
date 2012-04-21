package se.patrickthomsson.websocketserver.protocol.connectfour;

import java.util.List;

import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Cell;
import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Grid;
import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Path;
import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Player;

public class InARowCounter {
	
	private PathFinder pathFinder = new PathFinder();
	
	public int countCellsInARow(Player player, Grid grid) {
		List<Path> paths = pathFinder.findPaths(grid.getDimension());
		int max = 0;
		for(Path path : paths) {
			List<Cell> cells = grid.cellsAt(path.getCoordinates());
			int inARowInPath = countCellsInARow(player, cells);
			max = inARowInPath > max ? inARowInPath : max;
		}
		return max;
	}
	
	private int countCellsInARow(Player player, List<Cell> cells) {
		int max = 0;
		int inARow = 0;
		for(Cell cell : cells) {
			inARow = cell.isOccupiedBy(player) ? inARow + 1 : 0;
			max = inARow > max ? inARow : max;
		}
		return max;
	}

}
