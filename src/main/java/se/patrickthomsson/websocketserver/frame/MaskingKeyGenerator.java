package se.patrickthomsson.websocketserver.frame;

import java.util.Random;

import se.patrickthomsson.util.BitPatternUtil;

public class MaskingKeyGenerator {
	
	public byte[] generate() {
		Random r = new Random();
		return generate(r.nextInt());
	}
	
	public byte[] generate(int random) {
		String integerBitPattern = BitPatternUtil.createBitPattern(random);
		byte[] maskingKey = new byte[4];
		
		for(int i=0; i<4; i++) {
			String byteBitPattern = integerBitPattern.substring(i * 8, (i + 1) * 8);
			int intValue = Integer.parseInt(byteBitPattern, 2);
			maskingKey[i] = (byte) intValue;
		}
		return maskingKey;
	}

}
