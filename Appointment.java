import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

/**
 * Represents a medical appointment in the system.
 * This class handles the scheduling, management, and tracking of appointments between doctors and patients.
 * Implements Serializable for persistent storage.
 */
public class Appointment implements Serializable {
    private String appointmentId;
    private String patientId;  // Empty means slot hasn't been taken
    private String doctorId;
    private String doctorName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus appointmentStatus;

    /**
     * Enumeration of possible appointment statuses.
     */
    public static enum AppointmentStatus {
        SCHEDULED,
        PENDING,
        COMPLETED,
        CANCELLED,
        OPEN
    }

    /**
     * Constructs a new Appointment with the specified doctor information and time slot.
     * Generates a unique UUID for the appointment and sets initial status to OPEN.
     *
     * @param doctorId The unique identifier of the doctor
     * @param doctorName The name of the doctor
     * @param appointmentDate The date of the appointment
     * @param appointmentTime The time of the appointment
     */
    public Appointment(String doctorId, String doctorName, LocalDate appointmentDate, LocalTime appointmentTime) {
        this.appointmentId = UUID.randomUUID().toString();
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentStatus = AppointmentStatus.OPEN;
    }

    /**
     * Deserializes appointments from a file.
     * 
     * @return ArrayList of Appointment objects, or null if deserialization fails
     */
    protected static ArrayList<Appointment> deserialize() {
        ArrayList<Appointment> allAppointments = null;
        try (FileInputStream fis = new FileInputStream("appointments.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            allAppointments = (ArrayList<Appointment>) ois.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * Serializes appointments to a file.
     * 
     * @param allAppointments List of appointments to serialize
     * @return true if serialization is successful, false otherwise
     */
    protected static boolean serialize(ArrayList<Appointment> allAppointments) {
        try (FileOutputStream fos = new FileOutputStream("appointments.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(allAppointments);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the unique identifier of this appointment.
     * 
     * @return The appointment's UUID as a String
     */
    public String getAppointmentId() {
        return this.appointmentId;
    }

    /**
     * Gets the ID of the patient scheduled for this appointment.
     * Will be empty string if the appointment slot is not taken.
     * 
     * @return The patient's ID, or empty string if no patient is scheduled
     */
    public String getPatientId() {
        return this.patientId;
    }

    /**
     * Gets the ID of the doctor assigned to this appointment.
     * 
     * @return The doctor's ID
     */
    public String getDoctorId() {
        return this.doctorId;
    }

    /**
     * Gets the scheduled date of the appointment.
     * 
     * @return The appointment date as a LocalDate object
     */
    public LocalDate getAppointmentDate() {
        return this.appointmentDate;
    }

    /**
     * Gets the scheduled time of the appointment.
     * 
     * @return The appointment time as a LocalTime object
     */
    public LocalTime getAppointmentTime() {
        return this.appointmentTime;
    }

    /**
     * Gets the current status of the appointment.
     * Status can be SCHEDULED, PENDING, COMPLETED, CANCELLED, or OPEN.
     * 
     * @return The current AppointmentStatus enum value of the appointment
     */
    public AppointmentStatus getAppointmentStatus() {
        return this.appointmentStatus;
    }

    /**
     * Schedules a patient for this appointment.
     * 
     * @param patientId The ID of the patient to schedule
     * @return true if scheduling is successful
     */
    public boolean schedulePatient(String patientId) {
        this.patientId = patientId;
        this.appointmentStatus = AppointmentStatus.PENDING;
        return true;
    }

    /**
     * Resets the appointment slot to open status.
     * 
     * @return true if reset is successful
     */
    public boolean resetSlot() {
        this.patientId = null;
        this.appointmentStatus = AppointmentStatus.OPEN;
        return true;
    }

    /**
     * Approves a pending appointment, sets its status to SCHEDULED.
     * 
     * @return true if approval is successful
     */
    public boolean approveSlot() {
        this.appointmentStatus = AppointmentStatus.SCHEDULED;
        return true;
    }

    /**
     * Declines a pending appointment, sets its status to OPEN.
     * 
     * @return true if decline is successful
     */
    public boolean declineSlot() {
        this.appointmentStatus = AppointmentStatus.OPEN;
        return true;
    }

    /**
     * Marks the appointment as cancelled.
     * 
     * @return true if cancellation is successful
     */
    public boolean cancelSlot() {
        this.appointmentStatus = AppointmentStatus.CANCELLED;
        return true;
    }

    /**
     * Marks the appointment as completed.
     * 
     * @return true if completion is successful
     */
    public boolean completeSlot() {
        this.appointmentStatus = AppointmentStatus.COMPLETED;
        return true;
    }

    /**
     * Creates a new appointment slot for a doctor.
     * 
     * @param doctor The doctor for whom to create the appointment
     * @param appointmentDate The date of the appointment
     * @param appointmentTime The time of the appointment
     * @return A new Appointment instance
     */
    public static Appointment createSlot(Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime) {
        return new Appointment(doctor.getId(), doctor.getName(), appointmentDate, appointmentTime);
    }

    /**
     * Prints all details of the appointment to the console.
     * Includes doctor's name, doctor's ID, patient's ID, appointment date, appointment time, and appointment status.
     */
    public void print() {
        System.out.println("Doctor: " + this.doctorName);
        System.out.println("DoctorId: " + this.doctorId);
        if (this.patientId != null) {
            System.out.println("PatientId: " + this.patientId);
        }
        System.out.println("Date: " + this.appointmentDate.format(DateTimeFormatter.ofPattern("dd/MM/yy")));
        System.out.println("Time: " + this.appointmentTime);
        System.out.println("Status: " + this.appointmentStatus);
    }
}
