import java.util.*;
import java.io.*;

/**
 * Represents a pharmacist in the healthcare system.
 * Extends the Staff class with pharmacist-specific functionality.
 * Implements serialization for persistent storage.
 */
public class Pharmacist extends Staff {
    
    /**
     * Constructs a new Pharmacist with the specified attributes.
     *
     * @param id The unique identifier for the pharmacist
     * @param role The role/position of the pharmacist
     * @param name The full name of the pharmacist
     * @param gender The gender of the pharmacist
     * @param age The age of the pharmacist
     */
    Pharmacist(String id, String role, String name, String gender, int age) {
        super(id, role, name, gender, age);
    }

    /**
     * Deserializes pharmacist data from a file.
     * 
     * @return ArrayList of Pharmacist objects, or null if deserialization fails
     */
    protected static ArrayList<Pharmacist> deserialize() {
        ArrayList<Pharmacist> allPharmacists = null;
        try (FileInputStream fis = new FileInputStream("pharmacist.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            allPharmacists = (ArrayList<Pharmacist>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allPharmacists;
    }

    /**
     * Serializes pharmacist data to a file.
     * 
     * @param allPharmacists List of pharmacists to serialize
     * @return true if serialization is successful, false otherwise
     */
    protected static boolean serialize(ArrayList<Pharmacist> allPharmacists) {
        try (FileOutputStream fos = new FileOutputStream("pharmacist.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(allPharmacists);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}