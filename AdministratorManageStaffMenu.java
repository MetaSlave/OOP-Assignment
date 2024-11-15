import java.util.Scanner;

public class AdministratorManageStaffMenu implements IMenu{
    @Override
    public void display() {
        System.out.println("----MANAGE STAFF----");
        System.out.println("1. Add Staff Member");
        System.out.println("2. Update Staff Member Details");
        System.out.println("3. Remove Staff Member");
        System.out.println("4. Back");
    }

    public void addNewStaff(Scanner scanner) {
        // Add new staff
        System.out.println("Enter staff details:");

        // Input name
        System.out.print("Name:");
        String newName = scanner.nextLine();

        // Input age
        System.out.print("Age: ");
        int newAge = scanner.nextInt();

        // Select role
        System.out.print("Select staff role:");
        System.out.print("1. Doctor");
        System.out.print("2. Pharmacist");
        System.out.print("3. Administrator");
        System.out.print("Staff Role (enter number): ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();
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
        System.out.print("Enter choice: ");
        int genderChoice = scanner.nextInt();
        scanner.nextLine();
        if (genderChoice != 1 && genderChoice != 2) {
            System.out.println("Invalid gender choice!");
            
            return;
        }
        String newGender = genderChoice == 1 ? "Male" : "Female";

        // Get highest existing ID for this role
        int maxId = HMS.allUsers.stream()
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
        HMS.allUsers.add(newStaff);
        System.out.println("Staff member added successfully with ID: " + newId);
        
    }

    public void updateStaffDetails(Scanner scanner) {
        // Update staff details (name, gender, age)
        System.out.print("Enter Staff ID to update: ");
        String staffIdToUpdate = scanner.nextLine();
        
        // Find staff member
        User staffToUpdate = HMS.allUsers.stream()
            .filter(user -> user.getId().equals(staffIdToUpdate) && !user.getRole().equals("Patient"))
            .findFirst()
            .orElse(null);

        if (staffToUpdate == null) {
            System.out.println("Staff member not found!");
            
            return;
        }

        Staff staff = (Staff) staffToUpdate;
        boolean subBack = true;
        while (subBack) {
            AdministratorUpdateStaffMenu newAdminUpdateStaffMenu = new AdministratorUpdateStaffMenu();

            newAdminUpdateStaffMenu.display();
            System.out.print("Enter your choice: ");
            int updateChoice = scanner.nextInt();
            scanner.nextLine();
            switch(updateChoice) {
                case 1: 
                    newAdminUpdateStaffMenu.updateName(staff);
                    break;
                case 2: 
                    newAdminUpdateStaffMenu.updateAge(staff);
                    break;
                case 3: 
                    newAdminUpdateStaffMenu.updateGender(staff);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
        
    }

    public void removeStaff(Scanner scanner) {
        // Remove staff
        System.out.print("Enter Staff ID to update: ");
        String staffIdToRemove = scanner.nextLine();
        
        // Find staff member
        User staffToRemove = HMS.allUsers.stream()
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
            boolean hasPendingAppointments = HMS.allAppointments.stream()
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
            boolean hasPendingRequests = HMS.allReplenishmentRequests.stream()
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
            HMS.allUsers.remove(staffToRemove);
            System.out.println("Staff member removed successfully!");
        } else {
            System.out.println("Staff removal cancelled.");
        }
        
    }
}
