package se.patrickthomsson.websocketserver.connection;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ConnectionIdImpl implements ConnectionId {
	
	private final String identifier;
	
	public ConnectionIdImpl(final String identifier) {
		this.identifier = identifier;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(identifier).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectionIdImpl other = (ConnectionIdImpl) obj;
		return new EqualsBuilder().append(identifier, other.identifier).isEquals();
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
