package se.patrickthomsson.websocketserver.frame;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import se.patrickthomsson.util.BitPatternUtil;

@Service
public class FrameInterpreter {
	
	private static final Logger LOG = Logger.getLogger(FrameInterpreter.class);
	
	private static final byte FLAG_BIT_1 = 1;    // 2^^0    00000001
	private static final byte FLAG_BIT_2 = 2;    // 2^^1    00000010
	private static final byte FLAG_BIT_3 = 4;    // 2^^2    00000100
	private static final byte FLAG_BIT_4 = 8;    // 2^^3    00001000
	
	public Frame interpret(byte[] frameBytes) {
		ByteIndex byteIndex = new ByteIndex(2);
		
		boolean frameFin = firstBitSet(frameBytes[0]);
		boolean frameRsv1 = secondBitSet(frameBytes[0]);
		boolean frameRsv2 = thirdBitSet(frameBytes[0]);
		boolean frameRsv3 = fourthBitSet(frameBytes[0]);
		
		byte opCode = getOpCode(frameBytes[0]);
		boolean masked = firstBitSet(frameBytes[1]);
		
		int payloadLen = getPayloadLength(frameBytes, byteIndex);
		byte[] maskingKey = getMaskingKey(frameBytes, byteIndex);
		byte[] payloadData = getPayloadData(frameBytes, payloadLen, byteIndex);
		String unmaskedPayloadData = unmaskPayloadData(payloadData, maskingKey);

		Frame frame = new Frame();
		frame.setFinalFrame(frameFin);
		frame.setRsv1(frameRsv1);
		frame.setRsv2(frameRsv2);
		frame.setRsv3(frameRsv3);
		frame.setType(FrameType.fromOpCode(opCode));
		frame.setMasked(masked);
		frame.setPayloadLength(payloadLen);
		frame.setMaskingKey(maskingKey);
		frame.setUnmaskedData(unmaskedPayloadData);
		frame.setRawFrame(frameBytes);

		LOG.debug("Interpreted frame: " + frame);
		
		return frame;
	}

	private String unmaskPayloadData(byte[] payloadData, byte[] maskingKey) {
		byte[] unmaskedBytes = new byte[payloadData.length];
		for(int i=0; i<payloadData.length; i++) {
			byte unmaskedByte = (byte) (payloadData[i] ^ maskingKey[i % 4]);
			unmaskedBytes[i] = unmaskedByte;
		}
		return new String(unmaskedBytes);
	}

	private byte[] getPayloadData(byte[] frame, int payloadLen, ByteIndex byteIndex) {
		byte[] payload = new byte[payloadLen];
		for(int i=0; i<payloadLen; i++) {
			payload[i] = frame[byteIndex.increment()];
		}
		return payload;
	}

	private byte[] getMaskingKey(byte[] frame, ByteIndex byteIndex) {
		byte[] maskingKey = new byte[4]; 
		for(int i=0; i<4; i++) {
			maskingKey[i] = frame[byteIndex.increment()];
		}
		return maskingKey;
	}

	private byte getOpCode(byte firstByte) {
		byte mask = Byte.parseByte("00001111", 2);
		return (byte) (firstByte & mask);
	}

	/**
	 * The length of the payload data, in bytes: if 0-125, that is the
	 * payload length.  If 126, the following 2 bytes interpreted as a 16
     * bit unsigned integer are the payload length.  If 127, the
     * following 8 bytes interpreted as a 64-bit unsigned integer (the
     * most significant bit MUST be 0) are the payload length.  Multibyte
     * length quantities are expressed in network byte order.  The
     * payload length is the length of the extension data + the length of
     * the application data.  The length of the extension data may be
     * zero, in which case the payload length is the length of the
     * application data.
	 * 
	 */
	private int getPayloadLength(byte[] frame, ByteIndex byteIndex) {
		byte mask = Byte.parseByte("01111111", 2);
		byte payloadLength = (byte) (frame[1] & mask);
		
		if(payloadLength <= 125 && payloadLength >= 0) {
			return payloadLength;
		}
		else if(payloadLength == 126) {
			return getPayloadLengthFromTwoBytes(frame, byteIndex);
		} else if(payloadLength == 127) {
			return getPayloadLengthFromEightBytes(frame, byteIndex);
		}
		
		throw new RuntimeException("Payload length has bad value: " + payloadLength);
	}

	private int getPayloadLengthFromEightBytes(byte[] frame, ByteIndex byteIndex) {
		String bitPattern = BitPatternUtil.createBitPattern(
				frame[byteIndex.increment()], 
				frame[byteIndex.increment()], 
				frame[byteIndex.increment()], 
				frame[byteIndex.increment()], 
				frame[byteIndex.increment()], 
				frame[byteIndex.increment()], 
				frame[byteIndex.increment()], 
				frame[byteIndex.increment()]);
		long payloadLengthLong = Long.parseLong(bitPattern, 2);
		
		if(payloadLengthLong > Integer.MAX_VALUE) {
			LOG.error("Payload length is too long...");
		}
		
		return (int) payloadLengthLong;
	}

	private int getPayloadLengthFromTwoBytes(byte[] frame, ByteIndex byteIndex) {
		String bitPattern = BitPatternUtil.createBitPattern(
				frame[byteIndex.increment()], 
				frame[byteIndex.increment()]);
		return Integer.parseInt(bitPattern, 2);
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
	
	private static class ByteIndex {
		private int byteIndex;
		
		public ByteIndex(int startValue) {
			this.byteIndex = startValue;
		}
		
		public int increment() {
			return byteIndex++;
		}
	}

}
