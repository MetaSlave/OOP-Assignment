import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
/**
* Manages administrative operations in the hospital management system.
* This class implements multiple interfaces to handle various administrative tasks
* including appointment viewing, medicine inventory management, and replenishment
* request approvals.
*/
public class AdministratorManager implements ICheckMedicineExists, IViewMedicineInventory, IAuthChangePassword{
    // Use database singleton
    private final HMSDatabase db = HMSDatabase.getInstance();
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();
   /**
    * Displays all appointments in the system.
    * Lists complete details of every appointment, regardless of status,
    * using the appointment's print method for formatting.
    */
    public void viewAppointmentDetails() {
        // View all appointment details
        System.out.println("\n----APPOINTMENTS DETAILS----");
        for (Appointment appointment : db.getAllAppointments()) {
            appointment.print();
        }
    }
   /**
    * Manages the medication inventory by allowing view and update operations.
    * This method:
    * - Displays the current medicine inventory
    * - Allows updating low stock alert levels for specific medicines
    * - Includes input validation for new alert levels
    * - Provides confirmation messages for successful updates
    *
    * If the specified medicine doesn't exist, the operation is cancelled.
    *
    * @throws NumberFormatException Caught internally when non-numeric input is provided
    */
    public void viewAndManageMedicationInventory() {
        // View and Manage Medication Inventory
        viewMedicineInventory();
        System.out.println("Enter a medicine name to update low stock alert level: ");

        String medicine = scanner.nextLine();

        // Check if medicine exists
        if (!checkMedicineExists(medicine)) {
            return;
        }
        int newAlertLevel;
        while (true) {
            try {
                System.out.println("Enter new low stock alert level: ");
                String enteredChoice = scanner.nextLine();
                newAlertLevel = Integer.parseInt(enteredChoice);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }

        Medicine medicineToUpdate = db.getAllMedicines().get(medicine);
        medicineToUpdate.setAlertLevel(newAlertLevel);
        System.out.println("Alert level for " + medicine + " sucessfully updated to " + newAlertLevel);
        
    }
   /**
    * Processes pending replenishment requests for medicines.
    * This method provides a complete workflow for handling requests:
    * - Displays all pending replenishment requests
    * - Allows selection of specific requests for approval
    * - Updates request status upon approval
    * - Automatically updates medicine stock levels
    * - Provides feedback for each action
    * 
    * The process continues until either:
    * - All pending requests are handled
    * - The administrator chooses to exit
    * - An invalid selection is made
    *
    * @throws NumberFormatException Caught internally when non-numeric input is provided
    */
    public void approveReplenishmentRequests() {
        // Approve Replenishment Requests
        while (true) {
            // Get all not approved replenishment requests
            List<ReplenishmentRequest> pendingRequests = db.getAllReplenishmentRequests()
                .stream()
                .filter(r -> r.getStatus().equals(ReplenishmentRequest.ReplenishmentRequestStatus.PENDING))
                .collect(Collectors.toList());
        
            if (pendingRequests.isEmpty()) {
                System.out.println("There are no pending requests");
                break;
            }
            System.out.println("\n----PENDING REQUESTS----");

            // Display all pending requests with index numbers
            for (int i = 0; i < pendingRequests.size(); i++) {
                System.out.println("\nRequest " + (i + 1) + ":");
                pendingRequests.get(i).print();
            }
            int requestNum;
            while (true) {
                try {
                    System.out.println("Enter request number to approve (1-" + pendingRequests.size() + ", 0 to quit): ");
                    String enteredChoice = scanner.nextLine();
                    requestNum = Integer.parseInt(enteredChoice);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number");
                }
            }
            // Exit
            if (requestNum == 0) {
                break;
            }
            // Invalid
            else if (requestNum < 1 || requestNum > pendingRequests.size()) {
                System.out.println("Invalid request number");
                break;
            }
            // Update request and medicine
            else {
                ReplenishmentRequest selectedRequest = pendingRequests.get(requestNum - 1);
                
                // Approve request
                selectedRequest.approveRequest();
                System.out.println("Request approved");

                // Replenish medicine amount
                String medicineName = selectedRequest.getMedicineName();
                int replenishAmount = selectedRequest.getRequestAmount();
                Medicine foundMedicine = db.getAllMedicines().get(medicineName);
                foundMedicine.replenishStock(replenishAmount);
                System.out.println("Stock for " + medicineName + " has been replenished with " + replenishAmount + " units");
            }
        }
        
    }
}
