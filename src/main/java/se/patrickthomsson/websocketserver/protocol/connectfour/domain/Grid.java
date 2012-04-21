package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import java.util.List;


public interface Grid {
	
	public Dimension getDimension();
	public Cell cellAt(Coordinate coord);
	public List<Cell> cellsAt(List<Coordinate> coords);

}
