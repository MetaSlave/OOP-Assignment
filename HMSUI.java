import java.util.Scanner;

public class HMSUI implements IDisplayOptions{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

    @Override
    public void displayOptions() {
        System.out.println("----HMS MENU----");
        System.out.println("1. Login");
        System.out.println("2. Exit");
    }

    public void launchMenu() {
        // Instantiate HMSManager
        HMSManager newHMSManager = new HMSManager();

        // Main HMS loop
        boolean programExited = false;
        while (!programExited) {
            displayOptions();
            int choice;
            User currentUser;
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    // Login
                    currentUser = newHMSManager.login();

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
            }
        }
    }

}
