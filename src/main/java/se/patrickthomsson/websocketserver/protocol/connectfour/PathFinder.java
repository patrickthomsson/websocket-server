package se.patrickthomsson.websocketserver.protocol.connectfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Coordinate;
import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Dimension;
import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Path;

public class PathFinder {

	private static final Map<Dimension, List<Path>> cache = new HashMap<Dimension, List<Path>>();

	public List<Path> findPaths(Dimension dimension) {
		if(cache.containsKey(dimension)) {
			return cache.get(dimension);
		}
		List<Path> allPaths = getPathsOfAllDirections(dimension);
		cache.put(dimension, allPaths);
		return allPaths;
	}

	private List<Path> getPathsOfAllDirections(Dimension dimension) {
		List<Path> allPaths = new ArrayList<Path>();
		allPaths.addAll(buildSouthToNorthPaths(dimension));
		allPaths.addAll(buildWestToEastPaths(dimension));
		allPaths.addAll(buildNorthEastToSouthWestPaths(dimension));
		allPaths.addAll(buildNorthWestToSouthEastPaths(dimension));
		return allPaths;
	}

	/**
	 *  ^ 
	 *  | 
	 *  |
	 * 
	 */
	List<Path> buildSouthToNorthPaths(Dimension dimension) {
		List<Path> paths = new ArrayList<Path>();
		for (int column = 0; column < dimension.width; column++) {
			Path.Builder path = Path.builder();
			for (int row = 0; row < dimension.height; row++) {
				path.withCoordinate(new Coordinate(column, row));
			}
			paths.add(path.build());
		}
		return paths;
	}

	/**
	 * ---->
	 * 
	 */
	List<Path> buildWestToEastPaths(Dimension dimension) {
		List<Path> paths = new ArrayList<Path>();
		for (int row = 0; row < dimension.height; row++) {
			Path.Builder path = Path.builder();
			for (int column = 0; column < dimension.width; column++) {
				path.withCoordinate(new Coordinate(column, row));
			}
			paths.add(path.build());
		}
		return paths;
	}

	/**
	 *  \ 
	 *   \ 
	 *    \ 
	 *     V
	 * 
	 */
	List<Path> buildNorthWestToSouthEastPaths(Dimension dimension) {
		List<Path> paths = new ArrayList<Path>();
		int column = 0;
		int row = 0;
		while (row + 1 < dimension.height) {
			Path path = buildNorthWestToSouthEastPath(column, row, dimension);
			paths.add(path);
			row++;
		}
		while (column < dimension.width) {
			Path path = buildNorthWestToSouthEastPath(column, row, dimension);
			paths.add(path);
			column++;
		}
		return paths;
	}

	/**
	 *     / 
	 *    / 
	 *   / 
	 *  v
	 * 
	 */
	List<Path> buildNorthEastToSouthWestPaths(Dimension dimension) {
		List<Path> paths = new ArrayList<Path>();
		int column = dimension.width - 1;
		int row = 0;
		while (row + 1 < dimension.height) {
			Path path = buildNorthEastToSouthWestPath(column, row);
			paths.add(path);
			row++;
		}
		while (column >= 0) {
			Path path = buildNorthEastToSouthWestPath(column, row);
			paths.add(path);
			column--;
		}
		return paths;
	}

	private Path buildNorthWestToSouthEastPath(int x, int y, Dimension dimension) {
		Path.Builder path = Path.builder();
		do {
			path.withCoordinate(new Coordinate(x, y));
			x++;
			y--;
		} while (y >= 0 && x < dimension.width);
		return path.build();
	}

	private Path buildNorthEastToSouthWestPath(int x, int y) {
		Path.Builder path = Path.builder();
		do {
			path.withCoordinate(new Coordinate(x, y));
			x--;
			y--;
		} while (y >= 0 && x >= 0);
		return path.build();
	}

}
