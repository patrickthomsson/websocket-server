package se.patrickthomsson.util;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BitManipulationUtilTest {
	
	@Test
	public void shouldSetFirstBit() {
		assertEquals(1, BitManipulationUtil.setFirstBit((byte) 0));
	}
	
	@Test
	public void shouldSetSecondBit() {
		assertEquals(2, BitManipulationUtil.setSecondBit((byte) 0));
		assertEquals(6, BitManipulationUtil.setSecondBit((byte) 4));
		assertEquals(10, BitManipulationUtil.setSecondBit((byte) 8));
	}
	
	@Test
	public void shouldSetEightBit() {
		assertEquals(-128, BitManipulationUtil.setEighthBit((byte) 0));
		assertEquals(-127, BitManipulationUtil.setEighthBit((byte) 1));
		assertEquals(-126, BitManipulationUtil.setEighthBit((byte) 2));
		assertEquals(-128, BitManipulationUtil.setEighthBit((byte) -128));
	}
	
	@Test
	public void shouldCheckIfBitsAreSet() {
		assertFalse(BitManipulationUtil.isFirstBitSet((byte) 0));
		assertTrue(BitManipulationUtil.isFirstBitSet((byte) 1));
		
		assertFalse(BitManipulationUtil.isEighthBitSet((byte) 0));
		assertTrue(BitManipulationUtil.isEighthBitSet((byte) -128));
	}
	
	@Test
	public void shouldUnsetEighthBit() {
		assertEquals(0, BitManipulationUtil.unsetEighthBit((byte) -128));
		assertEquals(1, BitManipulationUtil.unsetEighthBit((byte) -127));
		assertEquals(2, BitManipulationUtil.unsetEighthBit((byte) -126));
		assertEquals(3, BitManipulationUtil.unsetEighthBit((byte) -125));
		
		assertEquals(1, BitManipulationUtil.unsetEighthBit((byte) 1));
		assertEquals(2, BitManipulationUtil.unsetEighthBit((byte) 2));
		assertEquals(4, BitManipulationUtil.unsetEighthBit((byte) 4));
		assertEquals(8, BitManipulationUtil.unsetEighthBit((byte) 8));
		assertEquals(16, BitManipulationUtil.unsetEighthBit((byte) 16));
		assertEquals(32, BitManipulationUtil.unsetEighthBit((byte) 32));
		assertEquals(64, BitManipulationUtil.unsetEighthBit((byte) 64));
	}
	
	@Test
	public void shouldUnsetFourHighestOrderBits() {
		assertEquals(0, BitManipulationUtil.unsetFourHighestOrderBits((byte) -128));
		assertEquals(15, BitManipulationUtil.unsetFourHighestOrderBits((byte) 127));
		assertEquals(15, BitManipulationUtil.unsetFourHighestOrderBits((byte) 63));
		assertEquals(15, BitManipulationUtil.unsetFourHighestOrderBits((byte) 31));
		assertEquals(15, BitManipulationUtil.unsetFourHighestOrderBits((byte) 15));
		assertEquals(7, BitManipulationUtil.unsetFourHighestOrderBits((byte) 7));
		assertEquals(3, BitManipulationUtil.unsetFourHighestOrderBits((byte) 3));
		assertEquals(1, BitManipulationUtil.unsetFourHighestOrderBits((byte) 1));
		assertEquals(0, BitManipulationUtil.unsetFourHighestOrderBits((byte) 0));
	}
	
	@Test
	public void shouldInterpretAByteAsALong() {
		assertEquals(1, BitManipulationUtil.consecutiveBytesAsLong((byte) 1));
	}
	
	@Test
	public void shouldInterpretTwoConsecutiveBytesAsALong() {
		// 1 * (2^8)^1 + 0 * (2^8)^0 = 256 + 0
		assertEquals(256, BitManipulationUtil.consecutiveBytesAsLong((byte) 1, (byte) 0));
		
		// 1 * (2^8)^1 + 1 * (2^8)^0 = 256 + 1
		assertEquals(257, BitManipulationUtil.consecutiveBytesAsLong((byte) 1, (byte) 1));
		
		// 1 * (2^8)^1 + 20 * (2^8)^0 = 256 + 20
		assertEquals(276, BitManipulationUtil.consecutiveBytesAsLong((byte) 1, (byte) 20));
	}
	
	@Test
	public void shouldInterpretThreeConsecutiveBytesAsALong() {
		// 1 * (2^8)^2 + 0 * (2^8)^1 + 0 * (2^8)^0 = 65536 + 0 + 0
		assertEquals(65536, BitManipulationUtil.consecutiveBytesAsLong((byte) 1, (byte) 0, (byte) 0));
		
		// 1 * (2^8)^2 + 1 * (2^8)^1 + 50 * (2^8)^0 = 65536 + 256 + 50
		assertEquals(65842, BitManipulationUtil.consecutiveBytesAsLong((byte) 1, (byte) 1, (byte) 50));
	}
	
	@Test
	@Ignore("too large numbers... it's turned into integers when calculating, thus the higher order bits are cut off")
	public void shouldInterpretEightConsecutiveBytesAsALong() {
		assertEquals(72057594037927936L, BitManipulationUtil.consecutiveBytesAsLong((byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0));
	}

}
