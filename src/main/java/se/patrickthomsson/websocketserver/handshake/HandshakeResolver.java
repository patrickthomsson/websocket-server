package se.patrickthomsson.websocketserver.handshake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.patrickthomsson.websocketserver.WebSocketServer;

@Service
public class HandshakeResolver {
	
	private static final String LF = "\r\n";
	
	@Autowired
	private ChallengeCalculator challengeCalculator;

	public HandshakeResponse buildResponse(HandshakeRequest handshakeRequest) {
		int version = getVersion(handshakeRequest);
		if(version < 6) {
			return createVersionZero(handshakeRequest);
		}
		return createVersionSixOrHigher(handshakeRequest);
	}

	private HandshakeResponse createVersionSixOrHigher(HandshakeRequest handshakeRequest) {
		String secWebSocketKey = handshakeRequest.getFields().get("Sec-WebSocket-Key");
		String challenge = challengeCalculator.calculateVersion6Challenge(secWebSocketKey);
		
		String message = "HTTP/1.1 101 Switching Protocols" + LF + 
	        "Upgrade: webSocket" + LF + 
	        "Connection: Upgrade" + LF + 
			"Sec-WebSocket-Accept: " + challenge + LF + LF; 
		
		return new HandshakeResponse(message);
	}

	private HandshakeResponse createVersionZero(HandshakeRequest handshakeRequest) {
		String challenge = challengeCalculator.calculate(
				handshakeRequest.getFields().get("Sec-WebSocket-Key1"), 
				handshakeRequest.getFields().get("Sec-WebSocket-Key2"), 
				handshakeRequest.getRandomBits());
		
		String message = "HTTP/1.1 101 WebSocket Protocol Handshake" + LF + 
	        "Upgrade: WebSocket" + LF + 
	        "Connection: Upgrade" + LF + 
	        "Sec-WebSocket-Origin: " + handshakeRequest.getFields().get("Origin") + LF + 
	        "Sec-WebSocket-Location: ws://localhost:" + WebSocketServer.port + handshakeRequest.getPath() + LF + 
	        "Sec-WebSocket-Protocol: sample" + LF + 
			LF +
			challenge;
		
		return new HandshakeResponse(message);
	}

	private int getVersion(HandshakeRequest handshakeRequest) {
		String version = handshakeRequest.getFields().get("Sec-WebSocket-Version");
		return version != null ? Integer.parseInt(version) : 0;
	}

}
