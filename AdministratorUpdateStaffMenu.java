import java.util.Scanner;

public class AdministratorUpdateStaffMenu implements IMenu{
    public void display() {
        System.out.println("----UPDATE STAFF DETAILS----");
        System.out.println("1. Update Name");
        System.out.println("2. Update Age");
        System.out.println("3. Update Gender");
        System.out.println("4. Back");
    }

    public void updateName(Staff s) {
        Scanner scanner = new Scanner(System.in);
        // Update Name
        System.out.print("Enter new name: ");
        String updatedName = scanner.nextLine();
        s.setName(updatedName);
        System.out.println("Name successfully updated!");
    }

    public void updateAge(Staff s) {
        Scanner scanner = new Scanner(System.in);
        // Update Age
        System.out.print("Enter new age: ");
        int updatedAge = scanner.nextInt();
        scanner.nextLine();
        s.setAge(updatedAge);
        System.out.println("Age successfully updated!");
    }

    public void updateGender(Staff s) {
        Scanner scanner = new Scanner(System.in);
        // Update Gender
        System.out.println("Select new gender:");
        System.out.println("1. Male");
        System.out.println("2. Female");
        System.out.print("Enter choice: ");
        int updateGenderChoice = scanner.nextInt();
        scanner.nextLine();

        if (updateGenderChoice != 1 && updateGenderChoice != 2) {
            System.out.println("Invalid gender choice!");
            return;
        }

        String updatedGender = updateGenderChoice == 1 ? "Male" : "Female";
        s.setGender(updatedGender);
        System.out.println("Gender successfully updated!");
    }
}
