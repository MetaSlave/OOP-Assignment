import java.util.Scanner;

public class AdministratorUI implements IAuthMenu{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

    @Override
    public void displayOptions() {
        System.out.println("----ADMINISTRATOR MENU----");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Change Password");
        System.out.println("6. Logout");
    }

    @Override
    public void launchAuthMenu(User u) {
        Administrator admin = (Administrator) u;

        boolean loggedIn = true;

        // Create instance of Administrator Manager
        AdministratorManager newAdminManager = new AdministratorManager();
        
        // Administrator UI Loop
        while (loggedIn) {
            displayOptions();
            int adminChoice;
            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    String enteredChoice = scanner.nextLine();
                    adminChoice = Integer.parseInt(enteredChoice);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid choice");
                }
            }
            switch (adminChoice) {
                case 1:
                    // View Manage Hospital Staff Menu
                    // Create instance of AdministratorManageStaffUI
                    AdministratorManageStaffUI newAdministratorManageStaffUI = new AdministratorManageStaffUI();
                    newAdministratorManageStaffUI.launchAuthMenu(admin);
                    break;
                case 2:
                    // View Appointments Details
                    newAdminManager.viewAppointmentDetails();
                    break;
                case 3:
                    // View and Manage Medication Inventory
                    newAdminManager.viewAndManageMedicationInventory();
                    break;
                case 4:
                    // Approve Replenishment Requests
                    newAdminManager.approveReplenishmentRequests();
                    break;
                case 5:
                    newAdminManager.changePassword(admin);
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
