import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AdministratorManager implements ICheckMedicineExists, IViewMedicineInventory{
    // Use database singleton
    private final HMSDatabase db = HMSDatabase.getInstance();
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

    public void viewAppointmentDetails() {
        // View all appointment details
        System.out.println("\n----APPOINTMENTS DETAILS----");
        for (Appointment appointment : db.getAllAppointments()) {
            appointment.print();
        }
    }

    public void viewAndManageMedicationInventory() {
        // View and Manage Medication Inventory
        viewMedicineInventory();
        System.out.println("Enter a medicine name to update low stock alert level: ");
        
        String medicine = scanner.nextLine();

        // Check if medicine exists
        if (!checkMedicineExists(medicine)) {
            
            return;
        }

        System.out.println("Enter new low stock alert level: ");
        int newAlertLevel = scanner.nextInt();

        Medicine medicineToUpdate = db.getAllMedicines().get(medicine);
        medicineToUpdate.setAlertLevel(newAlertLevel);
        System.out.println("Alert level for " + medicine + " sucessfully updated to " + newAlertLevel);
        
    }

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

            System.out.print("Enter request number to approve (1-" + pendingRequests.size() + ", 0 to quit): ");
            int requestNum = scanner.nextInt();
            scanner.nextLine();
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
