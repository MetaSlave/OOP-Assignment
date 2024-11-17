
import java.util.Scanner;

public class HMSManager extends AbstractPasswordHasher implements IChangePassword{
    // Use database singleton
    private final HMSDatabase db = HMSDatabase.getInstance();
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();


    // Login a user to the HMS
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
