package se.patrickthomsson.websocketserver.frame;

import org.apache.commons.lang.NotImplementedException;

import se.patrickthomsson.util.BitPatternUtil;

public class FrameBuilder {
	
	private MaskingKeyGenerator maskingKeyGenerator = new MaskingKeyGenerator();
	
	//TODO implement creation of unmasked frames
	public Frame buildUnmaskedFrame(String message) {
		throw new NotImplementedException("Unmasked frames not supported!");
	}

	public Frame buildMaskedFrame(String message) {
		Frame frame = new Frame();
		frame.setFinalFrame(true);
		frame.setRsv1(false);
		frame.setRsv2(false);
		frame.setRsv3(false);
		frame.setType(FrameType.TEXT);
		frame.setMasked(true);
		frame.setPayloadLength(message.getBytes().length);
		frame.setMaskingKey(maskingKeyGenerator.generate());
		frame.setUnmaskedData(message);
		frame.setRawFrame(buildRawFrame(frame));
		return frame;
	}
	
	//TODO support 1) unmasked frames 2) long frames
	private byte[] buildRawFrame(Frame frame) {
		byte firstByte = buildFirstByte(frame);
		byte[] payloadLengthBytes = buildPayloadDataLengthBytes(frame);
		byte[] maskingKey = maskingKeyGenerator.generate();
		byte[] maskedPayloadData = buildMaskedPayloadData(frame.getUnmaskedData(), maskingKey);

		int numBytes = 1 + payloadLengthBytes.length + maskingKey.length + maskedPayloadData.length;
		
		byte[] rawFrame = new byte[numBytes];
		int index = 0;
		rawFrame[index++] = firstByte;
		
		for(int i=0; i<payloadLengthBytes.length; i++) {
			rawFrame[index++] = payloadLengthBytes[i];
		}
		for(int i=0; i<maskingKey.length; i++) {
			rawFrame[index++] = maskingKey[i];
		}
		for(int i=0; i<maskedPayloadData.length; i++) {
			rawFrame[index++] = maskedPayloadData[i];
		}
		return rawFrame;
	}

	private byte[] buildMaskedPayloadData(String unmaskedData, byte[] maskingKey) {
		byte[] payloadData = unmaskedData.getBytes();
		byte[] maskedPayloadData = new byte[payloadData.length];
		for(int i=0; i<payloadData.length; i++) {
			maskedPayloadData[i] = (byte) (payloadData[i] ^ maskingKey[i % 4]);
		}
		return maskedPayloadData;
	}

	private byte[] buildPayloadDataLengthBytes(Frame frame) {
		int payloadLength = frame.getUnmaskedData().length();
		byte payloadLengthByte = (byte) payloadLength;
		if(payloadLength <= 125) {
			if(frame.isMasked()) {
				//set highest order bit to 1
				payloadLengthByte |= (byte) 128;
			}
		} else {
			throw new RuntimeException("payload length unsupported... was " + payloadLength);
		}
		return new byte[] { payloadLengthByte };
	}

	private byte buildFirstByte(Frame frame) {
		StringBuilder firstByteBitPattern = new StringBuilder();
		firstByteBitPattern.append(frame.isFinalFrame() ? "1" : "0");
		firstByteBitPattern.append(frame.isRsv1() ? "1" : "0");
		firstByteBitPattern.append(frame.isRsv2() ? "1" : "0");
		firstByteBitPattern.append(frame.isRsv3() ? "1" : "0");
		firstByteBitPattern.append(buildOpcodeBitPattern(frame.getType()));
		byte firstByte = (byte) Integer.parseInt(firstByteBitPattern.toString(), 2);
		return firstByte;
	}

	private String buildOpcodeBitPattern(FrameType type) {
		String bitPattern = BitPatternUtil.createBitPattern(type.getOpCode());
		return bitPattern.substring(4);
	}

}
