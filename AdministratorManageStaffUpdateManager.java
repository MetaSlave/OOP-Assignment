import java.util.Scanner;
/**
* Manages the updating of staff member details in the hospital system.
* This class provides specific methods for updating individual staff attributes
* such as name, age, and gender, with appropriate validation and error handling.
*/
public class AdministratorManageStaffUpdateManager {
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();
    /**
    * Updates the name of a staff member.
    * Prompts for and sets a new name for the specified staff member,
    * providing confirmation upon successful update.
    *
    * @param s The Staff object whose name needs to be updated
    */
    public void updateName(Staff s) {
        // Update Name
        System.out.print("Enter new name: ");
        String updatedName = scanner.nextLine();
        s.setName(updatedName);
        System.out.println("Name successfully updated!");
    }
   /**
    * Updates the age of a staff member.
    * Prompts for a new age value with input validation to ensure
    * a valid numeric value is entered. Continues to prompt until
    * valid input is received.
    *
    * @param s The Staff object whose age needs to be updated
    * @throws NumberFormatException Caught internally when non-numeric input is provided
    */
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
   /**
    * Updates the gender of a staff member.
    * Presents a menu of gender options (Male/Female) and updates
    * the staff member's gender based on the selection.
    * Includes input validation and error handling for invalid choices.
    *
    * @param s The Staff object whose gender needs to be updated
    * @throws NumberFormatException Caught internally when non-numeric input is provided
    */
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
