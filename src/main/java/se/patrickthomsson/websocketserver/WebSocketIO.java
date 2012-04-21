package se.patrickthomsson.websocketserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class WebSocketIO {
	
	private static final Logger LOG = Logger.getLogger(WebSocketIO.class);
	private final ByteBuffer buffer = ByteBuffer.allocate(16384);

	public void write(byte[] response, SocketChannel sc) throws IOException {
		sc.write(ByteBuffer.wrap(response));
		LOG.trace("RESPONDING: \n" + new String(response).replaceAll("\\r\\n", "<CR><LF>\n"));
	}
	
	public byte[] read(SocketChannel socketChannel) {
		try {
			buffer.clear();
			int read = socketChannel.read(buffer);
			
			if(read != -1) {
				String input = bufferAsString().substring(0, read);
				LOG.trace("INPUT IS:\n" + input.replaceAll("\\r\\n", "<CR><LF>\n"));
				return readBytesFromBuffer(read);
			}
			else {
				LOG.info("Nothing to read... Connection will be closed");
			}
		}
		catch(Exception e) {
			LOG.error("Read failed: ", e);
		}
		return new byte[0];
	}

	private byte[] readBytesFromBuffer(int numberOfBytesToRead) {
		byte[] bytesToRead = new byte[numberOfBytesToRead];
		byte[] allBytesInBuffer = buffer.array();
		for(int i=0; i<numberOfBytesToRead; i++) {
			bytesToRead[i] = allBytesInBuffer[i];
		}
		return bytesToRead;
	}

	private String bufferAsString() {
		return new String(buffer.array());
	}

}
