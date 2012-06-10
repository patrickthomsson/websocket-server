package se.patrickthomsson.websocketserver.frame;

import java.io.UnsupportedEncodingException;

import se.patrickthomsson.util.BitPatternUtil;
import se.patrickthomsson.websocketserver.exception.WebSocketServerException;

public class RawFrame {

    private static final String ZERO = "0";
    private static final String ONE = "1";
    private static final String CHAR_SET = "UTF-8";

    private final Frame frame;

    public RawFrame(final Frame frame) {
        this.frame = frame;
    }

    // TODO support long frames
    public byte[] buildFromFrame() {
        final byte firstByte = buildFirstByte();
        final byte[] payloadLengthBytes = buildPayloadDataLengthBytes();
        final byte[] payloadData = buildPayloadData();
        return buildRawFrame(firstByte, payloadLengthBytes, payloadData);
    }

    private byte[] buildRawFrame(final byte firstByte, final byte[] payloadLengthBytes, final byte[] payloadData) {
        final int numBytes = 1 + payloadLengthBytes.length + (frame.isMasked() ? frame.getMaskingKey().length : 0) + payloadData.length;

        final byte[] rawFrame = new byte[numBytes];
        int index = 0;
        rawFrame[index++] = firstByte;

        for (int i = 0; i < payloadLengthBytes.length; i++) {
            rawFrame[index++] = payloadLengthBytes[i];
        }
        if (frame.isMasked()) {
            for (int i = 0; i < frame.getMaskingKey().length; i++) {
                rawFrame[index++] = frame.getMaskingKey()[i];
            }
        }
        for (int i = 0; i < payloadData.length; i++) {
            rawFrame[index++] = payloadData[i];
        }
        return rawFrame;
    }

    private byte[] buildPayloadData() {
        if (frame.isMasked()) {
            return buildMaskedPayloadData(frame.getUnmaskedData(), frame.getMaskingKey());
        }
        return getUnmaskedDataAsUtf8();
    }

    private byte[] buildMaskedPayloadData(final String unmaskedData, final byte[] maskingKey) {
        byte[] payloadData = unmaskedData.getBytes();
        byte[] maskedPayloadData = new byte[payloadData.length];
        for (int i = 0; i < payloadData.length; i++) {
            maskedPayloadData[i] = (byte) (payloadData[i] ^ maskingKey[i % 4]);
        }
        return maskedPayloadData;
    }

    private byte[] buildPayloadDataLengthBytes() {
        int payloadLength = getUnmaskedDataAsUtf8().length;
        byte payloadLengthByte = (byte) payloadLength;
        if (payloadLength <= 125) {
            if (frame.isMasked()) {
                // set highest order bit to 1, to indicate that the frame is
                // masked
                payloadLengthByte |= (byte) 128;
            }
        } else {
            throw new WebSocketServerException("Payload length was too long, not supported yet... length was " + payloadLength);
        }
        return new byte[] { payloadLengthByte };
    }

    private byte[] getUnmaskedDataAsUtf8() {
        try {
            return frame.getUnmaskedData().getBytes(CHAR_SET);
        } catch (UnsupportedEncodingException e) {
            throw new WebSocketServerException("Could not convert to UTF-8: " + frame.getUnmaskedData(), e);
        }
    }

    private byte buildFirstByte() {
        StringBuilder firstByteBitPattern = new StringBuilder();
        firstByteBitPattern.append(frame.isFinalFrame() ? ONE : ZERO);
        firstByteBitPattern.append(frame.isRsv1() ? ONE : ZERO);
        firstByteBitPattern.append(frame.isRsv2() ? ONE : ZERO);
        firstByteBitPattern.append(frame.isRsv3() ? ONE : ZERO);
        firstByteBitPattern.append(buildOpcodeBitPattern(frame.getType()));
        byte firstByte = (byte) Integer.parseInt(firstByteBitPattern.toString(), 2);
        return firstByte;
    }

    private String buildOpcodeBitPattern(final FrameType type) {
        String bitPattern = BitPatternUtil.createBitPattern(type.getOpCode());
        return bitPattern.substring(4);
    }

}
