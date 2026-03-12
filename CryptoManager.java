/**
 * This is a utility class that encrypts and decrypts a phrase using three
 * different approaches. 
 * 
 * The first approach is called the Vigenere Cipher.Vigenere encryption 
 * is a method of encrypting alphabetic text based on the letters of a keyword.
 * 
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph) 
 * at a time instead of just one.
 * 
 * The third approach is Caesar Cipher. It is a simple replacement cypher. 
 * 
 * @author Huseyin Aygun
 * @version 8/3/2025
 */

public class CryptoManager { 

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher  
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_ ";

    public static boolean isStringInBounds(String text)
    {
        for(int i = 0; i < text.length(); i++)
        {
            if(ALPHABET64.indexOf(text.charAt(i)) == -1)
                return false;
        }
        return true;
    }

	/**
	 * Vigenere Cipher is a method of encrypting alphabetic text 
	 * based on the letters of a keyword. It works as below:
	 * 		Choose a keyword (e.g., KEY).
	 * 		Repeat the keyword to match the length of the plaintext.
	 * 		Each letter in the plaintext is shifted by the position of the 
	 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
	 */   

    public static String vigenereEncryption(String plainText, String key)
    {
        if (!isStringInBounds(plainText) || !isStringInBounds(key))
            return "The selected string is not in bounds, Try again.";

        if (key.length() == 0)
            return "Key cannot be empty";

        String encrypt = "";

        for (int i = 0; i < plainText.length(); i++)
        {
            int plainIndex = ALPHABET64.indexOf(plainText.charAt(i));
            int keyIndex = ALPHABET64.indexOf(key.charAt(i % key.length()));

            int encryptedIndex = (plainIndex + keyIndex) % 64;

            encrypt += ALPHABET64.charAt(encryptedIndex);
        }

        return encrypt;
    }

    // Vigenere Decryption
    public static String vigenereDecryption(String encryptedText, String key)
    {
        if (!isStringInBounds(encryptedText) || !isStringInBounds(key))
            return "The selected string is not in bounds, Try again.";

        if (key.length() == 0)
            return "Key cannot be empty";

        String decrypt = "";

        for (int i = 0; i < encryptedText.length(); i++)
        {
            int encryptedIndex = ALPHABET64.indexOf(encryptedText.charAt(i));
            int keyIndex = ALPHABET64.indexOf(key.charAt(i % key.length()));

            int plainIndex = (encryptedIndex - keyIndex + 64) % 64;

            decrypt += ALPHABET64.charAt(plainIndex);
        }

        return decrypt;
    }


	/**
	 * Playfair Cipher encrypts two letters at a time instead of just one.
	 * It works as follows:
	 * A matrix (8X8 in our case) is built using a keyword
	 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
	 * Encryption rules depend on the positions of the letters in the matrix:
	 *     Same row: replace each letter with the one to its right.
	 *     Same column: replace each with the one below.
	 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
	 */    

    private static char[][] buildMatrix(String key)
    {
        char[][] matrix = new char[8][8];
        String used = "";

        key = key.toUpperCase();

        for(int i = 0; i < key.length(); i++)
        {
            char c = key.charAt(i);
            if(ALPHABET64.indexOf(c) != -1 && used.indexOf(c) == -1)
                used += c;
        }

        for(int i = 0; i < ALPHABET64.length(); i++)
        {
            char c = ALPHABET64.charAt(i);
            if(used.indexOf(c) == -1)
                used += c;
        }

        int index = 0;

        for(int r = 0; r < 8; r++)
            for(int c = 0; c < 8; c++)
                matrix[r][c] = used.charAt(index++);

        return matrix;
    }
    
    private static int[] findPosition(char[][] matrix, char target)
    {
        for(int r = 0; r < 8; r++)
            for(int c = 0; c < 8; c++)
                if(matrix[r][c] == target)
                    return new int[]{r,c};

        return null;
    }
    
    // Playfair Encryption
    public static String playfairEncryption(String plainText, String key)
    {
        if(!isStringInBounds(plainText) || !isStringInBounds(key))
            return "The selected string is not in bounds, Try again.";

        if(key.length() == 0)
            return "Key cannot be empty";

        char[][] matrix = buildMatrix(key);

        String encrypted = "";

        for(int i = 0; i < plainText.length(); i += 2)
        {
            char a = plainText.charAt(i);
            char b;

            if(i+1 < plainText.length())
                b = plainText.charAt(i+1);
            else
                b = 'X';

            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            int r1 = posA[0];
            int c1 = posA[1];
            int r2 = posB[0];
            int c2 = posB[1];

            if(r1 == r2) // same row
            {
                encrypted += matrix[r1][(c1+1)%8];
                encrypted += matrix[r2][(c2+1)%8];
            }
            else if(c1 == c2) // same column
            {
                encrypted += matrix[(r1+1)%8][c1];
                encrypted += matrix[(r2+1)%8][c2];
            }
            else // rectangle
            {
                encrypted += matrix[r1][c2];
                encrypted += matrix[r2][c1];
            }
        }

        return encrypted;
    }

    // Playfair Decryption
    public static String playfairDecryption(String encryptedText, String key)
    {
        if(!isStringInBounds(encryptedText) || !isStringInBounds(key))
            return "The selected string is not in bounds, Try again.";

        if(key.length() == 0)
            return "Key cannot be empty";

        char[][] matrix = buildMatrix(key);

        String decrypted = "";

        for(int i = 0; i < encryptedText.length(); i += 2)
        {
            char a = encryptedText.charAt(i);
            char b;

            if(i + 1 < encryptedText.length())
                b = encryptedText.charAt(i+1);
            else
                b = 'X';

            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            int r1 = posA[0];
            int c1 = posA[1];
            int r2 = posB[0];
            int c2 = posB[1];

            if(r1 == r2) // same row
            {
                decrypted += matrix[r1][(c1+7)%8];
                decrypted += matrix[r2][(c2+7)%8];
            }
            else if(c1 == c2) // same column
            {
                decrypted += matrix[(r1+7)%8][c1];
                decrypted += matrix[(r2+7)%8][c2];
            }
            else // rectangle
            {
                decrypted += matrix[r1][c2];
                decrypted += matrix[r2][c1];
            }
        }

        if(decrypted.endsWith("X"))
            decrypted = decrypted.substring(0, decrypted.length() - 1);

        return decrypted;
    }
    /**
     * Caesar Cipher is a simple substitution cipher that replaces each letter in a message 
     * with a letter some fixed number of positions down the alphabet. 
     * For example, with a shift of 3, 'A' would become 'D', 'B' would become 'E', and so on.
     */    
 
    public static String caesarEncryption(String plainText, int key)
    {
        if (!isStringInBounds(plainText))
            return "The selected string is not in bounds, Try again.";

        String encrypt = "";

        for (int i = 0; i < plainText.length(); i++)
        {
            int plainIndex = ALPHABET64.indexOf(plainText.charAt(i));

            int encryptedIndex = (plainIndex + key + 64) % 64;

            encrypt += ALPHABET64.charAt(encryptedIndex);
        }

        return encrypt;
    }

    // Caesar Decryption
    public static String caesarDecryption(String encryptedText, int key)
    {
        if (!isStringInBounds(encryptedText))
            return "The selected string is not in bounds, Try again.";

        String decrypt = "";

        for (int i = 0; i < encryptedText.length(); i++)
        {
            int encryptedIndex = ALPHABET64.indexOf(encryptedText.charAt(i));

            int plainIndex = (encryptedIndex - key + 64) % 64;

            decrypt += ALPHABET64.charAt(plainIndex);
        }

        return decrypt;
    }
    	
}
