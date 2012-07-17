package se.patrickthomsson.websocketserver.frame;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaskingKeyGeneratorTest {
	
	private MaskingKeyGenerator maskingKeyGenerator = new MaskingKeyGenerator();
	
	@Test
	public void shouldGenerateMaskingKey() throws Exception {
		byte[] maskingKey = maskingKeyGenerator.generate(1);
		assertEquals(0, maskingKey[0]);
		assertEquals(0, maskingKey[1]);
		assertEquals(0, maskingKey[2]);
		assertEquals(1, maskingKey[3]);
	}
	
	@Test
	public void shouldGenerateMaskingKey2() throws Exception {
		byte[] maskingKey = maskingKeyGenerator.generate(256);
		assertEquals(0, maskingKey[0]);
		assertEquals(0, maskingKey[1]);
		assertEquals(1, maskingKey[2]);
		assertEquals(0, maskingKey[3]);
	}
	
	@Test
	public void shouldGenerateMaskingKey3() throws Exception {
		byte[] maskingKey = maskingKeyGenerator.generate(257);
		assertEquals(0, maskingKey[0]);
		assertEquals(0, maskingKey[1]);
		assertEquals(1, maskingKey[2]);
		assertEquals(1, maskingKey[3]);
	}
	
	@Test
	public void shouldGenerateMaskingKey4() throws Exception {
		byte[] maskingKey = maskingKeyGenerator.generate(65536);
		assertEquals(0, maskingKey[0]);
		assertEquals(1, maskingKey[1]);
		assertEquals(0, maskingKey[2]);
		assertEquals(0, maskingKey[3]);
	}
	
	@Test
	public void shouldGenerateMaskingKey5() throws Exception {
		byte[] maskingKey = maskingKeyGenerator.generate(16777216);
		assertEquals(1, maskingKey[0]);
		assertEquals(0, maskingKey[1]);
		assertEquals(0, maskingKey[2]);
		assertEquals(0, maskingKey[3]);
	}
	
	@Test
	public void shouldGenerateMaskingKey6() throws Exception {
		byte[] maskingKey = maskingKeyGenerator.generate(16777217);
		assertEquals(1, maskingKey[0]);
		assertEquals(0, maskingKey[1]);
		assertEquals(0, maskingKey[2]);
		assertEquals(1, maskingKey[3]);
	}
	
}
