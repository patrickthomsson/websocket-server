package se.patrickthomsson.util;

public class BitManipulationUtil {

	private static final byte FLAG_ALL_OFF = 0;  //         000...00000000 (empty mask)
	private static final byte FLAG_BIT_1 = 1;    // 2^^0    000...00000001
	private static final byte FLAG_BIT_2 = 2;    // 2^^1    000...00000010
	private static final byte FLAG_BIT_3 = 4;    // 2^^2    000...00000100
	private static final byte FLAG_BIT_4 = 8;    // 2^^3    000...00001000
	private static final byte FLAG_BIT_5 = 16;   // 2^^4    000...00010000
	private static final byte FLAG_BIT_6 = 32;   // 2^^5    000...00100000
	private static final byte FLAG_BIT_7 = 64;   // 2^^6    000...01000000
	private static final byte FLAG_BIT_8 = -128;  // 2^^7    000...10000000
	private static final byte[] FLAGS = new byte[] { FLAG_ALL_OFF, FLAG_BIT_1, FLAG_BIT_2, FLAG_BIT_3, FLAG_BIT_4, FLAG_BIT_5, FLAG_BIT_6, FLAG_BIT_7, FLAG_BIT_8 };
	
	private static final int MASK_WITH_FIRST_FOUR_BITS_SET = 15;   //00001111
	private static final int MASK_WITH_FIRST_SEVEN_BITS_SET = 127; //01111111

	public static byte setFirstBit(byte b) {
		return setBit(b, 1);
	}
	
	public static byte setSecondBit(byte b) {
		return setBit(b, 2);
	}
	
	public static byte setThirdBit(byte b) {
		return setBit(b, 3);
	}
	
	public static byte setFourthBit(byte b) {
		return setBit(b, 4);
	}
	
	public static byte setFifthBit(byte b) {
		return setBit(b, 5);
	}
	
	public static byte setSixthBit(byte b) {
		return setBit(b, 6);
	}
	
	public static byte setSeventhBit(byte b) {
		return setBit(b, 7);
	}
	
	public static byte setEighthBit(byte b) {
		return setBit(b, 8);
	}
	
	/**
	 * Set the highest order bit to zero (equal to ANDing it with 01111111)
	 * 
	 * @param b the byte to manipulate
	 * @return the bit-manipulated byte
	 */
	public static byte unsetEighthBit(byte b) {
		return andBits(b, MASK_WITH_FIRST_SEVEN_BITS_SET);
	}
	
	/**
	 * Sets the four highest order bits to zero (equal to ANDing it with 00001111)
	 * 
	 * @param b the byte to manipulate
	 * @return the bit-manipulated byte
	 */
	public static byte unsetFourHighestOrderBits(byte b) {
		return andBits(b, MASK_WITH_FIRST_FOUR_BITS_SET);
	}
	
	public static boolean isFirstBitSet(byte b) {
		return isBitSet(b, 1);
	}

	public static boolean isSecondBitSet(byte b) {
		return isBitSet(b, 2);
	}

	public static boolean isThirdBitSet(byte b) {
		return isBitSet(b, 3);
	}

	public static boolean isFourthBitSet(byte b) {
		return isBitSet(b, 4);
	}
	
	public static boolean isEighthBitSet(byte b) {
		return isBitSet(b, 8);
	}
	
	/**
	 * Interpret consecutive bytes as a Long. The first byte (at index 0) is interpreted as being of the highest order
	 */
	public static long consecutiveBytesAsLong(byte... bytes) {
		long result = 0;
		for(int i=0; i<bytes.length; i++) {
			int shiftFactor = 8 * (bytes.length - (i + 1));
			result += bytes[i] << shiftFactor;
		}
		return result;
	}

	/**
	 * Interpret the integer as a sequence of bytes. 
	 * 
	 * Imagine chopping the integer bit-pattern into four pieces and looking at the separate pieces as bytes. 
	 * The first element in the array (at index 0) will be of the highest order  
	 */
	public static byte[] integerAsConsecutiveBytes(int num) {
		byte[] bytes = new byte[4];
		for(int i=0; i < bytes.length; i++) {
			int shiftFactor = 8 * (bytes.length - (i + 1));
			int value = num >> shiftFactor;
			bytes[i] = (byte) value;
		}
		return bytes;
	}
	
	private static byte setBit(byte b, int index) {
		return b |= FLAGS[index];
	}
	
	private static byte andBits(byte b, int mask) {
		return b &= mask;
	}
	
	private static boolean isBitSet(byte b, int index) {
		return FLAGS[index] == (b & FLAGS[index]);
	}
	
}
