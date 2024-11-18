import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
/**
* Manages pharmacist-specific operations in the hospital management system.
* This class implements multiple interfaces to handle various pharmacy tasks
* including prescription management, medicine inventory, and replenishment requests.
*/
public class PharmacistManager implements IViewMedicineInventory, ICheckMedicineExists, IAuthChangePassword{
    // Use database singleton
    private final HMSDatabase db = HMSDatabase.getInstance();
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();
   /**
    * Displays outcomes of all completed appointments including prescriptions.
    * For each completed appointment, shows:
    * - Date and time
    * - Services provided
    * - Prescribed medications and quantities
    * - Consultation notes
    */
    public void viewAppointmentOutcomeRecords() {
        // View appointment outcome records
        List<Appointment> completedAppointments = db.getAllAppointments()
            .stream()
            .filter(a -> 
                a.getAppointmentStatus().equals(Appointment.AppointmentStatus.COMPLETED))
            .collect(Collectors.toList());

        if (completedAppointments.isEmpty()) {
            System.out.println("There are no completed appointments");
        }
        else {
            System.out.println("----APPOINTMENT OUTCOMES----");
            for (Appointment a : completedAppointments) {
                AppointmentOutcome ao = db.getAllAppointmentOutcomes().get(a.getAppointmentId());
                ArrayList<Prescription> pList = db.getAllPrescriptions().get(a.getAppointmentId());
                System.out.println(ao.getOutcomeDateTime());
                System.out.println("Services Provided: " + ao.getServicesProvided());
                System.out.println("Prescriptions: ");
                for (Prescription pre : pList) {
                    pre.print();
                }
                System.out.println("Consultation Notes: " + ao.getConsultationNotes());
            }
        }
    }

   /**
    * Manages the dispensing of pending prescriptions.
    * This method:
    * - Displays all pending prescriptions
    * - Allows selection of prescriptions to dispense
    * - Processes medication charges
    * - Updates prescription status
    * - Updates medicine inventory
    *
    * Handles input validation and provides appropriate error messages.
    * Continues processing until all prescriptions are handled or user exits.
    *
    * @throws NumberFormatException Caught internally for invalid numeric input
    */
    public void updatePrescriptionStatus() {
        // Update prescription status
        while (true) {
            List<Prescription> pendingPrescriptions = db.getAllPrescriptions().values()
                .stream()
                .flatMap(List::stream)
                .filter(p -> p.getStatus().equals(Prescription.Status.PENDING))
                .collect(Collectors.toList());

            if (pendingPrescriptions.isEmpty()) {
                System.out.println("There are no pending prescriptions");
                break;
            }
            System.out.println("\n----PENDING PRESCRIPTIONS----");

            // Display all pending prescriptions with index numbers
            for (int i = 0; i < pendingPrescriptions.size(); i++) {
                System.out.println("\nPrescription " + (i + 1) + ":");
                pendingPrescriptions.get(i).print();
            }

            int prescriptionNum;
            while (true) {
                try {
                    System.out.print("Enter prescription number to dispense (1-" + pendingPrescriptions.size() + ", 0 to quit): ");
                    String enteredChoice = scanner.nextLine();
                    prescriptionNum = Integer.parseInt(enteredChoice);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid choice");
                }
            }
            // Exit
            if (prescriptionNum == 0) {
                break;
            }
            // Invalid
            else if (prescriptionNum < 1 || prescriptionNum > pendingPrescriptions.size()) {
                System.out.println("Invalid prescription number");
                break;
            }
            // Charge prescription to user
            else {
                Prescription prescription = pendingPrescriptions.get(prescriptionNum - 1);
                chargePrescriptions(prescription);
            }
        }
        
    }
   /**
    * Creates a replenishment request for low-stock medicines.
    * Validates:
    * - Medicine existence in inventory
    * - Current stock level is below alert threshold
    * - Replenishment quantity is valid
    *
    * @param ph The Pharmacist creating the replenishment request
    * @throws NumberFormatException Caught internally for invalid numeric input
    */
    public void createReplenishmentRequest(Pharmacist ph) {
        // Create replenishment request
        // Input medicine name and check if it exists
        System.out.println("Which medicine do you want to submit a request for?:");
        String medicine = scanner.nextLine();

        // Check if medicine exists
        if (!checkMedicineExists(medicine)) {
            return;
        }

        // Check if low stock level
        if (db.getAllMedicines().get(medicine).checkStockLevel()) {
            // If it does exist
            int replenishAmount;
            while (true) {
                try {
                    System.out.println("How many units of " + medicine + " do you want to replenish?:");
                    String enteredChoice = scanner.nextLine();
                    replenishAmount = Integer.parseInt(enteredChoice);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid choice");
                }
            }

            // Add
            ReplenishmentRequest newRequest = ReplenishmentRequest.createReplenishmentRequest(ph.getId(), medicine, replenishAmount);
            db.getAllReplenishmentRequests().add(newRequest);
        }
        else {
            System.out.println("This medicine does not have low stock level");
        }
        
    }
   /**
    * Processes charges for dispensed prescriptions.
    * This method:
    * - Calculates total cost based on medicine cost and quantity
    * - Updates medicine inventory levels
    * - Updates appointment outcome with prescription costs
    * - Marks prescription as dispensed
    *
    * @param prescription The Prescription to be charged and dispensed
    */
    public void chargePrescriptions(Prescription prescription) {
        Medicine medicine = db.getAllMedicines().get(prescription.getMedication());
        double cost = medicine.getMedicineCost() * prescription.getQuantity();
        
        AppointmentOutcome appointmentOutcome = db.getAllAppointmentOutcomes().get(prescription.getAppointmentId());
        
        // Use up medicine, set cost of appointment and set prescriptionStatus to dispensed
        medicine.decreaseStock(prescription.getQuantity());
        appointmentOutcome.setAppointmentCost(cost + appointmentOutcome.getAppointmentCost());
        prescription.dispensePrescription();
        System.out.println("Prescription dispensed");
    }
}
