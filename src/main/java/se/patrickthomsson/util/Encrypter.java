package se.patrickthomsson.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class Encrypter {
	
	private static final Logger LOG = Logger.getLogger(Encrypter.class);
	
	private static final String ALGORITHM_SHA1 = "SHA-1";
	private static final String ALGORITHM_MD5 = "MD5";

	public static byte[] md5(byte[] unencrypted) {
		return encrypt(ALGORITHM_MD5, unencrypted);
	}

	public static byte[] sha1(byte[] unencrypted) {
		return encrypt(ALGORITHM_SHA1, unencrypted);
	}

	private static byte[] encrypt(String algorithm, byte[] unencrypted) {
		try {
			return MessageDigest.getInstance(algorithm).digest(unencrypted);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("No such algorithm: " + algorithm, e);
		}
		return new byte[0];
	}
	
}
