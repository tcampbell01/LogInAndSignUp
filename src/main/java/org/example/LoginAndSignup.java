///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           LoginAndSignUp
// Course:          CS200, Summer 2024
//
// Author:          Teresa Campbell
// Email:           tjcamobe@wisc.edu
// Lecturer's Name: Professor Jim Williams
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
// NONE
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////
//package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class provides methods for reading from and writing to a
 * user database file, as well as encrypting and decrypting passwords.
 * It also includes methods for user login and signup functionality.
 *
 * The class includes:
 * - A method to read user data from a file and populate lists of usernames,
 *   encrypted passwords, and keys.
 * - A method to write the lists of usernames, encrypted passwords, and keys
 *   back to the file.
 * - Placeholder methods for password encryption and decryption,
 *   user login, and user signup (commented out).
 *
 * The main method demonstrates reading from and writing to the user database.
 */
public class LoginAndSignup {

    /**
     * Reads from the database file and populates the users, passwords, and
     * keys lists.
     * Outputs an error message if the file cannot be opened.
     *
     * @param dbName The name of the database file to read from.
     * @param users  The list to populate with usernames.
     * @param pwds   The list to populate with encrypted passwords.
     * @param keys   The list to populate with keys.
     */
    public static void readFromFile(String dbName, ArrayList<String> users,
                                    ArrayList<String> pwds,
                                    ArrayList<Integer> keys) {
        File inputFile = new File(dbName);

        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\t");

                if (parts.length == 3) {
                    users.add(parts[0]);
                    pwds.add(parts[1]);
                    keys.add(Integer.valueOf(parts[2]));
                } else {
                    System.out.println("Warning: Skipping line with " +
                            "unexpected " +
                            "format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("<" + dbName + "> cannot open!");
        }
    }

    /**
     * Writes the usernames, encrypted passwords, and keys back to the file.
     * Each username, encrypted password, and key are written on the same line.
     * Tabs separate each value.
     *
     * @param fileName      The name of the file to write to.
     * @param users         The list of usernames to write.
     * @param encryptedpwds The list of encrypted passwords to write.
     * @param keys          The list of keys to write.
     */
    public static void writeToFile(String fileName, ArrayList<String> users,
                                   ArrayList<String> encryptedpwds,
                                   ArrayList<Integer> keys) {

        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (int i = 0; i < users.size(); i++) {
                writer.println(users.get(i) + "\t" +
                        encryptedpwds.get(i) + "\t" + keys.get(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("<" + fileName + "> cannot open!");
        }
    }

    /**
     * Encrypts the password by adding the key to each character.
     *
     * @param password The password to encrypt.
     * @param key      The key to use for encryption.
     * @return The encrypted password.
     */
    public static String pwdEncryption(String password, int key) {
        String encryptedPassword = "";

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            ch += key;
            encryptedPassword += ch;
        }
        return encryptedPassword;
    }


    /**
     * Decrypts the password by subtracting the key from each character.
     *
     * @param encryptedPassword The encrypted password to decrypt.
     * @param key The key used for encryption.
     * @return The decrypted password.
     */
    public static String pwdDecryption(String encryptedPassword, int key) {
        String decryptedPassword = "";

        for (int i = 0; i < encryptedPassword.length(); i++) {
            char ch = encryptedPassword.charAt(i);
            ch -= key;
            decryptedPassword += ch;
        }
        return decryptedPassword;
    }


    /**
     * This method is used for signing up a new user.
     * If the newUser already exists, then the signup action fails and returns
     * "Invalid username".
     * Else signup action succeeds with the username, encrypted password and
     * the key are added to the corresponding ArrayLists and return "Successful
     * signup".
     *
     * @param rand Random instance to generate a random key. The key should
     *             be in the range of [1, 20]
     * @param users ArrayList contains all usernames
     * @param pwds ArrayList contains all passwords
     * @param keys ArrayList contains all keys to encrypt and decrypt
     * @param newUser new username
     * @param newPwd new password
     * @return a message to indicate the signup result
     */
    public static String signup(Random rand, ArrayList<String> users,
                                ArrayList<String> pwds, ArrayList<Integer> keys,
                                String newUser,
                                String newPwd) {

        if (users.contains(newUser)) {
            return "Invalid username";
        } else {
            int key = rand.nextInt(20) + 1;
            String encryptedPwd = pwdEncryption(newPwd, key);
            users.add(newUser);
            pwds.add(encryptedPwd);
            keys.add(key);
            return "Successful signup";
        }
    }

    /**
     * Determine whether the new username exists.
     *
     * @param newUser The new username to check.
     * @param users The list of existing usernames.
     * @return boolean true if the newUser doesn't exist,
     *                false if the newUser already exists.
     */
    public static boolean uniqueUser(String newUser, ArrayList<String> users) {
        return !users.contains(newUser);
    }

    /**
     * The main method to run the LoginAndSignup application.
     * It demonstrates reading from and writing to the user database.
     *
     * This method initializes the lists to hold users, passwords, and keys,
     * defines the file path for the user database, reads from the file to
     * populate the lists, and then writes the updated lists back to the file.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create lists to hold users, passwords, and keys
        ArrayList<String> users = new ArrayList<>();
        ArrayList<String> pwds = new ArrayList<>();
        ArrayList<Integer> keys = new ArrayList<>();

        // Define the file path
        String fileName = "/Users/teresacampbell/Desktop/LogInAndSignUp/" +
                "userDB.txt";

        // Read from the file
        readFromFile(fileName, users, pwds, keys);

        // Write updated lists to the file
        writeToFile(fileName, users, pwds, keys);
    }
}
