import java.util.Scanner;

public interface IChangePassword{
    /**
     * Allows a user to change their password after verifying their old password.
     * Prompts the user to enter the new password twice for confirmation.
     *
     * @param oldPassword The current password for verification
     * @return true if password was successfully changed, false otherwise
     */
    default boolean changePassword(User u) {
        // Get the scanner instance
        HMSInput input = HMSInput.getInstance();
        // Get the scanner
        Scanner scanner = input.getScanner();

        System.out.println("Enter your old password:");
        String oldPassword = scanner.nextLine();
        if (AbstractPasswordHasher.verifyPassword(oldPassword,u.getPassword())) {
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
            u.setPassword(AbstractPasswordHasher.hashPassword(newPassword));
            System.out.println("Password has been successfully changed!");
            return true;
        }
        else {
            System.out.println("Password incorrect!");
        }
        return false;
    }
    /**
     * Allows a user to change their password after verifying their old password.
     * Prompts the user to enter the new password twice for confirmation.
     * NOTE: USED ONLY FOR FIRST LOGIN
     *
     * @param oldPassword The current password for verification
     * @return true if password was successfully changed, false otherwise
     */
    default boolean changePassword(User u, String oldPassword) {
        // Get the scanner instance
        HMSInput input = HMSInput.getInstance();
        // Get the scanner
        Scanner scanner = input.getScanner();

        if (AbstractPasswordHasher.verifyPassword(oldPassword,u.getPassword())) {
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
            u.setPassword(AbstractPasswordHasher.hashPassword(newPassword));
            System.out.println("Password has been successfully changed!");
            return true;
        }
        else {
            System.out.println("Password incorrect!");
        }
        return false;
    }
}
