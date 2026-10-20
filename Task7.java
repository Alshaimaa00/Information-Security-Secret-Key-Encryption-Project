

import java.io.File;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Task7 {

    public static void main(String[] args) {
        // Start the key guessing process
        performKeyGuessing();
    }

    static public void performKeyGuessing() {
        try {
            // Read the dictionary file containing potential keys
            Scanner fileScanner = new Scanner(new File("words.txt"));
            
            while (fileScanner.hasNextLine()) {
                String keyCandidate = fileScanner.nextLine().trim(); // Read and trim the key

                // Ensure the key is 16 bytes long
                if (keyCandidate.length() < 16) {
                    while (keyCandidate.length() < 16) {
                        keyCandidate += "#"; // Pad the key with #
                    }
                }

                // Prepare inputs for encryption
                String plainText = "This is a top secret.";
                byte[] ivBytes = convertHexToBytes("aabbccddeeff00998877665544332211");

                // Encrypt the plaintext using the current key candidate
                byte[] encryptedBytes = encryptAES(plainText, ivBytes, keyCandidate);

                if (encryptedBytes != null) {
                    String encryptedHex = convertBytesToHex(encryptedBytes);

                    if (encryptedHex.equalsIgnoreCase("764aa26b55a4da654df6b19e4bce00f4ed05e09346fb0e762583cb7da2ac93a2")) {
                        System.out.println("Key found! Decryption process started...");

                        // Decrypt the text using the found key
                        String decryptedText = decryptAES(encryptedBytes, ivBytes, keyCandidate);

                        if (decryptedText != null) {
                            System.out.println("Decrypted Text: " + decryptedText);
                            System.out.println("Key (ASCII): " + keyCandidate);
                            System.out.println("Key (Hex): " + convertBytesToHex(keyCandidate.getBytes()));
                            System.out.println("Encrypted Text (Hex): " + encryptedHex);
                        } else {
                            System.err.println("Decryption failed.");
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Convert a hex string to a byte array
    public static byte[] convertHexToBytes(String hex) {
        int length = hex.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    // AES encryption method
    public static byte[] encryptAES(String plaintext, byte[] iv, String key) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(plaintext.getBytes());
        } catch (Exception e) {
            // Suppressed exception for cleaner output
        }
        return null;
    }

    // AES decryption method
    public static String decryptAES(byte[] encrypted, byte[] iv, String key) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(cipher.doFinal(encrypted));
        } catch (Exception e) {
            // Suppressed exception for cleaner output
        }
        return null;
    }

    // Convert a byte array to a hex string
    public static String convertBytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

