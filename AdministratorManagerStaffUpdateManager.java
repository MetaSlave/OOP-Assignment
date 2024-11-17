import java.util.Scanner;

public class AdministratorManagerStaffUpdateManager {
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

    public void updateName(Staff s) {
        // Update Name
        System.out.print("Enter new name: ");
        String updatedName = scanner.nextLine();
        s.setName(updatedName);
        System.out.println("Name successfully updated!");
    }

    public void updateAge(Staff s) {
        // Update Age
        int updatedAge;
        while (true) {
            try {
                System.out.print("Enter new age: ");
                String enteredChoice = scanner.nextLine();
                updatedAge = Integer.parseInt(enteredChoice);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }
        s.setAge(updatedAge);
        System.out.println("Age successfully updated!");
    }

    public void updateGender(Staff s) {
        // Update Gender
        System.out.println("Select new gender:");
        System.out.println("1. Male");
        System.out.println("2. Female");
        System.out.print("Enter choice: ");
        int updateGenderChoice;
        while (true) {
            try {
                System.out.print("Enter new age: ");
                String enteredChoice = scanner.nextLine();
                updateGenderChoice = Integer.parseInt(enteredChoice);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }

        if (updateGenderChoice != 1 && updateGenderChoice != 2) {
            System.out.println("Invalid gender choice!");
            return;
        }

        String updatedGender = updateGenderChoice == 1 ? "Male" : "Female";
        s.setGender(updatedGender);
        System.out.println("Gender successfully updated!");
    }
}
