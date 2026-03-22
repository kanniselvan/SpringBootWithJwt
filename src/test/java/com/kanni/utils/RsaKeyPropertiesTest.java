import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.PrivateKey;

public class RsaKeyPropertiesTest {

    private RsaKeyProperties rsaKeyProperties;
    private KeyPair keyPair;

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
        rsaKeyProperties = new RsaKeyProperties(keyPair.getPublic(), keyPair.getPrivate());
    }

    @Test
    public void testConstructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RsaKeyProperties(null, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new RsaKeyProperties(keyPair.getPublic(), null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new RsaKeyProperties(null, keyPair.getPrivate());
        });
    }

    @Test
    public void testGetPublicKey() {
        PublicKey publicKey = rsaKeyProperties.getPublicKey();
        assertNotNull(publicKey);
        assertEquals(keyPair.getPublic(), publicKey);
    }

    @Test
    public void testGetPrivateKey() {
        PrivateKey privateKey = rsaKeyProperties.getPrivateKey();
        assertNotNull(privateKey);
        assertEquals(keyPair.getPrivate(), privateKey);
    }
}