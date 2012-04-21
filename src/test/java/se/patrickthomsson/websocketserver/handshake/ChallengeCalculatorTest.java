package se.patrickthomsson.websocketserver.handshake;

import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChallengeCalculatorTest {
	
	private static final String CHECKSUM = "fQJ,fN/4F4!~K~MH";
	private static final String KEY_1 = "18x 6]8vM;54 *(5:  {   U1]8  z [  8";
	private static final String KEY_2 = "1_ tx7X d  <  nw  334J702) 7]o}` 0";
	private static final String RANDOM_BITS = "Tm[K T2u";
	
	private ChallengeCalculator calculator;
	
	@Before
	public void setUp() {
		calculator = new ChallengeCalculator();
	}
	
	@Test
	public void shouldTransformKeyToNumber() {
		assertEquals(Long.valueOf(1868545188), calculator.extractNumber(KEY_1));
		assertEquals(Long.valueOf(1733470270), calculator.extractNumber(KEY_2));
	}
	
	@Test
	public void shouldCountAllBlankSpaces() {
		assertEquals(12, calculator.countSpaces(KEY_1));
		assertEquals(10, calculator.countSpaces(KEY_2));
	}
	
	@Test
	public void shouldCalculateChecksum() {
		assertEquals(CHECKSUM, calculator.calculate(KEY_1, KEY_2, RANDOM_BITS));
	}
	
	@Test
	public void shouldComputeVersion6Challenge() throws NoSuchAlgorithmException {
		assertEquals("HSmrc0sMlYUkAGmm5OPpG2HaGWk=", calculator.calculateVersion6Challenge("x3JJHMbDL1EzLkh9GBhXDw=="));
	}
	
}
