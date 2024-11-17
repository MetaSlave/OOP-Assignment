import java.util.Scanner;

public class PatientUI implements IAuthMenu{
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

    @Override
    public void displayOptions() {
        System.out.println("----PATIENT MENU----");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Change Password");
        System.out.println("10. Logout");
    }

    @Override
    public void launchAuthMenu(User u) {
        // Upcast user to patient
        Patient p = (Patient) u;

        boolean loggedIn = true;

        // Instantiate Patient Manager
        PatientManager newPatientManager = new PatientManager();

        // Patient UI Loop
        while (loggedIn) {
            displayOptions();
            int patientChoice;
            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    String enteredChoice = scanner.nextLine();
                    patientChoice = Integer.parseInt(enteredChoice);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid choice");
                }
            }
            
            switch (patientChoice) {
                case 1:
                    // View Medical Record
                    newPatientManager.viewMedicalRecord(p);
                    break;
                case 2:
                    // Update Personal Information
                    newPatientManager.updateContactDetails(p);
                    break;
                case 3:
                    // View Available Appointment Slots
                    newPatientManager.viewAppointmentSlots();
                    break;
                case 4:
                    // Schedule an Appointment
                    newPatientManager.scheduleAppointment(p);
                    break;
                case 5:
                    // Reschedule an Appointment
                    if (newPatientManager.cancelAppointment(p)) {
                        newPatientManager.scheduleAppointment(p);
                    }
                    break;
                case 6:
                    // Cancel an Appointment
                    newPatientManager.cancelAppointment(p);
                    break;
                case 7:
                    // View Scheduled Appointments
                    newPatientManager.viewScheduledAppointments(p);
                    break;
                case 8:
                    // View Past Appointment Outcome Records
                    newPatientManager.viewAppointmentOutcomeRecords(p);
                    break;
                case 9:
                    newPatientManager.changePassword(p);
                    break;
                case 10:
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}
