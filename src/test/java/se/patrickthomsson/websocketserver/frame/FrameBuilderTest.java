package se.patrickthomsson.websocketserver.frame;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;


public class FrameBuilderTest {

	@InjectMocks
	private FrameBuilder frameBuilder = new FrameBuilder();
	
	@Mock
	private MaskingKeyGenerator maskingKeyGenerator;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldBuildMaskedFrame() {
		when(maskingKeyGenerator.generate()).thenReturn(new byte[] { 0x37, (byte) 0xfa, 0x2, 0x3d });
		
		Frame frame = frameBuilder.buildMaskedFrame("Hello");
		
		byte[] bytes = new byte[]{(byte) 0x81, (byte) 0x85, 0x37, (byte) 0xfa, 0x2, 0x3d, 0x7f, (byte) 0x9f, 0x6e, 0x51, 0x58};
		
		for(int i=0; i<bytes.length; i++) {
			assertEquals("Byte not equal at index " + i + ".", bytes[i], frame.getRawFrame()[i]);
		}

		assertEquals(bytes.length, frame.getRawFrame().length);
	}
	
}
