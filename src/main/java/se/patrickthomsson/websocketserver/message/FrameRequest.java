package se.patrickthomsson.websocketserver.message;

import se.patrickthomsson.websocketserver.connection.ConnectionId;
import se.patrickthomsson.websocketserver.frame.Frame;
import se.patrickthomsson.websocketserver.protocol.Request;

public class FrameRequest implements Request {

	private Frame frame;
	private ConnectionId connectionId;
	
	public FrameRequest(Frame frame, ConnectionId connectionId) {
		this.frame = frame;
		this.connectionId = connectionId;
	}
	
	@Override
	public String getMessage() {
		return frame.getUnmaskedData();
	}

	@Override
	public ConnectionId getConnectionId() {
		return connectionId;
	}

}
