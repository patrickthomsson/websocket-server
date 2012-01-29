package se.patrickthomsson.websocketserver.frame;

public enum FrameType {
	
	CONTINUATION,
	TEXT,
	BINARY,
	RESERVED_3,
	RESERVED_4,
	RESERVED_5,
	RESERVED_6,
	RESERVED_7,
	CONNECTION_CLOSE,
	PING,
	PONG,
	RESERVED_11,
	RESERVED_12,
	RESERVED_13,
	RESERVED_14,
	RESERVED_15;
	
	public byte getOpCode() {
		return (byte) ordinal();
	}
	
	public static FrameType fromOpCode(byte opCode) {
		for(FrameType t : values()) {
			if(t.getOpCode() == opCode) {
				return t;
			}
		}
		throw new IllegalArgumentException("No matching frame type for opCode=" + opCode);
	}
	
}
