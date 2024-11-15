import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class HMSMenu implements IMenu{
    
    @Override
    public void display() {
        System.out.println("----HMS MENU----");
        System.out.println("1. Login");
        System.out.println("2. Exit");
    }

    // Login a user to the HMS
    public User login(ArrayList<User> allUsers, Scanner scanner) {
        User currentUser;
        while(true) {
            System.out.println("Enter your login id: ");
            String loginId = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
            currentUser = User.login(loginId, password, allUsers, scanner);
            if (!Objects.isNull(currentUser)) {
                return currentUser;
            }
        }
    }
}
