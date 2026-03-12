import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class CryptoManagerTest {
	
	    @Test
	    void testIsStringInBoundsValid() {
	        assertTrue(CryptoManager.isStringInBounds("HELLO"));
	    }

	    @Test
	    void testIsStringInBoundsInvalid() {
	        assertFalse(CryptoManager.isStringInBounds("hello"));
	    }

	    @Test
	    void testCaesarEncryption() {
	        String result = CryptoManager.caesarEncryption("ABC", 3);
	        assertEquals("DEF", result);
	    }

	    @Test
	    void testCaesarDecryption() {
	        String encrypted = CryptoManager.caesarEncryption("HELLO", 5);
	        String decrypted = CryptoManager.caesarDecryption(encrypted, 5);

	        assertEquals("HELLO", decrypted);
	    }

	    @Test
	    void testVigenereEncryptionDecryption() {
	        String encrypted = CryptoManager.vigenereEncryption("HELLO", "KEY");
	        String decrypted = CryptoManager.vigenereDecryption(encrypted, "KEY");

	        assertEquals("HELLO", decrypted);
	    }

	    @Test
	    void testPlayfairEncryptionDecryption() {
	        String encrypted = CryptoManager.playfairEncryption("PLAYFAIR", "MONTGOMERY");
	        String decrypted = CryptoManager.playfairDecryption(encrypted, "MONTGOMERY");

	        assertEquals("PLAYFAIR", decrypted);
	    }

	    @Test
	    void testInvalidBounds() {
	        String result = CryptoManager.caesarEncryption("hello", 3);

	        assertEquals("The selected string is not in bounds, Try again.", result);
	    }
	    
	}
