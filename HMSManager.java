
import java.util.Scanner;
/**
* Manages the Hospital Management System's authentication and user operations.
* This class handles user login, password verification, and first-time login procedures,
* implementing the IChangePassword interface for password management functionality.
*/
public class HMSManager implements IChangePassword{
    // Use database singleton
    private final HMSDatabase db = HMSDatabase.getInstance();
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();


    /**
    * Authenticates a user's login attempt and manages first-time login procedures.
    * This method:
    * 1. Prompts for user ID and password
    * 2. Verifies credentials against stored user data
    * 3. Handles first-time login password change requirement
    * 4. Provides appropriate feedback for login success/failure
    *
    * Login Process:
    * - Checks if provided ID exists in the system
    * - Verifies the provided password
    * - For first-time logins, forces password change
    * - Updates first login status after password change
    *
    * @return User The authenticated User object if login successful,
    *         null if authentication fails
    * 
    * Security Features:
    * - Password verification through IChangePassword interface
    * - Enforced password change for first-time logins
    * - Clear success/failure feedback
    */
    public User login() {
        System.out.println("Enter your login id: ");
        String loginId = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        // Loop all users
        for (User u : db.getAllUsers()) {
            if (loginId.equals(u.getId())) {
                if (verifyPassword(password,u.getPassword())) {
                    // First Login must change password
                    if (u.getFirstLogin()) {
                        System.out.println("This is your first time logging in");
                        changePassword(u, password);
                        u.setFirstLoginFalse();
                    }
                    System.out.println("Successful login!");
                    return u;
                }
            }
        }
        System.out.println("Unsuccessful login, please try again");
        return null;
    }
}
