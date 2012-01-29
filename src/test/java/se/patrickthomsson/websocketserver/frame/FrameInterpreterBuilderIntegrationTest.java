package se.patrickthomsson.websocketserver.frame;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FrameInterpreterBuilderIntegrationTest {

	private FrameBuilder frameBuilder = new FrameBuilder();
	private FrameInterpreter frameInterpreter = new FrameInterpreter();
	
	@Test
	public void testBuilderInterpreterIntegration() {
		String message = "tjenna";
		
		Frame maskedFrame = frameBuilder.buildMaskedFrame(message);
		Frame interpreted = frameInterpreter.interpret(maskedFrame.getRawFrame());
		
		assertEquals(message, interpreted.getUnmaskedData());
		
		Frame maskedFrame2 = frameBuilder.buildMaskedFrame(interpreted.getUnmaskedData());
		Frame interpreted2 = frameInterpreter.interpret(maskedFrame2.getRawFrame());
		
		assertEquals(message, interpreted2.getUnmaskedData());
	}
	
}
