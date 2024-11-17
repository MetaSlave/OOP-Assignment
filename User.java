import java.io.*;

/**
 * Represents a user in the system with basic profile information and authentication capabilities.
 * This class implements Serializable to allow for object persistence.
 */
public class User implements Serializable, IHashPassword{
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
     * Returns the user's password hashed.
     * @return The user's current password hashed
     */
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPasswordHash) {
        this.password = newPasswordHash;
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
}