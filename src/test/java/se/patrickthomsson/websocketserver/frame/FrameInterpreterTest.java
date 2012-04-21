package se.patrickthomsson.websocketserver.frame;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FrameInterpreterTest {

	@Test
	public void shouldInterpretMaskedFrame() throws Exception {
		byte[] bytes = new byte[]{(byte) 0x81, (byte) 0x85, 0x37, (byte) 0xfa, 0x2, 0x3d, 0x7f, (byte) 0x9f, 0x6e, 0x51, 0x58};
		Frame frame = new FrameInterpreter().interpret(bytes);
		assertEquals("Hello", frame.getUnmaskedData());
	}
	
}
