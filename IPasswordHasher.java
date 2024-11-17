import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public interface IPasswordHasher {
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

    default boolean verifyPassword(String password, String hashedPassword) {
        String newHash = hashPassword(password);
        return hashedPassword.equals(newHash);
    }
}
