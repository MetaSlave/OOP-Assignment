import java.util.Scanner;
/**
* Provides the user interface for updating individual staff member details
*/
public class AdministratorManageStaffUpdateUI extends AbstractAuthMenu{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();
   /**
    * Displays the menu options for updating staff details
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
    * Launches the staff detail update menu interface
    *
    * @param u The User object (Staff member) whose details need to be updated
    */
    @Override
    public void launchAuthMenu(User u) {
        Staff staffToEdit = (Staff) u;
        AdministratorManageStaffUpdateManager newAdministratorManagerStaffUpdateManager = new AdministratorManageStaffUpdateManager();

        while (true) {
            displayOptions();
            int updateChoice;
            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    String enteredChoice = scanner.nextLine();
                    updateChoice = Integer.parseInt(enteredChoice);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid choice");
                }
            }
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
