package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Coordinate {

	public final int x;
	public final int y;
	
	public Coordinate(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(x).append(y).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		return new EqualsBuilder().append(x, other.x).append(y, other.y).isEquals();
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
