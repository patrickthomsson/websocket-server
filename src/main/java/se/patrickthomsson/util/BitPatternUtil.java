package se.patrickthomsson.util;

public class BitPatternUtil {
	
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
	}

}
