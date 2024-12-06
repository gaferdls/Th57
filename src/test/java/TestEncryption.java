import org.junit.jupiter.api.Test;

import static database.DatabaseEncryption.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEncryption {

    @Test
    public void testEncryption1() {
        String text = "Hello, world!";
        String encryptedText = encrypt(text);
        System.out.println("Encrypted text for \"" + text + "\" is \"" + encryptedText + "\"");
        assertEquals("Hello, world!", decrypt(encryptedText));
    }

    @Test
    public void testEncryption2() {
        String text = "1234567890";
        String encryptedText = encrypt(text);
        System.out.println("Encrypted text for \"" + text + "\" is \"" + encryptedText + "\"");
        assertEquals("1234567890", decrypt(encryptedText));
    }

    @Test
    public void testEncryption3() {
        String text = "This text is encrypted! Woah!1!!?!1";
        String encryptedText = encrypt(text);
        System.out.println("Encrypted text for \"" + text + "\" is \"" + encryptedText + "\"");
        assertEquals("This text is encrypted! Woah!1!!?!1", decrypt(encryptedText));
    }

    @Test
    public void testEncryption4() {
        String text = "ksadjhf9i2e723894q&^&^%*&^)(";
        String encryptedText = encrypt(text);
        System.out.println("Encrypted text for \"" + text + "\" is \"" + encryptedText + "\"");
        assertEquals("ksadjhf9i2e723894q&^&^%*&^)(", decrypt(encryptedText));
    }

}
