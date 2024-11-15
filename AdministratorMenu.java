import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AdministratorMenu implements IMenu, ICheckMedicineExists, IViewMedicineInventory{
    public void display() {
        System.out.println("----ADMINISTRATOR MENU----");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }

    public void viewAndManageHospitalStaff(Scanner scanner) {
        AdministratorManageStaffMenu newAdminManageStaffMenu = new AdministratorManageStaffMenu();
        while (true) {
            newAdminManageStaffMenu.display();
            System.out.print("Enter your choice: ");
            int subChoice = scanner.nextInt();
            scanner.nextLine();
            switch(subChoice) {
                case 1:
                    newAdminManageStaffMenu.addNewStaff(scanner);
                    break;
                case 2:
                    newAdminManageStaffMenu.updateStaffDetails(scanner);
                    break;
                case 3:
                    newAdminManageStaffMenu.removeStaff(scanner);
                    break;
                case 4:
                    
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;

            }
        }
    }

    public void viewAppointmentDetails() {
        // View all appointment details
        System.out.println("\n----APPOINTMENTS DETAILS----");
        for (Appointment appointment : HMS.allAppointments) {
            appointment.print();
        }
    }

    public void viewAndManageMedicationInventory(Scanner scanner) {
        // View and Manage Medication Inventory
        viewMedicineInventory();
        System.out.print("Enter a medicine name to update low stock alert level: ");
        
        String medicine = scanner.nextLine();

        // Check if medicine exists
        if (!checkMedicineExists(medicine)) {
            
            return;
        }

        System.out.print("Enter new low stock alert level: ");
        int newAlertLevel = scanner.nextInt();

        Medicine medicineToUpdate = HMS.allMedicines.get(medicine);
        medicineToUpdate.setAlertLevel(newAlertLevel);
        System.out.print("Alert level for " + medicine + " sucessfully updated to " + newAlertLevel);
        
    }

    public void approveReplenishmentRequests(Scanner scanner) {
        // Approve Replenishment Requests
        while (true) {
            // Get all not approved replenishment requests
            List<ReplenishmentRequest> pendingRequests = HMS.allReplenishmentRequests
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
                Medicine foundMedicine = HMS.allMedicines.get(medicineName);
                foundMedicine.replenishStock(replenishAmount);
                System.out.println("Stock for " + medicineName + " has been replenished with " + replenishAmount + " units");
            }
        }
        
    }
}
