package se.patrickthomsson.websocketserver.protocol.connectfour;

import org.apache.log4j.Logger;

import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.connectfour.event.EventType;

public class RequestUtil {

	private static final Logger LOG = Logger.getLogger(RequestUtil.class);
	private static final String DELIMITER = ":";

	public static String getMessagePart(Request request) {
		int messageStartIndex = request.getMessage().indexOf(DELIMITER) + 1;
		return request.getMessage().substring(messageStartIndex);
	}

	public static EventType getEventType(Request request) {
		if(request.getMessage().contains(DELIMITER)) {
			int delimiterIndex = request.getMessage().indexOf(DELIMITER);
			String eventType = request.getMessage().substring(0, delimiterIndex);
			EventType type = EventType.get(eventType);
			LOG.debug(String.format("Interpreted eventType '%s' as '%s' ", eventType, type));
			return type;
		}
		throw new ConnectFourException("Could not parse request message type. Request message contained no delimter.");
	}
	
}
