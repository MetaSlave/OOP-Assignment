import java.util.Scanner;
/**
* Provides the main user interface for the Hospital Management System
*/
public class HMSUI implements IDisplayOptions{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();
   /**
    * Displays the main HMS menu options
    */
    @Override
    public void displayOptions() {
        System.out.println("----HMS MENU----");
        System.out.println("1. Login");
        System.out.println("2. Exit");
    }
   /**
    * Launches the main HMS interface and manages the program flow
    * @throws NumberFormatException Caught internally for invalid numeric input
    */
    public void launchMenu() {
        // Instantiate HMSManager
        HMSManager newHMSManager = new HMSManager();

        // Main HMS loop
        boolean programExited = false;
        while (!programExited) {
            displayOptions();
            int choice;
            User currentUser;
            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    String enteredChoice = scanner.nextLine();
                    choice = Integer.parseInt(enteredChoice);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid choice");
                }
            }
            switch (choice) {
                case 1:
                    // Login
                    currentUser = newHMSManager.login();
                    if (currentUser == null) {
                        break;
                    }

                    // After login
                    if (currentUser.getRole().equals("Patient")) {
                        // Create instance of PatientUI
                        PatientUI newPatientUI = new PatientUI();
                        newPatientUI.launchAuthMenu(currentUser);
                    }
                    else if (currentUser.getRole().equals("Doctor")) {
                        // Create instance of DoctorUI
                        DoctorUI newDoctorUI = new DoctorUI();
                        newDoctorUI.launchAuthMenu(currentUser);
                    }
                    else if (currentUser.getRole().equals("Pharmacist")) {
                        // Create instance of PharmacistUI
                        PharmacistUI newPharmacistUI = new PharmacistUI();
                        newPharmacistUI.launchAuthMenu(currentUser);
                    }
                    else if (currentUser.getRole().equals("Administrator")){
                        // Create instance of AdministratorUI
                        AdministratorUI newAdministratorUI = new AdministratorUI();
                        newAdministratorUI.launchAuthMenu(currentUser);    
                    }
                    else {
                        System.out.println("Role not recognized");
                        programExited = true;
                    }
                    break;
                case 2:
                    programExited = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid choice");
                    break;
            }
        }
    }

}
