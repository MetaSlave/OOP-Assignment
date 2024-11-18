import java.io.*;

/**
 * Represents a user in the system
 * Implements Serializable for persistent storage
 */
public class User implements Serializable, IHashPassword{
    /**
     * The unique identifier for the user
     */
    private String id;
    /**
     * The user's name
     */
    private String name;
    /**
     * The password stored as a hash for the user
     */
    private String password;
    /**
     * The user's role, one of Doctor, Patient, Administrator, Pharmacist
     */
    private String role;
    /**
     * The user's gender, Male or Female
     */
    private String gender;
    /**
     * A boolean that is true if this is the user's first login, false otherwise
     */
    private boolean firstLogin;
    
    /**
     * Constructs a new User with specified attributes (Default password is "password", changes upon first login, denoted by FirstLogin flag)
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
     * Gets user ID
     * @return user ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets user role
     * @return user role
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Gets user name
     * @return user name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets user name
     * @param name user name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets user gender
     * @return user gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Sets user gender
     * @param gender user gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets hashed password
     * @return hashed password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets new password
     * @param newPasswordHash new hashed password to set
     */
    public void setPassword(String newPasswordHash) {
        this.password = newPasswordHash;
    }

    /**
     * Checks if first login
     * @return true if first login, false otherwise
     */
    public boolean getFirstLogin() {
        return this.firstLogin;
    }

    /**
     * Sets first login to false
     */
    public void setFirstLoginFalse() {
        this.firstLogin = false;
    }
}