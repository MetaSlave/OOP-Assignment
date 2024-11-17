import java.util.Objects;
import java.util.Scanner;

public class HMSManager{
    // Use database singleton
    private final HMSDatabase db = HMSDatabase.getInstance();
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();


    // Login a user to the HMS
    public User login() {
        User currentUser;
        while(true) {
            System.out.println("Enter your login id: ");
            String loginId = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
            currentUser = User.login(loginId, password, db.getAllUsers());
            return currentUser;
        }
    }
}
