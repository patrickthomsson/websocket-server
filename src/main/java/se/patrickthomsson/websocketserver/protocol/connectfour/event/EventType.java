package se.patrickthomsson.websocketserver.protocol.connectfour.event;

public enum EventType {

	NEW_USER, INVITATION, BROADCAST;

	public static EventType get(String eventType) {
		for(EventType type : values()) {
			if(eventType.equalsIgnoreCase(type.name())) {
				return type;
			};
		}
		return BROADCAST;
	} 
	
}
