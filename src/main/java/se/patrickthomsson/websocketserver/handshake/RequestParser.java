package se.patrickthomsson.websocketserver.handshake;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class RequestParser {

	private static final Logger LOG = Logger.getLogger(RequestParser.class);

	private static final String LF = "\r\n";
	private static final String PATH_REGEXP = "/(\\w*(\\.\\w*)? )";

	public HandshakeRequest parse(byte[] request) {
		String handshakeRequest = new String(request);
		String path = parsePath(handshakeRequest);
		Map<String, String> fields = parseFields(handshakeRequest);
		String randomBits = parseRandomBits(handshakeRequest);
		return new HandshakeRequest(path, fields, randomBits);
	}

	private String parsePath(String handshakeRequest) {
		Pattern pattern = Pattern.compile(PATH_REGEXP);
		Matcher matcher = pattern.matcher(handshakeRequest);
		String path = matcher.find() ? matcher.group(0).trim() : null;
		LOG.debug("Parsed path: " + path);
		return path;
	}

	private Map<String, String> parseFields(String handshakeRequest) {
		HashMap<String, String> fields = new HashMap<String, String>();
		for (String line : handshakeRequest.split(LF)) {
			String[] keyAndValue = line.split(": ");
			if (isField(keyAndValue)) {
				fields.put(keyAndValue[0], keyAndValue[1]);
			}
		}
		LOG.debug("Parsed fields: " + fields);
		return fields;
	}

	private boolean isField(String[] keyAndValue) {
		return keyAndValue.length == 2;
	}

	private String parseRandomBits(String handshakeRequest) {
		String[] split = handshakeRequest.split(LF + LF);
		String randomBits = split.length > 1 ? split[1] : null;
		LOG.debug("Parsed random bits: " + randomBits);
		return randomBits;
	}

}
