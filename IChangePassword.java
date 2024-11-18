import java.util.Scanner;
/**
 * Interface for handling password change functionality in the system.
 * Extends IHashPassword to utilize password hashing capabilities.
 */
public interface IChangePassword extends IHashPassword{
/**
     * Allows a user to change their password after verifying their old password.
     * Prompts the user to enter the new password twice for confirmation.
     * NOTE: USED ONLY FOR FIRST LOGIN
     *
     * @param u The user who is logging in for the first time
     * @param oldPassword The current password for verification
     * @return true if password was successfully changed, false otherwise
     */
    default boolean changePassword(User u, String oldPassword) {
        // Get the scanner instance
        HMSInput input = HMSInput.getInstance();
        // Get the scanner
        Scanner scanner = input.getScanner();

        if (verifyPassword(oldPassword,u.getPassword())) {
            String newPassword;
            String newPassword2;
            do {
                System.out.println("Enter your new password:");
                newPassword = scanner.nextLine();
                System.out.println("Enter your new password again:");
                newPassword2 = scanner.nextLine();
                if (!newPassword.equals(newPassword2)) {
                    System.out.println("Passwords do not match");
                }
            } while (!newPassword.equals(newPassword2));
            u.setPassword(hashPassword(newPassword));
            System.out.println("Password has been successfully changed!");
            return true;
        }
        else {
            System.out.println("Password incorrect!");
        }
        return false;
    }
}
