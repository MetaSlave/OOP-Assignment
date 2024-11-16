import java.util.Scanner;

public class AdministratorManageStaffUI implements IAuthMenu{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

    @Override
    public void displayOptions() {
        System.out.println("----MANAGE STAFF----");
        System.out.println("1. Add Staff Member");
        System.out.println("2. Update Staff Member Details");
        System.out.println("3. Remove Staff Member");
        System.out.println("4. Back");
    }

    @Override
    public void launchAuthMenu(User u) {
        AdministratorManageStaffManager newAdminManageStaffManager = new AdministratorManageStaffManager();
        while (true) {
            displayOptions();
            System.out.print("Enter your choice: ");
            int subChoice = scanner.nextInt();
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
