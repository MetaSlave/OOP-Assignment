import java.util.Scanner;

public class PharmacistUI implements IAuthMenu{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

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
        // Upcast user to patient
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
                    newPharmacistManager.changePassword(ph);
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
