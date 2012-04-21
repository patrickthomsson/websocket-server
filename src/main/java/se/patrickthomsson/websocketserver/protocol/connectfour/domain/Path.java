package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Path {

	private List<Coordinate> coordinates = new ArrayList<Coordinate>();
	
	private Path(List<Coordinate> coordinates) {
		this.coordinates.addAll(coordinates);
	}
	
	public List<Coordinate> getCoordinates() {
		return new ArrayList<Coordinate>(coordinates);
	}
	
	public static Builder builder() {
		return new BuilderImpl();
	}
	
	public interface Builder {
		public Builder withCoordinate(Coordinate coordinate);
		public Path build();
	}
	
	private static class BuilderImpl implements Builder {
		
		private List<Coordinate> builderCoordinates = new ArrayList<Coordinate>();

		@Override
		public Builder withCoordinate(Coordinate coordinate) {
			builderCoordinates.add(coordinate);
			return this;
		}

		@Override
		public Path build() {
			return new Path(builderCoordinates);
		}
		
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
