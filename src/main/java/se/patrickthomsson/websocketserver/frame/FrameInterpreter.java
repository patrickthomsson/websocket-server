package se.patrickthomsson.websocketserver.frame;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import se.patrickthomsson.util.BitManipulationUtil;
import se.patrickthomsson.websocketserver.exception.WebSocketServerException;

@Service
public class FrameInterpreter {

	private static final Logger LOG = Logger.getLogger(FrameInterpreter.class);

	public Frame interpret(byte[] frameBytes) {
		ByteIndex byteIndex = new ByteIndex(2);

		boolean frameFin = BitManipulationUtil.isFirstBitSet(frameBytes[0]);
		boolean frameRsv1 = BitManipulationUtil.isSecondBitSet(frameBytes[0]);
		boolean frameRsv2 = BitManipulationUtil.isThirdBitSet(frameBytes[0]);
		boolean frameRsv3 = BitManipulationUtil.isFourthBitSet(frameBytes[0]);

		byte opCode = getOpCode(frameBytes[0]);
		boolean masked = BitManipulationUtil.isFirstBitSet(frameBytes[1]);

		int payloadLen = getPayloadLength(frameBytes, byteIndex);
		byte[] maskingKey = getMaskingKey(frameBytes, byteIndex);
		byte[] payloadData = getPayloadData(frameBytes, payloadLen, byteIndex);
		String unmaskedPayloadData = unmaskPayloadData(payloadData, maskingKey);

		Frame frame = Frame.builder()
				.finalFrame(frameFin)
				.rsv1(frameRsv1)
				.rsv2(frameRsv2)
				.rsv3(frameRsv3)
				.type(FrameType.fromOpCode(opCode))
				.masked(masked)
				.payloadLength(payloadLen)
				.maskingKey(maskingKey)
				.unmaskedData(unmaskedPayloadData)
				.rawFrame(frameBytes)
				.build();

		LOG.debug("Interpreted frame: " + frame);
		LOG.debug(String.format("Interpreted data as: [%s]", unmaskedPayloadData));

		return frame;
	}

	private String unmaskPayloadData(byte[] payloadData, byte[] maskingKey) {
		byte[] unmaskedBytes = new byte[payloadData.length];
		for (int i = 0; i < payloadData.length; i++) {
			byte unmaskedByte = (byte) (payloadData[i] ^ maskingKey[i % 4]);
			unmaskedBytes[i] = unmaskedByte;
		}
		return new String(unmaskedBytes);
	}

	private byte[] getPayloadData(byte[] frame, int payloadLen, ByteIndex byteIndex) {
		byte[] payload = new byte[payloadLen];
		for (int i = 0; i < payloadLen; i++) {
			payload[i] = frame[byteIndex.next()];
		}
		return payload;
	}

	private byte[] getMaskingKey(byte[] frame, ByteIndex byteIndex) {
		byte[] maskingKey = new byte[4];
		for (int i = 0; i < 4; i++) {
			maskingKey[i] = frame[byteIndex.next()];
		}
		return maskingKey;
	}

	private byte getOpCode(byte firstByte) {
		return BitManipulationUtil.unsetFourHighestOrderBits(firstByte);
	}

	/**
	 * The length of the payload data, in bytes: if 0-125, that is the payload
	 * length. If 126, the following 2 bytes interpreted as a 16 bit unsigned
	 * integer are the payload length. If 127, the following 8 bytes interpreted
	 * as a 64-bit unsigned integer (the most significant bit MUST be 0) are the
	 * payload length. Multibyte length quantities are expressed in network byte
	 * order. The payload length is the length of the extension data + the
	 * length of the application data. The length of the extension data may be
	 * zero, in which case the payload length is the length of the application
	 * data.
	 */
	private int getPayloadLength(byte[] frame, ByteIndex byteIndex) {
		byte payloadLength = BitManipulationUtil.unsetEighthBit(frame[1]);

		if (payloadLength <= 125 && payloadLength >= 0) {
			return payloadLength;
		} else if (payloadLength == 126) {
			return getPayloadLengthFromTwoBytes(frame, byteIndex);
		} else if (payloadLength == 127) {
			return getPayloadLengthFromEightBytes(frame, byteIndex);
		}

		throw new WebSocketServerException("Payload length has bad value: " + payloadLength);
	}

	private int getPayloadLengthFromEightBytes(byte[] frame, ByteIndex byteIndex) {
		byte[] bytes = new byte[8];
		for(int i=0; i<bytes.length; i++) {
			bytes[i] = frame[byteIndex.next()];
		}
		
		long payloadLength = BitManipulationUtil.consecutiveBytesAsLong(bytes);

		if (payloadLength > Integer.MAX_VALUE) {
			LOG.error("Payload length is too long...");
		}

		return (int) payloadLength;
	}

	private int getPayloadLengthFromTwoBytes(byte[] frame, ByteIndex byteIndex) {
		byte[] bytes = new byte[2];
		for(int i=0; i<bytes.length; i++) {
			bytes[i] = frame[byteIndex.next()];
		}
		long payloadLength = BitManipulationUtil.consecutiveBytesAsLong(bytes);
		
		return (int) payloadLength;
	}

	private static class ByteIndex {
		private int byteIndex;

		public ByteIndex(int startValue) {
			this.byteIndex = startValue;
		}

		public int next() {
			return byteIndex++;
		}
	}

}
