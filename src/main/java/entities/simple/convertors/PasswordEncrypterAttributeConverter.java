package entities.simple.convertors;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.Key;
import java.util.Base64;

/**
 * Encrypt / decrypt a password upon storing in the database
 */
public class PasswordEncrypterAttributeConverter implements AttributeConverter<String, String> {

    /**
     * The algorithm used for encryption
     */
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * The private KEY used for encryption
     */
    private static final byte[] KEY = "MySuperSecretKey".getBytes();

    @Override
    public String convertToDatabaseColumn(String value) {
        // do some encryption
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(c.doFinal(value.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String value) {
        // do some decryption
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64.getDecoder().decode(value)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}