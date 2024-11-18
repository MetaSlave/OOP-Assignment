import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Tracks medication details, quantity, and dispensing status
 * Implements Serializable for persistent storage
 */
public class Prescription implements Serializable {
    /**
     * Appointment ID for the prescription
     */
    private String appointmentId;
    /**
     * Type of medication
     */
    private String medication;
    /**
     * Quantity of medication
     */
    private int quantity;
    /**
     * Prescription status (i.e. DISPENSED)
     */
    private Status prescriptionStatus;

    /**
     * Status of prescriptions.
     */
    public static enum Status {
        /**
         * Prescription has not been dispensed
         */
        PENDING,

        /**
         * Prescription has been dispensed
         */
        DISPENSED
    }

    /**
     * Constructs a new Prescription with the specified details (initial status is pending)
     *
     * @param appointmentId The ID of the associated appointment
     * @param medication The name of the prescribed medication
     * @param quantity The quantity of medication prescribed
     */
    public Prescription(String appointmentId, String medication, int quantity) {
        this.appointmentId = appointmentId;
        this.medication = medication;
        this.quantity = quantity;
        this.prescriptionStatus = Status.PENDING;
    }

    /**
     * Deserializes prescriptions from a file.
     * 
     * @return Map of appointment IDs to their list of prescriptions, or null if deserialization fails
     */
    protected static Map<String, ArrayList<Prescription>> deserialize() {
        Map<String, ArrayList<Prescription>> allPrescriptions = null;
        try (FileInputStream fis = new FileInputStream("prescriptions.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            allPrescriptions = (Map<String, ArrayList<Prescription>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allPrescriptions;
    }

    /**
     * Serializes prescriptions to a file.
     * 
     * @param allPrescriptions Map of prescriptions to serialize
     * @return true if serialization is successful, false otherwise
     */
    protected static boolean serialize(Map<String, ArrayList<Prescription>> allPrescriptions) {
        try (FileOutputStream fos = new FileOutputStream("prescriptions.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new HashMap<>(allPrescriptions));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Gets the current status of the prescription.
     * 
     * @return The current status of the prescription
     */
    public Status getStatus() {
        return this.prescriptionStatus;
    }
   /**
    * Gets the name of the prescribed medication.
    *
    * @return String The medication name
    */
    public String getMedication() {
        return this.medication;
    }
   /**
    * Gets the prescribed quantity of medication.
    *
    * @return int The quantity prescribed
    */
    public int getQuantity() {
        return this.quantity;
    }

   /**
    * Gets the ID of the associated appointment.
    *
    * @return String The appointment ID
    */
    public String getAppointmentId() {
        return this.appointmentId;
    }

    /**
     * Creates a new prescription for a specific appointment.
     * 
     * @param appointmentId The ID of the associated appointment
     * @param medication The name of the medication to prescribe
     * @param quantity The quantity of medication to prescribe
     * @return A new Prescription instance
     */
    public static Prescription createPrescription(String appointmentId, String medication, int quantity) {
        return new Prescription(appointmentId, medication, quantity);
    }

    /**
     * Marks the prescription as dispensed by changing its status to DISPENSED.
     */
    public void dispensePrescription() {
        this.prescriptionStatus = Status.DISPENSED;
    }

    /**
     * Prints the details of the prescription to the console.
     */
    public void print() {
        System.out.println("Medication: " + this.medication);
        System.out.println("Quantity: " + this.quantity);
        System.out.println("Status: " + this.prescriptionStatus);
    }
}
