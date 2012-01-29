package se.patrickthomsson.websocketserver.frame;

import org.junit.Test;

public class MaskingKeyGeneratorTest {
	
	private MaskingKeyGenerator maskingKeyGenerator = new MaskingKeyGenerator();
	
	@Test
	public void shouldGenerateMaskingKey() throws Exception {
		maskingKeyGenerator.generate(255);
	}
	

}
