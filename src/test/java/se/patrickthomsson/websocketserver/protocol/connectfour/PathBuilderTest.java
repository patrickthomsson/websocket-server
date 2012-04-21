package se.patrickthomsson.websocketserver.protocol.connectfour;

import java.util.List;

import org.junit.Test;

import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Coordinate;
import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Dimension;
import se.patrickthomsson.websocketserver.protocol.connectfour.domain.Path;
import static org.junit.Assert.assertEquals;

public class PathBuilderTest {

	private PathFinder pathBuilder = new PathFinder();
	
	@Test
	public void shouldBuildOnePathWithOneCoordinate() {
		List<Path> paths = pathBuilder.buildSouthToNorthPaths(new Dimension(1, 1));
		assertEquals(1, paths.size());
		assertEquals(1, paths.get(0).getCoordinates().size());
		assertEquals(new Coordinate(0, 0), paths.get(0).getCoordinates().get(0));
	}
	
	@Test
	public void shouldBuildOnePathWithTwoCoordinates() {
		List<Path> paths = pathBuilder.buildSouthToNorthPaths(new Dimension(1, 2));
		assertEquals(1, paths.size());
		assertEquals(2, paths.get(0).getCoordinates().size());
		assertEquals(new Coordinate(0, 0), paths.get(0).getCoordinates().get(0));
		assertEquals(new Coordinate(0, 1), paths.get(0).getCoordinates().get(1));
	}
	
	@Test
	public void shouldBuildOnePathWithFiveCoordinates() {
		List<Path> paths = pathBuilder.buildSouthToNorthPaths(new Dimension(1, 5));
		assertEquals(1, paths.size());
		assertEquals(5, paths.get(0).getCoordinates().size());
		assertEquals(new Coordinate(0, 0), paths.get(0).getCoordinates().get(0));
		assertEquals(new Coordinate(0, 1), paths.get(0).getCoordinates().get(1));
		assertEquals(new Coordinate(0, 2), paths.get(0).getCoordinates().get(2));
		assertEquals(new Coordinate(0, 3), paths.get(0).getCoordinates().get(3));
		assertEquals(new Coordinate(0, 4), paths.get(0).getCoordinates().get(4));
	}
	
	@Test
	public void shouldBuildTwoPathsWithTwoCoordinatesEach() {
		List<Path> paths = pathBuilder.buildSouthToNorthPaths(new Dimension(2, 2));
		assertEquals(2, paths.size());
		assertEquals(2, paths.get(0).getCoordinates().size());
		assertEquals(2, paths.get(1).getCoordinates().size());
		assertEquals(new Coordinate(0, 0), paths.get(0).getCoordinates().get(0));
		assertEquals(new Coordinate(0, 1), paths.get(0).getCoordinates().get(1));
		assertEquals(new Coordinate(1, 0), paths.get(1).getCoordinates().get(0));
		assertEquals(new Coordinate(1, 1), paths.get(1).getCoordinates().get(1));
	}
	
	@Test
	public void shouldBuildWestToEastPathWithTwoPathsWithThreeCoordinatesEach() {
		List<Path> paths = pathBuilder.buildWestToEastPaths(new Dimension(3, 2));
		assertEquals(2, paths.size());
		assertEquals(3, paths.get(0).getCoordinates().size());
		assertEquals(3, paths.get(1).getCoordinates().size());
		assertEquals(new Coordinate(0, 0), paths.get(0).getCoordinates().get(0));
		assertEquals(new Coordinate(1, 0), paths.get(0).getCoordinates().get(1));
		assertEquals(new Coordinate(2, 0), paths.get(0).getCoordinates().get(2));
		
		assertEquals(new Coordinate(0, 1), paths.get(1).getCoordinates().get(0));
		assertEquals(new Coordinate(1, 1), paths.get(1).getCoordinates().get(1));
		assertEquals(new Coordinate(2, 1), paths.get(1).getCoordinates().get(2));
	}
	
	@Test
	public void shouldBuildDiagonalPathFromNorthWestToSouthEastDimension2x2() {
		List<Path> paths = pathBuilder.buildNorthWestToSouthEastPaths(new Dimension(2, 2));
		assertEquals(3, paths.size());
		assertEquals(1, paths.get(0).getCoordinates().size());
		assertEquals(2, paths.get(1).getCoordinates().size());
		assertEquals(1, paths.get(2).getCoordinates().size());
		
		assertEquals(new Coordinate(0, 0), paths.get(0).getCoordinates().get(0));
		
		assertEquals(new Coordinate(0, 1), paths.get(1).getCoordinates().get(0));
		assertEquals(new Coordinate(1, 0), paths.get(1).getCoordinates().get(1));
		
		assertEquals(new Coordinate(1, 1), paths.get(2).getCoordinates().get(0));
	}
	
