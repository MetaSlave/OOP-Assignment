import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a medical record (contains patient diagnosis, treatment info and tracking of updates)
 * Implements Serializable for persistent storage.
 */
public class MedicalRecord implements Serializable {
    /**
     * ID of the patient. Empty string indicates available slot.
     */
    private String patientId;
    /**
     * Diagnosis of the patient
     */
    private String diagnosis;
    /**
     * Treatment of the patient
     */
    private String treatment;
    /**
     * Update time of the medical record
     */
    private LocalDateTime updated;

    /**
     * Creates a new medical record with user-specified details
     * 
     * @param patientId The ID of the patient this record belongs to
     * @param diagnosis The medical diagnosis
     * @param treatment The prescribed treatment
     */
    public MedicalRecord(String patientId, String diagnosis, String treatment) {
        this.patientId = patientId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.updated = LocalDateTime.now();
    }

    /**
     * Deserializes medical records from a file.
     * 
     * @return Map of patient IDs to their list of medical records, or null if deserialization fails
     */
    protected static Map<String, ArrayList<MedicalRecord>> deserialize() {
        Map<String, ArrayList<MedicalRecord>> allMedicalRecords = null;
        try (FileInputStream fis = new FileInputStream("medicalRecords.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            allMedicalRecords = (Map<String, ArrayList<MedicalRecord>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allMedicalRecords;
    }

    /**
     * Using a new HashMap to serialize medical records to a file
     * 
     * @param allMedicalRecords Map of medical records to serialize
     * @return true if serialization is successful, false otherwise
     */
    protected static boolean serialize(Map<String, ArrayList<MedicalRecord>> allMedicalRecords) {
        try (FileOutputStream fos = new FileOutputStream("medicalRecords.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new HashMap<>(allMedicalRecords));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates a new medical record with the specified details.
     * 
     * @param patientId The ID of the patient this record belongs to
     * @param diagnosis The medical diagnosis
     * @param treatment The prescribed treatment
     * @return A new MedicalRecord instance
     */
    public static MedicalRecord createRecord(String patientId, String diagnosis, String treatment) {
        return new MedicalRecord(patientId, diagnosis, treatment);
    }

    /**
     * Prints details of the medical record (diagnosis, treatment, update date, etc.)
     */
    public void print() {
        System.out.println("Updated: " + this.updated.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")));
        System.out.println("Diagnosis: " + this.diagnosis);
        System.out.println("Treatment: " + this.treatment);
    }
}
