package se.patrickthomsson.websocketserver.handshake;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RequestParserTest {

	private static final String HANDSHAKE_REQUEST = "GET /%s HTTP/1.1\r\n"
		+ "Upgrade: WebSocket\r\n"
		+ "Connection: Upgrade\r\n"
		+ "Host: localhost:8080\r\n"
		+ "Origin: http://localhost\r\n"
		+ "Sec-WebSocket-Key1: JdLq163tD#``22x586~ 7\r\n"
		+ "Sec-WebSocket-Key2: M350 e5 9   47   1 65\r\n"
		+ "\r\n"
		+ "�^@�T]�\r\n";
	
	private RequestParser handshakeParser;
	
	@Before
	public void setUp() {
		handshakeParser = new RequestParser();
	}
	
	@Test
	public void shouldParseRequestPath() {
		String path = "file.html";
		HandshakeRequest parsedRequest = handshakeParser.parse(String.format(HANDSHAKE_REQUEST, path).getBytes());
		assertNotNull(parsedRequest.getPath());
		assertEquals("/" + path, parsedRequest.getPath());
	}
	
	@Test
	public void shouldParseRequestFields() {
		HandshakeRequest parsedRequest = handshakeParser.parse(HANDSHAKE_REQUEST.getBytes());
		assertNotNull(parsedRequest.getFields());
		assertFalse(parsedRequest.getFields().isEmpty());
		assertEquals("WebSocket", parsedRequest.getFields().get("Upgrade"));
		assertEquals("Upgrade", parsedRequest.getFields().get("Connection"));
		assertEquals("localhost:8080", parsedRequest.getFields().get("Host"));
		assertEquals("http://localhost", parsedRequest.getFields().get("Origin"));
		assertEquals("JdLq163tD#``22x586~ 7", parsedRequest.getFields().get("Sec-WebSocket-Key1"));
		assertEquals("M350 e5 9   47   1 65", parsedRequest.getFields().get("Sec-WebSocket-Key2"));
	}
	
	@Test
	public void shouldParseRequestRandomBits() {
		HandshakeRequest parsedRequest = handshakeParser.parse(HANDSHAKE_REQUEST.getBytes());
		assertNotNull(parsedRequest.getRandomBits());
		assertEquals("�^@�T]�\r\n", parsedRequest.getRandomBits());
	}
}
