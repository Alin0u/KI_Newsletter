package kgn.util;

import lombok.experimental.UtilityClass;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Utility class for key conversion operations.
 * Provides methods to convert string representations of RSA keys into their respective RSA key objects.
 */
@UtilityClass
public class KeyUtils {

    /**
     * Converts a PEM-formatted RSA public key string into an RSAPublicKey object.
     *
     * @param pem The PEM string representing the public key.
     * @return RSAPublicKey The converted RSA public key.
     * @throws Exception if there is an error during the conversion process.
     */
    public static RSAPublicKey convertPublicKey(String pem) throws Exception {
        String publicKeyPEM = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");  // Remove all whitespace characters

        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

    /**
     * Converts a PEM-formatted RSA private key string into an RSAPrivateKey object.
     *
     * @param pem The PEM string representing the private key.
     * @return RSAPrivateKey The converted RSA private key.
     * @throws Exception if there is an error during the conversion process.
     */
    public static RSAPrivateKey convertPrivateKey(String pem) throws Exception {
        String privateKeyPEM = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }
}
