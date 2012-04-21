package se.patrickthomsson.websocketserver.protocol;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import se.patrickthomsson.websocketserver.connection.ConnectionId;


public class ResponseImpl implements Response {
	
	private Collection<ConnectionId> receiverids;
	private String message;

	public ResponseImpl(String message, ConnectionId... receiverId) {
		this.message = message;
		this.receiverids = Arrays.asList(receiverId);
	}
	
	public ResponseImpl(String message, Collection<ConnectionId> receiverids) {
		this.message = message;
		this.receiverids = receiverids;
	}

	private ResponseImpl(Builder builder) {
		this.message = builder.message;
		this.receiverids = builder.receivers;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Collection<ConnectionId> getReceiverIds() {
		return receiverids;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(message)
			.append(receiverids)
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
		ResponseImpl other = (ResponseImpl) obj;
		
		return new EqualsBuilder()
			.append(message, other.message)
			.append(receiverids, other.receiverids)
			.isEquals();
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private String message;
		private Collection<ConnectionId> receivers;

		private Builder() {
			//hidden
		}
		
		public Builder message(String message) {
			this.message = message;
			return this;
		}
		
		public Builder receivers(ConnectionId... receivers) {
			this.receivers = Arrays.asList(receivers);
			return this;
		}
		
		public ResponseImpl build() {
			return new ResponseImpl(this);
		}
		
	}
	
}
