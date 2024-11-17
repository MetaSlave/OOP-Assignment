import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
/**
 * Provides secure password hashing and verification functionality.
 * This interface implements SHA-256 hashing for password security,
 * with methods to both hash new passwords and verify existing ones.
 */
public interface IHashPassword {
    /**
     * Hashes a password using SHA-256 algorithm.
     * The method converts the password into a hexadecimal string representation
     * of its SHA-256 hash value, ensuring secure password storage.
     *
     * @param password The plain-text password to be hashed
     * @return A hexadecimal string representation of the hashed password
     * @throws RuntimeException If there is an error during the hashing process
     */
    default String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // Convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
            
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    /**
     * Verifies if a plain-text password matches a stored hashed password.
     * This method works by hashing the provided plain-text password
     * and comparing it with the stored hash.
     *
     * @param password The plain-text password to verify
     * @param hashedPassword The stored hashed password to compare against
     * @return boolean Returns true if the passwords match, false otherwise
     */
    default boolean verifyPassword(String password, String hashedPassword) {
        String newHash = hashPassword(password);
        return hashedPassword.equals(newHash);
    }
}
