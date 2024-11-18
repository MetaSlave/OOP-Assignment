import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the outcome of a medical appointment, including services provided and consultation notes.
 * This class implements Serializable for persistent storage of appointment outcomes.
 */
public class AppointmentOutcome implements Serializable {
    /**
     * ID of the appointment.
     */
    private String appointmentId;

    /**
     * Date and time when the appointment was completed.
     */
    private LocalDateTime outcomeDateTime;

    /**
     * List of services provided during the appointment.
     */
    private String servicesProvided;

    /**
     * Notes from the consultation/appointment.
     */
    private String consultationNotes;

    /**
     * Total cost of the appointment including services.
     */
    private double appointmentCost;

    /**
     * Base cost for any appointment before additional services.
     */
    public static final double APPOINTMENT_BASE_COST = 20;
    /**
     * Constructs a new AppointmentOutcome with the specified details.
     * The outcomeDateTime is automatically set to the current time.
     *
     * @param appointmentId The unique identifier of the associated appointment
     * @param outcomeDateTime The date and time of the outcome (automatically set)
     * @param servicesProvided Description of medical services provided during the appointment
     * @param consultationNotes Detailed notes from the consultation
     */
    public AppointmentOutcome(String appointmentId, LocalDateTime outcomeDateTime, 
            String servicesProvided, String consultationNotes) {
        this.appointmentId = appointmentId;
        this.outcomeDateTime = LocalDateTime.now();
        this.servicesProvided = servicesProvided;
        this.consultationNotes = consultationNotes;
        // Standard cost of appointment
        this.appointmentCost = APPOINTMENT_BASE_COST;
    }

    /**
     * Deserializes appointment outcomes from a file.
     * 
     * @return Map of appointment IDs to their outcomes, or null if deserialization fails
     */
    protected static Map<String, AppointmentOutcome> deserialize() {
        Map<String, AppointmentOutcome> allAppointmentOutcomes = null;
        try (FileInputStream fis = new FileInputStream("appointmentOutcomes.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            allAppointmentOutcomes = (Map<String, AppointmentOutcome>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allAppointmentOutcomes;
    }

    /**
     * Serializes appointment outcomes to a file.
     * Creates a new HashMap to ensure serializability.
     * 
     * @param allAppointmentOutcomes Map of appointment outcomes to serialize
     * @return true if serialization is successful, false otherwise
     */
    protected static boolean serialize(Map<String, AppointmentOutcome> allAppointmentOutcomes) {
        try (FileOutputStream fos = new FileOutputStream("appointmentOutcomes.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new HashMap<>(allAppointmentOutcomes));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the date and time when this outcome was recorded.
     * 
     * @return LocalDateTime when the outcome was created
     */
    public LocalDateTime getOutcomeDateTime() {
        return this.outcomeDateTime;
    }

    /**
     * Gets the description of services provided during the appointment.
     * 
     * @return String describing the medical services provided
     */
    public String getServicesProvided() {
        return this.servicesProvided;
    }

    /**
     * Gets the consultation notes recorded during the appointment.
     * 
     * @return String containing the consultation notes
     */
    public String getConsultationNotes() {
        return this.consultationNotes;
    }
    /**
     * Gets the total cost of the appointment including base cost and any additional charges.
     * The base cost is defined by APPOINTMENT_BASE_COST constant.
     * 
     * @return double The total cost of the appointment
     */
    public double getAppointmentCost() {
        return this.appointmentCost;
    }
    /**
     * Updates the total cost of the appointment.
     * This method allows modification of the appointment cost to account for
     * additional services or special circumstances beyond the base cost.
     * 
     * @param newAppointmentCost The new total cost to set for the appointment
     */
    public void setAppointmentCost(double newAppointmentCost) {
        this.appointmentCost = newAppointmentCost;
    }

    /**
     * Creates a new AppointmentOutcome instance for a given appointment.
     * 
     * @param appointment The appointment for which to create an outcome
     * @param servicesProvided Description of services provided during the appointment
     * @param consultationNotes Detailed notes from the consultation
     * @return A new AppointmentOutcome instance
     */
    public static AppointmentOutcome createOutcome(Appointment appointment, 
            String servicesProvided, String consultationNotes) {
        return new AppointmentOutcome(
            appointment.getAppointmentId(), 
            LocalDateTime.now(), 
            servicesProvided, 
            consultationNotes
        );
    }
}