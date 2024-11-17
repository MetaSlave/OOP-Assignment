import java.util.Scanner;

public class AdministratorManageStaffManager {
    // Use database singleton
    private final HMSDatabase db = HMSDatabase.getInstance();
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

    public void addNewStaff() {
        // Add new staff
        System.out.println("Enter staff details:");

        // Input name
        System.out.println("Name:");
        String newName = scanner.nextLine();

        // Input age
        int newAge;
        while (true) {
            try {
                System.out.println("Age: ");
                String enteredChoice = scanner.nextLine();
                newAge = Integer.parseInt(enteredChoice);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }

        // Select role
        System.out.println("Select staff role:");
        System.out.println("1. Doctor");
        System.out.println("2. Pharmacist");
        System.out.println("3. Administrator");
        System.out.println("Staff Role (enter number): ");
        int roleChoice;
        while (true) {
            try {
                String enteredChoice = scanner.nextLine();
                roleChoice = Integer.parseInt(enteredChoice);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }
        
        String newRole;
        String rolePrefix;
        switch(roleChoice) {
            case 1:
                newRole = "Doctor";
                rolePrefix = "D";
                break;
            case 2:
                newRole = "Pharmacist";
                rolePrefix = "P";
                break;
            case 3:
                newRole = "Administrator";
                rolePrefix = "A";
                break;
            default:
                System.out.println("Invalid role choice!");
                newRole = "none";
                rolePrefix = "none";
                break;
        }
        if (newRole.equals("none")) {
            return;
        }

        // Select Gender
        System.out.println("Select Gender:");
        System.out.println("1. Male");
        System.out.println("2. Female");
        System.out.println("Enter choice: ");
        int genderChoice;
        while (true) {
            try {
                String enteredChoice = scanner.nextLine();
                genderChoice = Integer.parseInt(enteredChoice);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }
        if (genderChoice != 1 && genderChoice != 2) {
            System.out.println("Invalid gender choice!");
            return;
        }
        String newGender = genderChoice == 1 ? "Male" : "Female";

        // Get highest existing ID for this role
        int maxId = db.getAllUsers().stream()
            .filter(user -> user.getRole().equals(newRole))
            .map(user -> Integer.parseInt(user.getId().substring(1)))
            .max(Integer::compareTo)
            .orElse(0);
        
        // Create new id with +1 to maxid
        String newId = String.format("%s%03d", rolePrefix, maxId + 1);

        // Create staff
        Staff newStaff;
        if (newRole.equals("Doctor")) {
            newStaff = new Doctor(newId, newRole, newName, newGender, newAge);
        } else if (newRole.equals("Pharmacist")) {
            newStaff = new Pharmacist(newId, newRole, newName, newGender, newAge);
        } else {
            newStaff = new Administrator(newId, newRole, newName, newGender, newAge);
        }

        // Add to allUsers
        db.getAllUsers().add(newStaff);
        System.out.println("Staff member added successfully with ID: " + newId);
        
    }

    public void updateStaffDetails() {
        // Update staff details (name, gender, age)
        System.out.print("Enter Staff ID to update: ");
        String staffIdToUpdate = scanner.nextLine();
        
        // Find staff member
        User staffToUpdate = db.getAllUsers().stream()
            .filter(user -> user.getId().equals(staffIdToUpdate) && !user.getRole().equals("Patient"))
            .findFirst()
            .orElse(null);

        if (staffToUpdate == null) {
            System.out.println("Staff member not found!");       
            return;
        }

        // Get instance of Update UI
        AdministratorManagerStaffUpdateUI newAdministratorManagerStaffUpdateUI = new AdministratorManagerStaffUpdateUI();
        newAdministratorManagerStaffUpdateUI.launchAuthMenu(staffToUpdate);
    }

    public void removeStaff() {
        // Remove staff
        System.out.print("Enter Staff ID to update: ");
        String staffIdToRemove = scanner.nextLine();
        
        // Find staff member
        User staffToRemove = db.getAllUsers().stream()
            .filter(user -> user.getId().equals(staffIdToRemove) && !user.getRole().equals("Patient"))
            .findFirst()
            .orElse(null);

        if (staffToRemove == null) {
            System.out.println("Staff member not found!");
            return;
        }

        // If staff is doctor, cannot remove if pending appointments
        if (staffToRemove.getRole().equals("Doctor")) {
            // Check for pending or scheduled appointments
            boolean hasPendingAppointments = db.getAllAppointments().stream()
                .anyMatch(d -> 
                    d.getDoctorId().equals(staffIdToRemove) 
                    && (d.getAppointmentStatus().equals(Appointment.AppointmentStatus.PENDING) 
                    || d.getAppointmentStatus().equals(Appointment.AppointmentStatus.SCHEDULED)
                    || d.getAppointmentStatus().equals(Appointment.AppointmentStatus.OPEN)));
            
            if (hasPendingAppointments) {
                System.out.println("Cannot remove doctor with open, pending or scheduled appointments!");
                return;
            }
        } 
        // If staff is pharmacist, cannot remove if pending replenishmentrequests
        else if (staffToRemove.getRole().equals("Pharmacist")) {
            // Check for pending replenishment requests
            boolean hasPendingRequests = db.getAllReplenishmentRequests().stream()
                .anyMatch(ph -> ph.getPharmacistId().equals(staffIdToRemove));
            
            if (hasPendingRequests) {
                System.out.println("Cannot remove pharmacist with pending replenishment requests!");
                
                return;
            }
        }

        // Confirm removal
        System.out.print("Are you sure you want to remove this staff member? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            // Remove staff
            db.getAllUsers().remove(staffToRemove);
            System.out.println("Staff member removed successfully!");
        } else {
            System.out.println("Staff removal cancelled.");
        }
        
    }
}
