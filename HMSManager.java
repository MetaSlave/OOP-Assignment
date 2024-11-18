
import java.util.Scanner;
/**
* Manages the Hospital Management System's authentication and user operations
*/
public class HMSManager implements IChangePassword{
    // Use database singleton
    private final HMSDatabase db = HMSDatabase.getInstance();
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();


    /**
    * Authenticates a user's login attempt and manages first-time login procedures
    *
    * @return User The authenticated User object if login successful and null if authentication fails
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
