package se.patrickthomsson.websocketserver.message;

public abstract class Message {
	
	private String message;
	
	public Message(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	protected static String wrap(String message) {
		return asString(0) + message + asString(-1);
	}
	
	private static String asString(int... bytes) {
		byte[] bs = asByteArray(bytes);
		return new String(bs);
	}

	private static byte[] asByteArray(int... bytes) {
		byte[] bs = new byte[bytes.length];
		for(int i=0; i<bytes.length; i++) {
			bs[i] = (byte) bytes[i];
		}
		return bs;
	}
}
