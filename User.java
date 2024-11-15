import java.util.*;
import java.io.*;

/**
 * Represents a user in the system with basic profile information and authentication capabilities.
 * This class implements Serializable to allow for object persistence.
 */
public class User extends AbstractPasswordHasher implements Serializable {
    private String id;
    private String name;
    private String password;
    private String role;
    private String gender;
    private boolean firstLogin;
    
    /**
     * Constructs a new User with specified attributes.
     * Sets default password to "password" and FirstLogin flag accordingly.
     *
     * @param id     The unique identifier for the user
     * @param role   The role/permission level of the user
     * @param name   The full name of the user
     * @param gender The gender of the user
     */
    public User(String id, String role, String name, String gender) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.password = hashPassword("password");
        this.firstLogin = true;
    }
    
    /**
     * Returns the user's ID.
     * @return The user's unique identifier
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the user's role.
     * @return The user's role in the system
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Returns the user's name.
     * @return The user's full name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the user's name to a new value.
     * @param name The new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user's gender.
     * @return The user's gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Sets the user's gender to a new value.
     * @param Gender The new gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the user's password.
     * @return The user's current password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Checks if this is the user's first login.
     * @return true if this is the user's first login, false otherwise
     */
    public boolean getFirstLogin() {
        return this.firstLogin;
    }

    /**
     * Sets the user's first login to false.
     */
    public void setFirstLoginFalse() {
        this.firstLogin = false;
    }

    /**
     * Authenticates a user based on login ID and password.
     * If it's the user's first login, they will be prompted to change their password.
     *
     * @param loginId   The ID to authenticate with
     * @param password  The password to authenticate with
     * @param allUsers  List of all users in the system
     * @return The authenticated User object if successful, null otherwise
     */
    public static User login(String loginId, String password, ArrayList<User> allUsers, Scanner scanner) {
        // Loop all users
        for (User u : allUsers) {
            if (loginId.equals(u.getId())) {
                if (verifyPassword(password,u.getPassword())) {
                    // First Login must change password
                    if (u.getFirstLogin()) {
                        System.out.println("This is your first time logging in");
                        u.changePassword(password, scanner);
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

    /**
     * Allows a user to change their password after verifying their old password.
     * Prompts the user to enter the new password twice for confirmation.
     *
     * @param oldPassword The current password for verification
     * @return true if password was successfully changed, false otherwise
     */
    public boolean changePassword(String oldPassword, Scanner scanner) {
        if (verifyPassword(oldPassword,this.password)) {
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
            this.password = newPassword;
            System.out.println("Password has been successfully changed!");
            return true;
        }
        else {
            System.out.println("Password incorrect!");
        }
        return false;
    }
}