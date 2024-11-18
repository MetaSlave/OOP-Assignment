import java.util.Scanner;
/**
* Provides the user interface for administrator staff management operations
*/
public class AdministratorManageStaffUI extends AbstractAuthMenu{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();
   /**
    * Displays the staff management menu options
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
    * Launches the staff management menu interface for authenticated administrators
    *
    * @param u The authenticated User object (administrator) accessing the menu
    *          While not directly used in this implementation, it's maintained
    *          for consistency with the interface and potential future use
    */
    @Override
    public void launchAuthMenu(User u) {
        AdministratorManageStaffManager newAdminManageStaffManager = new AdministratorManageStaffManager();
        while (true) {
            displayOptions();
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
