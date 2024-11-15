import java.util.*;
import java.io.*;

/**
 * Represents an administrator in the healthcare system.
 * Extends the Staff class with administrator-specific functionality.
 * Implements serialization for persistent storage.
 */
public class Administrator extends Staff {
    
    /**
     * Constructs a new Administrator with the specified attributes.
     *
     * @param id The unique identifier for the administrator
     * @param role The role/position of the administrator
     * @param name The full name of the administrator
     * @param gender The gender of the administrator
     * @param age The age of the administrator
     */
    public Administrator(String id, String role, String name, String gender, int age) {
        super(id, role, name, gender, age);
    }

    /**
     * Deserializes administrator data from a file.
     * 
     * @return ArrayList of Administrator objects, or null if deserialization fails
     */
    protected static ArrayList<Administrator> deserialize() {
        ArrayList<Administrator> allAdministrators = null;
        try (FileInputStream fis = new FileInputStream("administrator.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            allAdministrators = (ArrayList<Administrator>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allAdministrators;
    }

    /**
     * Serializes administrator data to a file.
     * 
     * @param allAdministrators List of administrators to serialize
     * @return true if serialization is successful, false otherwise
     */
    protected static boolean serialize(ArrayList<Administrator> allAdministrators) {
        try (FileOutputStream fos = new FileOutputStream("administrator.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(allAdministrators);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}