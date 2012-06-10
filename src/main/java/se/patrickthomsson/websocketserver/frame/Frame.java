package se.patrickthomsson.websocketserver.frame;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Frame {

    private final Builder builder;

    private Frame(final Builder builder) {
        this.builder = builder;
    }

    public boolean isFinalFrame() {
        return builder.finalFrame;
    }

    public boolean isRsv1() {
        return builder.rsv1;
    }

    public boolean isRsv2() {
        return builder.rsv2;
    }

    public boolean isRsv3() {
        return builder.rsv3;
    }

    public FrameType getType() {
        return builder.type;
    }

    public boolean isMasked() {
        return builder.masked;
    }

    public int getPayloadLength() {
        return builder.payloadLength;
    }

    public byte[] getMaskingKey() {
        return builder.maskingKey;
    }

    public String getUnmaskedData() {
        return builder.unmaskedData;
    }

    public byte[] getRawFrame() {
        return builder.rawFrame;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private boolean finalFrame;
        private boolean rsv1;
        private boolean rsv2;
        private boolean rsv3;
        private FrameType type;
        private boolean masked;
        private int payloadLength;
        private byte[] maskingKey;
        private String unmaskedData;
        private byte[] rawFrame;

        public Frame build() {
            Frame frame = new Frame(this);
            if (rawFrame == null) {
                rawFrame = new RawFrame(frame).buildFromFrame();
            }
            return frame;
        }

        public Builder finalFrame(boolean finalFrame) {
            this.finalFrame = finalFrame;
            return this;
        }

        public Builder rsv1(boolean rsv1) {
            this.rsv1 = rsv1;
            return this;
        }

        public Builder rsv2(boolean rsv2) {
            this.rsv2 = rsv2;
            return this;
        }

        public Builder rsv3(boolean rsv3) {
            this.rsv3 = rsv3;
            return this;
        }

        public Builder type(FrameType type) {
            this.type = type;
            return this;
        }

        public Builder masked(boolean masked) {
            this.masked = masked;
            return this;
        }

        public Builder payloadLength(int payloadLength) {
            this.payloadLength = payloadLength;
            return this;
        }

        public Builder maskingKey(byte[] maskingKey) {
            this.maskingKey = maskingKey;
            return this;
        }

        public Builder unmaskedData(String unmaskedData) {
            this.unmaskedData = unmaskedData;
            return this;
        }

        public Builder rawFrame(byte[] rawFrame) {
            this.rawFrame = rawFrame;
            return this;
        }

        // // TODO support long frames
        // private byte[] buildRawFrame(Frame frame) {
        // final byte firstByte = buildFirstByte(frame);
        // final byte[] payloadLengthBytes = buildPayloadDataLengthBytes(frame);
        // final byte[] payloadData = buildPayloadData(frame);
        // return buildRawFrame(firstByte, payloadLengthBytes, payloadData);
        // }
        //
        // private byte[] buildRawFrame(final byte firstByte, final byte[]
        // payloadLengthBytes, final byte[] payloadData) {
        // final int numBytes = 1 + payloadLengthBytes.length + (masked ?
        // maskingKey.length : 0) + payloadData.length;
        //
        // final byte[] rawFrame = new byte[numBytes];
        // int index = 0;
        // rawFrame[index++] = firstByte;
        //
        // for (int i = 0; i < payloadLengthBytes.length; i++) {
        // rawFrame[index++] = payloadLengthBytes[i];
        // }
        // if (masked) {
        // for (int i = 0; i < maskingKey.length; i++) {
        // rawFrame[index++] = maskingKey[i];
        // }
        // }
        // for (int i = 0; i < payloadData.length; i++) {
        // rawFrame[index++] = payloadData[i];
        // }
        // return rawFrame;
        // }
        //
        // private byte[] buildPayloadData(Frame frame) {
        // if (masked) {
        // return buildMaskedPayloadData(frame.getUnmaskedData(), maskingKey);
        // }
        // return getUnmaskedDataAsUtf8(frame);
        // }
        //
        // private byte[] buildMaskedPayloadData(String unmaskedData, byte[]
        // maskingKey) {
        // byte[] payloadData = unmaskedData.getBytes();
        // byte[] maskedPayloadData = new byte[payloadData.length];
        // for (int i = 0; i < payloadData.length; i++) {
        // maskedPayloadData[i] = (byte) (payloadData[i] ^ maskingKey[i % 4]);
        // }
        // return maskedPayloadData;
        // }
        //
        // private byte[] buildPayloadDataLengthBytes(Frame frame) {
        // int payloadLength = getUnmaskedDataAsUtf8(frame).length;
        // byte payloadLengthByte = (byte) payloadLength;
        // if (payloadLength <= 125) {
        // if (frame.isMasked()) {
        // // set highest order bit to 1, to indicate that the frame is
        // // masked
        // payloadLengthByte |= (byte) 128;
        // }
        // } else {
        // throw new
        // WebSocketServerException("Payload length was too long, not supported yet... length was "
        // + payloadLength);
        // }
        // return new byte[] { payloadLengthByte };
        // }
        //
        // private byte[] getUnmaskedDataAsUtf8(Frame frame) {
        // try {
        // return frame.getUnmaskedData().getBytes("UTF-8");
        // } catch (UnsupportedEncodingException e) {
        // throw new WebSocketServerException("Could not convert to UTF-8: " +
        // frame.getUnmaskedData(), e);
        // }
        // }
        //
        // private byte buildFirstByte(Frame frame) {
        // StringBuilder firstByteBitPattern = new StringBuilder();
        // firstByteBitPattern.append(frame.isFinalFrame() ? "1" : "0");
        // firstByteBitPattern.append(frame.isRsv1() ? "1" : "0");
        // firstByteBitPattern.append(frame.isRsv2() ? "1" : "0");
        // firstByteBitPattern.append(frame.isRsv3() ? "1" : "0");
        // firstByteBitPattern.append(buildOpcodeBitPattern(frame.getType()));
        // byte firstByte = (byte)
        // Integer.parseInt(firstByteBitPattern.toString(), 2);
        // return firstByte;
        // }
        //
        // private String buildOpcodeBitPattern(FrameType type) {
        // String bitPattern =
        // BitPatternUtil.createBitPattern(type.getOpCode());
        // return bitPattern.substring(4);
        // }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
        }

    }

}
