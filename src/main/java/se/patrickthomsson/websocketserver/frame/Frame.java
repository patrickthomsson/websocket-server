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

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
        }

    }

}
