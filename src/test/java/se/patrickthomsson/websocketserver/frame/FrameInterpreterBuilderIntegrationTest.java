package se.patrickthomsson.websocketserver.frame;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import static org.mockito.MockitoAnnotations.initMocks;

public class FrameInterpreterBuilderIntegrationTest {

	@Mock
	private MaskingKeyGenerator maskingKeyGenerator;
	
	@InjectMocks
	private FrameBuilder frameBuilder = new FrameBuilder();
	private FrameInterpreter frameInterpreter = new FrameInterpreter();
	
	@Before
	public void setUp() {
		initMocks(this);
		when(maskingKeyGenerator.generate()).thenReturn(new MaskingKeyGenerator().generate());
	}
	
	@Test
	public void shouldNotDistortMessageWhenMaskingAndUnmasking() {
		String message = "tjenna";
		
		Frame maskedFrame = frameBuilder.buildMaskedFrame(message);
		Frame interpreted = frameInterpreter.interpret(maskedFrame.getRawFrame());
		
		assertEquals(message, interpreted.getUnmaskedData());
		
		Frame maskedFrame2 = frameBuilder.buildMaskedFrame(interpreted.getUnmaskedData());
		Frame interpreted2 = frameInterpreter.interpret(maskedFrame2.getRawFrame());
		
		assertEquals(message, interpreted2.getUnmaskedData());
	}
	
}
