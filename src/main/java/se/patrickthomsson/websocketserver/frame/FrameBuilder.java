package se.patrickthomsson.websocketserver.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrameBuilder {
	
	@Autowired
	private MaskingKeyGenerator maskingKeyGenerator;
	
	//TODO implement creation of unmasked frames
	public Frame buildUnmaskedFrame(String message) {
	    Frame frame = Frame.builder()
                .finalFrame(true)
                .rsv1(false)
                .rsv2(false)
                .rsv3(false)
                .type(FrameType.TEXT)
                .masked(false)
                .payloadLength(message.getBytes().length)
                .maskingKey(maskingKeyGenerator.generate())
                .unmaskedData(message)
                .build();
        return frame;
	}

	public Frame buildMaskedFrame(String message) {
		Frame frame = Frame.builder()
				.finalFrame(true)
				.rsv1(false)
				.rsv2(false)
				.rsv3(false)
				.type(FrameType.TEXT)
				.masked(true)
				.payloadLength(message.getBytes().length)
				.maskingKey(maskingKeyGenerator.generate())
				.unmaskedData(message)
				.build();
		return frame;
	}
	
}
