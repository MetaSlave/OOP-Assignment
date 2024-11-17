import java.util.Scanner;
/**
* Provides the main user interface for doctor operations in the hospital system.
* This class extends AbstractAuthMenu to implement a comprehensive menu system
* allowing doctors to manage patient records, appointments, consultations, and
* personal settings.
*/
public class DoctorUI extends AbstractAuthMenu {
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();
    /**
    * Displays the main doctor menu options.
    * Shows a formatted list of all available doctor operations including:
    * - Patient medical record management
    * - Appointment scheduling and management
    * - Consultation recording
    * - Personal settings
    * Each option is numbered for easy selection.
    */
    @Override
    public void displayOptions() {
        System.out.println("----DOCTOR MENU----");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Cancel an Open Appointment");
        System.out.println("6. Accept or Decline Appointment Requests");
        System.out.println("7. View Upcoming Appointments");
        System.out.println("8. Record Appointment Outcome");
        System.out.println("9. Change Password");
        System.out.println("10. Logout");
    }
    /**
    * Launches the main doctor interface with full functionality.
    * This method:
    * - Casts the provided User to Doctor type
    * - Creates a DoctorManager instance for operations
    * - Runs in a loop until logout is selected
    * - Processes user input and delegates to appropriate manager methods
    * - Provides input validation and error handling
    *
    * The menu provides access to:
    * 1. Medical record viewing and updating
    * 2. Schedule management and appointment handling
    * 3. Consultation outcome recording
    * 4. Password management
    *
    * @param u The User object to be cast to Doctor for medical operations.
    * @throws NumberFormatException Caught internally when non-numeric input is provided
    */
    @Override
    public void launchAuthMenu(User u) {
        // Upcast user to doctor
        Doctor d = (Doctor) u;

        boolean loggedIn = true;

        // Create instance of DoctorManager
        DoctorManager newDoctorManager = new DoctorManager();

        // Doctor UI Loop
        while (loggedIn) {
            displayOptions();
            int doctorChoice;
            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    String enteredChoice = scanner.nextLine();
                    doctorChoice = Integer.parseInt(enteredChoice);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid choice");
                }
            }
            switch (doctorChoice) {
                case 1:
                    // View Patient Medical Records
                    newDoctorManager.viewMedicalRecord(d);
                    break;
                case 2:
                    // Update Patient Medical Records
                    newDoctorManager.updateMedicalRecord(d);
                    break;
                case 3:
                    // View Personal Schedule
                    newDoctorManager.viewPersonalSchedule(d);
                    break;
                case 4:
                    // Set Availability for Appointments
                    newDoctorManager.createNewAppointment(d);
                    break;
                case 5:
                    // Cancel Appointment
                    newDoctorManager.cancelAppointment(d);
                case 6:
                    // Accept or Decline Appointment Requests
                    newDoctorManager.acceptOrDeclineAppointmentRequests(d);
                    break;
                case 7:
                    // View Upcoming Appointments
                    newDoctorManager.viewScheduledAppointments(d);
                    break;
                case 8:
                    // Record Appointment Outcome
                    newDoctorManager.recordAppointmentOutcome(d);
                    break;
                case 9:
                    newDoctorManager.changePassword((User) d);
                    break;
                case 10:
                    loggedIn = false;
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}
