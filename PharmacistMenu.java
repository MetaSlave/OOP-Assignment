import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PharmacistMenu implements IMenu, IViewMedicineInventory, ICheckMedicineExists{
    public void display() {
        System.out.println("----PHARMACIST MENU----");
        System.out.println("1. View Appointment Outcome Records");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");
    }

    public void viewAppointmentOutcomeRecords() {
        // View appointment outcome records
        List<Appointment> completedAppointments = HMS.allAppointments
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
                AppointmentOutcome ao = HMS.allAppointmentOutcomes.get(a.getAppointmentId());
                ArrayList<Prescription> pList = HMS.allPrescriptions.get(a.getAppointmentId());
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

    public void updatePrescriptionStatus(Scanner scanner) {
        // Update prescription status
        while (true) {
            List<Prescription> pendingPrescriptions = HMS.allPrescriptions.values()
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

            System.out.print("Enter prescription number to dispense (1-" + pendingPrescriptions.size() + ", 0 to quit): ");
            int prescriptionNum = scanner.nextInt();
            scanner.nextLine();

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
    
    public void createReplenishmentRequest(Pharmacist ph, Scanner scanner) {
        // Create replenishment request
        // Input medicine name and check if it exists
        System.out.println("Which medicine do you want to submit a request for?:");
        String medicine = scanner.nextLine();

        // Check if medicine exists
        if (!checkMedicineExists(medicine)) {
            
            return;
        }

        // Check if low stock level
        if (HMS.allMedicines.get(medicine).checkStockLevel()) {
            // If it does exist
            System.out.println("How many units of " + medicine + " do you want to replenish?:");
            int replenishAmount = scanner.nextInt();

            // Add
            ReplenishmentRequest newRequest = ReplenishmentRequest.createReplenishmentRequest(ph.getId(), medicine, replenishAmount);
            HMS.allReplenishmentRequests.add(newRequest);
        }
        else {
            System.out.println("This medicine does not have low stock level");
        }
        
    }

    public void chargePrescriptions(Prescription prescription) {
        Medicine medicine = HMS.allMedicines.get(prescription.getMedication());
        double cost = medicine.getMedicineCost() * prescription.getQuantity();
        
        AppointmentOutcome appointmentOutcome = HMS.allAppointmentOutcomes.get(prescription.getAppointmentId());
        
        // Use up medicine, set cost of appointment and set prescriptionStatus to dispensed
        medicine.decreaseStock(prescription.getQuantity());
        appointmentOutcome.setAppointmentCost(cost + appointmentOutcome.getAppointmentCost());
        prescription.dispensePrescription();
        System.out.println("Prescription dispensed");
    }
}
