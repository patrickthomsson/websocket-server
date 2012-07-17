package se.patrickthomsson.websocketserver.frame;

import java.util.Random;

import org.springframework.stereotype.Service;

import se.patrickthomsson.util.BitManipulationUtil;

@Service
public class MaskingKeyGenerator {
	
	public byte[] generate() {
		Random r = new Random();
		return generate(r.nextInt());
	}
	
	protected byte[] generate(int random) {
		return BitManipulationUtil.integerAsConsecutiveBytes(random);
	}

}
