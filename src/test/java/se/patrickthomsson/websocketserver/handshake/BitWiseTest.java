package se.patrickthomsson.websocketserver.handshake;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BitWiseTest {
	
	// Constants to hold bit masks for desired flags
	static final byte flagAllOff = 0;  //         000...00000000 (empty mask)
	static final byte flagbit1 = 1;    // 2^^0    000...00000001
	static final byte flagbit2 = 2;    // 2^^1    000...00000010
	static final byte flagbit3 = 4;    // 2^^2    000...00000100
	static final byte flagbit4 = 8;    // 2^^3    000...00001000
	static final byte flagbit5 = 16;   // 2^^4    000...00010000
	static final byte flagbit6 = 32;   // 2^^5    000...00100000
	static final byte flagbit7 = 64;   // 2^^6    000...01000000
	static final byte flagbit8 = -128;  // 2^^7    000...10000000

	@Test
	public void should() {
		byte b = -127;
		System.out.println(b &= flagbit7);
		assertTrue(b == (b &= flagbit7));
		assertTrue(b == (b &= flagbit8));
	}
	
	@Test
	public void test() {
		byte b = 1;
		assertEquals(b, b & flagbit1);
		
		assertEquals(3, b | flagbit2);
		assertEquals(5, b | flagbit3);
		assertEquals(9, b | flagbit4);
		
		assertEquals(17, b | flagbit5);
		assertEquals(33, b | flagbit6);
		assertEquals(65, b | flagbit7);
		
		assertEquals(-127, (byte) (b | flagbit8));
		assertEquals(-125, (byte) (b | flagbit8 | flagbit2));
		
		byte zero = 0;
		assertEquals(-128, zero | flagbit8); // 1000 0000
		assertEquals(-1, (byte) (zero | flagbit1 | flagbit2 | flagbit3 | flagbit4 | flagbit5 | flagbit6 | flagbit7 | flagbit8)); // 1111 1111
		assertEquals(127, (byte) (zero | flagbit1 | flagbit2 | flagbit3 | flagbit4 | flagbit5 | flagbit6 | flagbit7)); // 0111 1111
		assertEquals(-1, flagAllOff | ~flagAllOff);
		assertEquals(-1, ~flagAllOff);
		assertEquals(127, (byte) (~flagAllOff & ~flagbit8));
	}
	
	@Test
	public void testName() throws Exception {

//		int dataItemByte = 240 ^ "M".getBytes()[0];
//		String dataItem = new String(new byte[]{ (byte) dataItemByte });
//		System.out.println(dataItemByte + "=" + dataItem);
//		
//		
//		byte[] bytes = "A".getBytes("utf-8");
//		System.out.println(bytes[0] + "=" + new String(bytes));
//		System.out.println(new String(new byte[]{65}));
//		
//		for(int i=0; i<128; i++) {
//			System.out.println(i + "=" + new String(new byte[] { (byte) i }));
//		}
		
		
		int i = 56 ^ 43;
		System.out.println(i + "=" + (i ^ 43));
		
	}	
}
