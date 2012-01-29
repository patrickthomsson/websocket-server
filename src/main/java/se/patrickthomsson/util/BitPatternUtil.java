package se.patrickthomsson.util;

public class BitPatternUtil {
	
	private static final byte FLAG_BIT_1 = 1;    // 2^^0    00000001
	private static final byte FLAG_BIT_2 = 2;    // 2^^1    00000010
	private static final byte FLAG_BIT_3 = 4;    // 2^^2    00000100
	private static final byte FLAG_BIT_4 = 8;    // 2^^3    00001000
	private static final byte FLAG_BIT_5 = 16;
	private static final byte FLAG_BIT_6 = 32;
	private static final byte FLAG_BIT_7 = 64;
	private static final byte FLAG_BIT_8 = (byte) 128;
	
	private BitPatternUtil() {
		//hidden constructor
	}

	public static String createBitPattern(byte[] bytes) {
		Byte[] objects = new Byte[bytes.length];
		for(int i=0; i<bytes.length; i++) {
			objects[i] = bytes[i];
		}
		return createBitPattern(objects);
	}
	
	public static String createBitPattern(Byte... bytes) {
		StringBuilder pattern = new StringBuilder();
		for(byte b : bytes) {
			pattern.append(byteToBitPattern(b));
		}
		return pattern.toString();
	}
	
	public static String createBitPattern(Integer... integers) {
		StringBuilder pattern = new StringBuilder();
		for(int i : integers) {
			pattern.append(integerToBitPattern(i));
		}
		return pattern.toString();
	}

	private static String integerToBitPattern(Integer b) {
		String binaryString = Integer.toBinaryString(b);
		while(binaryString.length() < 32) {
			binaryString = "0" + binaryString;
		}
		return binaryString;
	}
	
	private static String byteToBitPattern(Byte b) {
		String binaryString = Integer.toBinaryString(b);
		while(binaryString.length() < 8) {
			binaryString = "0" + binaryString;
		}
		return binaryString;
		
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append(eighthBitSet(b) ? "1" : "0");
//		sb.append(seventhBitSet(b) ? "1" : "0");
//		sb.append(sixthBitSet(b) ? "1" : "0");
//		sb.append(fifthBitSet(b) ? "1" : "0");
//		sb.append(fourthBitSet(b) ? "1" : "0");
//		
//		sb.append(thirdBitSet(b) ? "1" : "0");
//		sb.append(secondBitSet(b) ? "1" : "0");
//		sb.append(firstBitSet(b) ? "1" : "0");
//		
//		return sb.toString();
	}
	
	
	private static boolean firstBitSet(byte b) {
		return FLAG_BIT_1 == (b & FLAG_BIT_1);
	}

	private static boolean secondBitSet(byte b) {
		return FLAG_BIT_2 == (b & FLAG_BIT_2);
	}

	private static boolean thirdBitSet(byte b) {
		return FLAG_BIT_3 == (b & FLAG_BIT_3);
	}

	private static boolean fourthBitSet(byte b) {
		return FLAG_BIT_4 == (b & FLAG_BIT_4);
	}
	
	private static boolean fifthBitSet(byte b) {
		return FLAG_BIT_5 == (b & FLAG_BIT_5);
	}
	
	private static boolean sixthBitSet(byte b) {
		return FLAG_BIT_6 == (b & FLAG_BIT_6);
	}
	
	private static boolean seventhBitSet(byte b) {
		return FLAG_BIT_7 == (b & FLAG_BIT_7);
	}
	
	private static boolean eighthBitSet(byte b) {
		return FLAG_BIT_8 == (b & FLAG_BIT_8);
	}

}
