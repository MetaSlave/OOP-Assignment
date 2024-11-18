import java.util.Scanner;
/**
 * This interface extends IHashPassword to ensure secure password handling.
 */
public interface IAuthChangePassword extends IHashPassword{
    /**
     * Changes a user's password after verification of their old password.
     * 
     * @param u The User object whose password needs to be changed
     * @return boolean Returns true if the password was successfully changed,
     *                 false if the old password verification failed
     */
    default boolean changePassword(User u) {
        // Get the scanner instance
        HMSInput input = HMSInput.getInstance();
        // Get the scanner
        Scanner scanner = input.getScanner();

        System.out.println("Enter your old password:");
        String oldPassword = scanner.nextLine();
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