	@Test
	public void shouldBuildDiagonalPathFromNorthWestToSouthEastDimension3x3() {
		List<Path> paths = pathBuilder.buildNorthWestToSouthEastPaths(new Dimension(3, 3));
		assertEquals(5, paths.size());
		assertEquals(1, paths.get(0).getCoordinates().size());
		assertEquals(2, paths.get(1).getCoordinates().size());
		assertEquals(3, paths.get(2).getCoordinates().size());
		assertEquals(2, paths.get(3).getCoordinates().size());
		assertEquals(1, paths.get(4).getCoordinates().size());
		
		assertEquals(new Coordinate(0, 0), paths.get(0).getCoordinates().get(0));
		
		assertEquals(new Coordinate(0, 1), paths.get(1).getCoordinates().get(0));
		assertEquals(new Coordinate(1, 0), paths.get(1).getCoordinates().get(1));
		
		assertEquals(new Coordinate(0, 2), paths.get(2).getCoordinates().get(0));
		assertEquals(new Coordinate(1, 1), paths.get(2).getCoordinates().get(1));
		assertEquals(new Coordinate(2, 0), paths.get(2).getCoordinates().get(2));
		
		assertEquals(new Coordinate(1, 2), paths.get(3).getCoordinates().get(0));
		assertEquals(new Coordinate(2, 1), paths.get(3).getCoordinates().get(1));
		
		assertEquals(new Coordinate(2, 2), paths.get(4).getCoordinates().get(0));
	}
	
	@Test
	public void shouldBuildDiagonalPathFromNorthWestToSouthEastDimension5x4() {
		List<Path> paths = pathBuilder.buildNorthWestToSouthEastPaths(new Dimension(5, 4));
		assertEquals(8, paths.size());
		assertEquals(1, paths.get(0).getCoordinates().size());
		assertEquals(2, paths.get(1).getCoordinates().size());
		assertEquals(3, paths.get(2).getCoordinates().size());
		assertEquals(4, paths.get(3).getCoordinates().size());
		assertEquals(4, paths.get(4).getCoordinates().size());
		assertEquals(3, paths.get(5).getCoordinates().size());
		assertEquals(2, paths.get(6).getCoordinates().size());
		assertEquals(1, paths.get(7).getCoordinates().size());
		
		assertEquals(new Coordinate(0, 0), paths.get(0).getCoordinates().get(0));
		
		assertEquals(new Coordinate(0, 1), paths.get(1).getCoordinates().get(0));
		assertEquals(new Coordinate(1, 0), paths.get(1).getCoordinates().get(1));
		
		assertEquals(new Coordinate(0, 2), paths.get(2).getCoordinates().get(0));
		assertEquals(new Coordinate(1, 1), paths.get(2).getCoordinates().get(1));
		assertEquals(new Coordinate(2, 0), paths.get(2).getCoordinates().get(2));
		
		assertEquals(new Coordinate(0, 3), paths.get(3).getCoordinates().get(0));
		assertEquals(new Coordinate(1, 2), paths.get(3).getCoordinates().get(1));
		assertEquals(new Coordinate(2, 1), paths.get(3).getCoordinates().get(2));
		assertEquals(new Coordinate(3, 0), paths.get(3).getCoordinates().get(3));
		
		assertEquals(new Coordinate(1, 3), paths.get(4).getCoordinates().get(0));
		assertEquals(new Coordinate(2, 2), paths.get(4).getCoordinates().get(1));
		assertEquals(new Coordinate(3, 1), paths.get(4).getCoordinates().get(2));
		assertEquals(new Coordinate(4, 0), paths.get(4).getCoordinates().get(3));
		
		assertEquals(new Coordinate(2, 3), paths.get(5).getCoordinates().get(0));
		assertEquals(new Coordinate(3, 2), paths.get(5).getCoordinates().get(1));
		assertEquals(new Coordinate(4, 1), paths.get(5).getCoordinates().get(2));
		
		assertEquals(new Coordinate(3, 3), paths.get(6).getCoordinates().get(0));
		assertEquals(new Coordinate(4, 2), paths.get(6).getCoordinates().get(1));
		
		assertEquals(new Coordinate(4, 3), paths.get(7).getCoordinates().get(0));
	}
	
	@Test
	public void shouldBuildDiagonalPathFromNorthEastToSouthWestDimension3x2() {
		List<Path> paths = pathBuilder.buildNorthEastToSouthWestPaths(new Dimension(3, 2));
		assertEquals(4, paths.size());
		assertEquals(1, paths.get(0).getCoordinates().size());
		assertEquals(2, paths.get(1).getCoordinates().size());
		assertEquals(2, paths.get(2).getCoordinates().size());
		assertEquals(1, paths.get(3).getCoordinates().size());
		
		assertEquals(new Coordinate(2, 0), paths.get(0).getCoordinates().get(0));
		
		assertEquals(new Coordinate(2, 1), paths.get(1).getCoordinates().get(0));
		assertEquals(new Coordinate(1, 0), paths.get(1).getCoordinates().get(1));
		
		assertEquals(new Coordinate(1, 1), paths.get(2).getCoordinates().get(0));
		assertEquals(new Coordinate(0, 0), paths.get(2).getCoordinates().get(1));
		
		assertEquals(new Coordinate(0, 1), paths.get(3).getCoordinates().get(0));
	}
	
}
