import java.util.Scanner;
/**
* Provides the user interface for administrator staff management operations.
* This class extends AbstractAuthMenu to implement a menu-driven interface
* for managing hospital staff members, including adding, updating, and removing staff.
*/

public class AdministratorManageStaffUI extends AbstractAuthMenu{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();
   /**
    * Displays the staff management menu options.
    * Shows a formatted list of all available staff management operations:
    * - Adding new staff members
    * - Updating existing staff details
    * - Removing staff members
    * - Returning to previous menu
    */
    @Override
    public void displayOptions() {
        System.out.println("----MANAGE STAFF----");
        System.out.println("1. Add Staff Member");
        System.out.println("2. Update Staff Member Details");
        System.out.println("3. Remove Staff Member");
        System.out.println("4. Back");
    }
   /**
    * Launches the staff management menu interface for authenticated administrators.
    * This method:
    * - Creates a staff manager instance to handle operations
    * - Runs in a loop until user chooses to exit
    * - Processes user input and delegates to appropriate manager methods
    * - Handles invalid input with appropriate error messages
    *
    * @param u The authenticated User object (administrator) accessing the menu.
    *          While not directly used in this implementation, it's maintained
    *          for consistency with the interface and potential future use.
    */
    @Override
    public void launchAuthMenu(User u) {
        AdministratorManageStaffManager newAdminManageStaffManager = new AdministratorManageStaffManager();
        while (true) {
            displayOptions();
            System.out.print("Enter your choice: ");
            int subChoice;
            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    String enteredChoice = scanner.nextLine();
                    subChoice = Integer.parseInt(enteredChoice);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid choice");
                }
            }
            scanner.nextLine();
            switch(subChoice) {
                case 1:
                    newAdminManageStaffManager.addNewStaff();
                    break;
                case 2:
                    newAdminManageStaffManager.updateStaffDetails();
                    break;
                case 3:
                    newAdminManageStaffManager.removeStaff();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;

            }
        }
    }
}
