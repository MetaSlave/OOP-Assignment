import java.util.Scanner;
/**
* Provides the main user interface for pharmacist operations in the HMS
* Allows pharmacist to manage prescriptions, view medical records, monitor inventory, etc.
*/
public class PharmacistUI extends AbstractAuthMenu{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

    /**
    * Launches the main pharmacist interface (runs in a loop until logout is selected)
    * Provides access to:
    * 1. Medical record and prescription viewing
    * 2. Prescription status management
    * 3. Medicine inventory monitoring
    * 4. Stock replenishment requests
    * 5. Password management
    *
    * @param u The User object to be cast to Pharmacist for pharmacy operations.
    * @throws NumberFormatException Caught internally when non-numeric input is provided
    */

    @Override
    public void displayOptions() {
        System.out.println("----PHARMACIST MENU----");
        System.out.println("1. View Appointment Outcome Records");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Change Password");
        System.out.println("6. Logout");
    }

   
    @Override
    public void launchAuthMenu(User u) {
        // Downcast user to patient
        Pharmacist ph = (Pharmacist) u;

        boolean loggedIn = true;

        // Instantiate Pharmacist Manager
        PharmacistManager newPharmacistManager = new PharmacistManager();

        // Pharmacist UI Loop
        while (loggedIn) {
            displayOptions();
            int pharmacistChoice;
            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    String enteredChoice = scanner.nextLine();
                    pharmacistChoice = Integer.parseInt(enteredChoice);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid choice");
                }
            }
            switch (pharmacistChoice) {
                case 1:
                    // View Appointment Outcome Records
                    newPharmacistManager.viewAppointmentOutcomeRecords();
                    break;
                case 2:
                    // Update Prescription Status
                    newPharmacistManager.updatePrescriptionStatus();
                    break;
                case 3:
                    // View Medication Inventory
                    newPharmacistManager.viewMedicineInventory();
                    break;
                case 4:
                    // Submit Replenishment Request
                    newPharmacistManager.createReplenishmentRequest(ph);
                    break;
                case 5:
                    newPharmacistManager.changePassword((User) ph);
                    break;
                case 6:
                    loggedIn = false;
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}
