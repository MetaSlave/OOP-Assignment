import java.util.*;
import java.io.*;

/**
 * Represents a doctor in the healthcare system
 * Implements serialization for persistent storage
 */
public class Doctor extends Staff {
    
    /**
     * Constructs a new Doctor with the specified attributes.
     *
     * @param id The unique identifier for the doctor
     * @param role The role/position of the doctor
     * @param name The full name of the doctor
     * @param gender The gender of the doctor
     * @param age The age of the doctor
     */
    public Doctor(String id, String role, String name, String gender, int age) {
        super(id, role, name, gender, age);
    }

    /**
     * Deserializes doctor data from a file.
     * 
     * @return ArrayList of Doctor objects, or null if deserialization fails
     */
    protected static ArrayList<Doctor> deserialize() {
        ArrayList<Doctor> allDoctors = null;
        try (FileInputStream fis = new FileInputStream("doctor.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            allDoctors = (ArrayList<Doctor>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allDoctors;
    }

    /**
     * Serializes doctor data to a file.
     * 
     * @param allDoctors List of doctors to serialize
     * @return true if serialization is successful, false otherwise
     */
    protected static boolean serialize(ArrayList<Doctor> allDoctors) {
        try (FileOutputStream fos = new FileOutputStream("doctor.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(allDoctors);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}