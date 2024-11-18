import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Represents a patient in the healthcare system
 * Includes personal and medical information such as blood type and contact details
 */
public class Patient extends User {
    /**
     * Blood type of patient
     */
    private String bloodType;
    /**
     * Date of birth of patient
     */
    private LocalDate dateOfBirth;
    /**
     * Email address of patient
     */
    private String email;
    /**
     * Contact number of patient
     */
    private String contactNumber;

    /**
     * Constructs a new Patient with the specified attributes (Contact info is empty by default)
     *
     * @param id The unique identifier for the patient
     * @param role The role in the system (typically "Patient")
     * @param name The full name of the patient
     * @param gender The gender of the patient
     * @param bloodType The patient's blood type
     * @param dateOfBirth The patient's date of birth
     * @param email The patient's email address
     */
    Patient(String id, String role, String name, String gender, 
            String bloodType, LocalDate dateOfBirth, String email) {
        super(id, role, name, gender);
        this.bloodType = bloodType;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.contactNumber = "";
    }
    
    /**
     * Deserializes patient data from a file.
     * 
     * @return ArrayList of Patient objects, or null if deserialization fails
     */
    protected static ArrayList<Patient> deserialize() {
        ArrayList<Patient> allPatients = null;
        try (FileInputStream fis = new FileInputStream("patient.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            allPatients = (ArrayList<Patient>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allPatients;
    }

    /**
     * Serializes patient data to a file.
     * 
     * @param allPatients List of patients to serialize
     * @return true if serialization is successful, false otherwise
     */
    protected static boolean serialize(ArrayList<Patient> allPatients) {
        try (FileOutputStream fos = new FileOutputStream("patient.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(allPatients);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Updates the patient's email
     * 
     * @param email The new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Updates the patient's contact nummber
     * 
     * @param contactNumber The new contact number
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Prints patient details (patient ID, role, name, gender, blood type, date of birth, email, etc.)
     */
    public void print() {
        System.out.println("Id: " + this.getId());
        System.out.println("Role: " + this.getRole());
        System.out.println("Name: " + this.getName());
        System.out.println("Gender: " + this.getGender());
        System.out.println("Blood Type: " + this.bloodType);
        System.out.println("DOB: " + this.dateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yy")));
        System.out.println("Email: " + this.email);
        System.out.println("Contact: " + this.contactNumber);
    }
}
