package se.patrickthomsson.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BitPatternUtilTest {
	
	@Test
	public void shouldCreateBitPatternFromByte() {
		String pattern = BitPatternUtil.createBitPattern((byte) 17);
		assertEquals(8, pattern.length());
	}
	
	@Test
	public void shouldCreateBitPatternFromInteger() {
		String pattern = BitPatternUtil.createBitPattern(17);
		assertEquals(32, pattern.length());
	}

}
