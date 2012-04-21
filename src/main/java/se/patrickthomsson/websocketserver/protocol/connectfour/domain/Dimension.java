package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Dimension {

	public final int width;
	public final int height;

	public Dimension(final int width, final int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(width)
			.append(height)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dimension other = (Dimension) obj;
		return new EqualsBuilder()
			.append(width, other.width)
			.append(height, other.height)
			.isEquals();
	}
	
}
