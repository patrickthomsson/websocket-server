package se.patrickthomsson.websocketserver.handshake;

import java.util.Map;

public class HandshakeRequest {

	private String path;
	private Map<String, String> fields;
	private String randomBits;

	public HandshakeRequest(String path, Map<String, String> fields, String randomBits) {
		this.path = path;
		this.fields = fields;
		this.randomBits = randomBits;
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public String getRandomBits() {
		return randomBits;
	}

}
