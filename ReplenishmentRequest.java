import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a request for medicine replenishment in the pharmacy system.
 * Tracks details about the requested medicine, quantity, requesting pharmacist, and request status.
 * Implements Serializable for persistent storage.
 */
public class ReplenishmentRequest implements Serializable {
    private String pharmacistId;
    private String medicineName;
    private int requestAmount;
    private LocalDateTime requestDate;
    private ReplenishmentRequestStatus replenishmentStatus;

    /**
     * Enumeration of possible replenishment request statuses.
     */
    public static enum ReplenishmentRequestStatus {
        PENDING,
        APPROVED
    }

    /**
     * Constructs a new ReplenishmentRequest with the specified details.
     * The request date is automatically set to the current time and status is set to PENDING.
     *
     * @param pharmacistId The ID of the pharmacist making the request
     * @param medicineName The name of the medicine to be replenished
     * @param requestAmount The quantity of medicine requested
     */
    public ReplenishmentRequest(String pharmacistId, String medicineName, int requestAmount) {
        this.pharmacistId = pharmacistId;
        this.medicineName = medicineName;
        this.requestAmount = requestAmount;
        this.requestDate = LocalDateTime.now();
        this.replenishmentStatus = ReplenishmentRequestStatus.PENDING;
    }

    /**
     * Deserializes replenishment requests from a file.
     * 
     * @return ArrayList of ReplenishmentRequest objects, or null if deserialization fails
     */
    protected static ArrayList<ReplenishmentRequest> deserialize() {
        ArrayList<ReplenishmentRequest> allReplenishmentRequests = null;
        try (FileInputStream fis = new FileInputStream("replenishmentRequests.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            allReplenishmentRequests = (ArrayList<ReplenishmentRequest>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allReplenishmentRequests;
    }

    /**
     * Serializes replenishment requests to a file.
     * 
     * @param allReplenishmentRequests List of requests to serialize
     * @return true if serialization is successful, false otherwise
     */
    protected static boolean serialize(ArrayList<ReplenishmentRequest> allReplenishmentRequests) {
        try (FileOutputStream fos = new FileOutputStream("replenishmentRequests.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(allReplenishmentRequests);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the current status of the replenishment request.
     * 
     * @return The current status of the request
     */
    public ReplenishmentRequestStatus getStatus() {
        return this.replenishmentStatus;
    }

    /**
     * Gets the name of the medicine requested.
     * 
     * @return The medicine name
     */
    public String getMedicineName() {
        return this.medicineName;
    }

    /**
     * Gets the quantity of medicine requested.
     * 
     * @return The requested amount
     */
    public int getRequestAmount() {
        return this.requestAmount;
    }

    /**
     * Gets the ID of the pharmacist who made the request.
     * 
     * @return The pharmacist's ID
     */
    public String getPharmacistId() {
        return this.pharmacistId;
    }

    /**
     * Gets the date and time when the request was made.
     * 
     * @return The request date and time
     */
    public LocalDateTime getRequestDate() {
        return this.requestDate;
    }

    /**
     * Creates a new replenishment request with the specified details.
     * 
     * @param pharmacistId The ID of the requesting pharmacist
     * @param medication The name of the medication to replenish
     * @param quantity The amount of medication requested
     * @return A new ReplenishmentRequest instance
     */
    public static ReplenishmentRequest createReplenishmentRequest(String pharmacistId, 
            String medication, int quantity) {
        return new ReplenishmentRequest(pharmacistId, medication, quantity);
    }

    /**
     * Approves the replenishment request by changing its status to APPROVED.
     */
    public void approveRequest() {
        this.replenishmentStatus = ReplenishmentRequestStatus.APPROVED;
    }

    /**
     * Prints all details of the replenishment request to the console.
     * Includes medication name, request amount, request date, request by, and status.
     */
    public void print() {
        System.out.println("Medication: " + this.medicineName);
        System.out.println("Request Amount: " + this.requestAmount);
        System.out.println("Request Date: " + this.requestDate.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")));
        System.out.println("Request By: " + this.pharmacistId);
        System.out.println("Status: " + this.replenishmentStatus);
    }
}
