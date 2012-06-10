package se.patrickthomsson.websocketserver.handshake;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import se.patrickthomsson.util.Encrypter;

@Service
public class ChallengeCalculator {

    private static final Logger LOG = Logger
            .getLogger(ChallengeCalculator.class);

    private static final String VERSION_6_SUFFIX = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private static final String SPACE = "\u0020";

    public String calculateVersion6Challenge(String secWebSocketKey) {
        LOG.debug("Calculating version 6 challenge from secWebSocketKey: "
                + secWebSocketKey);
        String base = secWebSocketKey + VERSION_6_SUFFIX;
        byte[] encrypted = Encrypter.sha1(base.getBytes());
        byte[] base64Encoded = Base64.encodeBase64(encrypted);
        return new String(base64Encoded);
    }

    public String calculateVersion1Challenge(String key1, String key2, String randomBits) {
        LOG.debug(String.format("key1: '%s', key2: '%s', randomBit: '%s'",
                key1, key2, randomBits));

        byte[] part1 = getBytes(key1);
        byte[] part2 = getBytes(key2);
        byte[] part3 = randomBits.getBytes();

        byte[] challenge = new byte[16];
        challenge[0] = part1[0];
        challenge[1] = part1[1];
        challenge[2] = part1[2];
        challenge[3] = part1[3];

        challenge[4] = part2[0];
        challenge[5] = part2[1];
        challenge[6] = part2[2];
        challenge[7] = part2[3];

        challenge[8] = part3[0];
        challenge[9] = part3[1];
        challenge[10] = part3[2];
        challenge[11] = part3[3];
        challenge[12] = part3[4];
        challenge[13] = part3[5];
        challenge[14] = part3[6];
        challenge[15] = part3[7];

        return new String(Encrypter.md5(challenge));
    }

    private byte[] getBytes(String key) {
        return doBitwiseOperations(extractNumber(key) / countSpaces(key));
    }

    private byte[] doBitwiseOperations(long key) {
        return new byte[] { (byte) (key >> 24), (byte) ((key << 8) >> 24),
                (byte) ((key << 16) >> 24), (byte) ((key << 24) >> 24) };
    }

    protected Long extractNumber(String key) {
        String onlyDigits = extractDigits(key);
        return Long.valueOf(onlyDigits);
    }

    private String extractDigits(String key) {
        return key.replaceAll("[^\\d]", "");
    }

    protected int countSpaces(String key) {
        return key.split(SPACE).length - 1;
    }

}
