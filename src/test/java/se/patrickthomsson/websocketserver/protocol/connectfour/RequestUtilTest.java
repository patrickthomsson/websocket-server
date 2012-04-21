package se.patrickthomsson.websocketserver.protocol.connectfour;

import org.junit.Test;

import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.RequestImpl;
import se.patrickthomsson.websocketserver.protocol.connectfour.event.EventType;
import static org.junit.Assert.assertEquals;

public class RequestUtilTest {
	
	@Test
	public void shouldReturnPartOfMessageAfterFirstColon() {
		Request request = new RequestImpl("NEW_USER:foo", null);
		assertEquals("foo", RequestUtil.getMessagePart(request));
	}
	
	@Test
	public void shouldReturnTypeOfMessage() {
		Request request = new RequestImpl("NEW_USER:foo", null);
		assertEquals(EventType.NEW_USER, RequestUtil.getEventType(request));
	}
	
	@Test
	public void shouldInterpretUnknownMessageTypeAsBroadcast() {
		Request request = new RequestImpl("unknownType:foo", null);
		assertEquals(EventType.BROADCAST, RequestUtil.getEventType(request));
	}
	
}
