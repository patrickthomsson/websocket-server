package se.patrickthomsson.websocketserver.frame;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import static org.mockito.MockitoAnnotations.initMocks;

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
		byte[] expectedBytes = new byte[] { (byte) 0x81, (byte) 0x85, 0x37, (byte) 0xfa, 0x2, 0x3d, 0x7f, (byte) 0x9f, 0x6e, 0x51, 0x58 };
		assertEqualArrays(expectedBytes, frame.getRawFrame());
	}

	private void assertEqualArrays(byte[] expected, byte[] actual) {
		for(int i=0; i<expected.length; i++) {
			assertEquals("Byte not equal at index " + i + ".", expected[i], actual[i]);
		}
		assertEquals(expected.length, actual.length);
	}
	
}
