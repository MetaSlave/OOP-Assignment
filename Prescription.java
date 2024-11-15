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
 * Represents a medical prescription issued during an appointment.
 * Tracks medication details, quantity, and dispensing status.
 * Implements Serializable for persistent storage.
 */
public class Prescription implements Serializable {
    private String appointmentId;
    private String medication;
    private int quantity;
    private Status prescriptionStatus;

    /**
     * Enumeration of possible prescription statuses.
     */
    public static enum Status {
        PENDING,
        DISPENSED
    }

    /**
     * Constructs a new Prescription with the specified details.
     * Initial status is set to PENDING.
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

    public String getMedication() {
        return this.medication;
    }

    public int getQuantity() {
        return this.quantity;
    }

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
     * Includes medication name, quantity, and status.
     */
    public void print() {
        System.out.println("Medication: " + this.medication);
        System.out.println("Quantity: " + this.quantity);
        System.out.println("Status: " + this.prescriptionStatus);
    }
}
