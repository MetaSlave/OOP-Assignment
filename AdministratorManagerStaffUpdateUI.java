import java.util.Scanner;

public class AdministratorManagerStaffUpdateUI implements IAuthMenu{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

    @Override
    public void displayOptions() {
        System.out.println("----UPDATE STAFF DETAILS----");
        System.out.println("1. Update Name");
        System.out.println("2. Update Age");
        System.out.println("3. Update Gender");
        System.out.println("4. Back");
    }
    @Override
    public void launchAuthMenu(User u) {
        Staff staffToEdit = (Staff) u;
        AdministratorManagerStaffUpdateManager newAdministratorManagerStaffUpdateManager = new AdministratorManagerStaffUpdateManager();

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
