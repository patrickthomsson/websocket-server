package se.patrickthomsson.websocketserver.handshake;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class HandshakeResolverTest {

	private HandshakeResolver handshakeResolver;
	
	@Before
	public void setUp() {
		handshakeResolver = new HandshakeResolver();
	}
	
	@Test
	public void shouldDoStuff() {
		handshakeResolver.buildResponse(createRequest());
	}

	private HandshakeRequest createRequest() {
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("Upgrade", "WebSocket");
		fields.put("Connection", "Upgrade");
		fields.put("Host", "localhost:8080");
		fields.put("Origin", "http://localhost");
		fields.put("Sec-WebSocket-Key1", "JdLq163tD#``22x586~ 7");
		fields.put("Sec-WebSocket-Key2", "M350 e5 9   47   1 65");
		return new HandshakeRequest("/test", fields, "õ^@‡T]š");
	}
	
}
