package se.patrickthomsson.websocketserver.frame;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Frame {

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

	public boolean isFinalFrame() {
		return finalFrame;
	}

	public void setFinalFrame(boolean finalFrame) {
		this.finalFrame = finalFrame;
	}

	public boolean isRsv1() {
		return rsv1;
	}

	public void setRsv1(boolean rsv1) {
		this.rsv1 = rsv1;
	}

	public boolean isRsv2() {
		return rsv2;
	}

	public void setRsv2(boolean rsv2) {
		this.rsv2 = rsv2;
	}

	public boolean isRsv3() {
		return rsv3;
	}

	public void setRsv3(boolean rsv3) {
		this.rsv3 = rsv3;
	}

	public FrameType getType() {
		return type;
	}

	public void setType(FrameType type) {
		this.type = type;
	}

	public boolean isMasked() {
		return masked;
	}

	public void setMasked(boolean masked) {
		this.masked = masked;
	}

	public int getPayloadLength() {
		return payloadLength;
	}

	public void setPayloadLength(int payloadLength) {
		this.payloadLength = payloadLength;
	}
	
	public byte[] getMaskingKey() {
		return maskingKey;
	}

	public void setMaskingKey(byte[] maskingKey) {
		this.maskingKey = maskingKey;
	}

	public String getUnmaskedData() {
		return unmaskedData;
	}

	public void setUnmaskedData(String unmaskedData) {
		this.unmaskedData = unmaskedData;
	}
	
	public byte[] getRawFrame() {
		return rawFrame;
	}

	public void setRawFrame(byte[] rawFrame) {
		this.rawFrame = rawFrame;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
