import java.util.Scanner;
/**
* Provides the user interface for updating individual staff member details.
* This class extends AbstractAuthMenu to implement a menu-driven interface
* that allows administrators to modify specific attributes of staff members,
* such as name, age, and gender.
*/
public class AdministratorManageStaffUpdateUI extends AbstractAuthMenu{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();
   /**
    * Displays the menu options for updating staff details.
    * Shows a formatted list of all available update operations:
    * - Updating name
    * - Updating age
    * - Updating gender
    * - Returning to previous menu
    */
    @Override
    public void displayOptions() {
        System.out.println("----UPDATE STAFF DETAILS----");
        System.out.println("1. Update Name");
        System.out.println("2. Update Age");
        System.out.println("3. Update Gender");
        System.out.println("4. Back");
    }
    /**
    * Launches the staff detail update menu interface.
    * This method:
    * - Casts the provided User to Staff for specific update operations
    * - Creates an update manager instance to handle operations
    * - Runs in a loop until user chooses to exit
    * - Delegates update operations to the appropriate manager methods
    * - Handles invalid input with appropriate error messages
    *
    * @param u The User object (Staff member) whose details need to be updated.
    */
    @Override
    public void launchAuthMenu(User u) {
        Staff staffToEdit = (Staff) u;
        AdministratorManageStaffUpdateManager newAdministratorManagerStaffUpdateManager = new AdministratorManageStaffUpdateManager();

        while (true) {
            displayOptions();
            System.out.print("Enter your choice: ");
            int updateChoice = scanner.nextInt();
            scanner.nextLine();
            switch(updateChoice) {
                case 1: 
                    newAdministratorManagerStaffUpdateManager.updateName(staffToEdit);
                    break;
                case 2: 
                    newAdministratorManagerStaffUpdateManager.updateAge(staffToEdit);
                    break;
                case 3: 
                    newAdministratorManagerStaffUpdateManager.updateGender(staffToEdit);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}
