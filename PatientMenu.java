import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PatientMenu implements IMenu, IViewMedicalRecord{

    @Override
    public void display() {
        System.out.println("----PATIENT MENU----");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout");
    }

    public void viewMedicalRecord(User p) {
        System.out.println("----YOUR MEDICAL RECORD----");
        ((Patient) p).print();
        ArrayList<MedicalRecord> medRecords = HMS.allMedicalRecords.getOrDefault(((Patient) p).getId(), null);
        if (medRecords == null || medRecords.isEmpty()) {
            System.out.println("You have no medical history");
        }
        else {
            for (MedicalRecord mr : medRecords) {
                mr.print();
            }
        }
    }
    public void updateContactDetails(Patient p, Scanner scanner) {
        System.out.println("Enter your new email address:");
        String email = scanner.nextLine();
        System.out.println("Enter your new contact number:");
        String contactNumber = scanner.nextLine();
        p.setEmail(email);
        p.setContactNumber(contactNumber);
        System.out.println("Contact information successfully updated!");
        
    }

    public void viewAppointmentSlots() {
        List<Appointment> openAppointments = HMS.allAppointments
            .stream()
            .filter(a -> a.getAppointmentStatus().equals(Appointment.AppointmentStatus.OPEN))
            .collect(Collectors.toList());

        if (openAppointments.isEmpty()) {
            System.out.println("There are no appointment slots available");
        }
        else {
            System.out.println("----AVAILABLE APPOINTMENT SLOTS----");
            int count = 1;
            for (Appointment a : openAppointments) {
                System.out.println("SLOT " + count++);
                a.print();
            }
        }
    }

    public void scheduleAppointment(Patient p, Scanner scanner) {
        // Choose doctor
        System.out.println("Which doctor do you want to schedule with? (id):");
        String doctorId = scanner.nextLine();

        // Choose date
        String date;
        LocalDate parsedDate;
        while (true) {
            try {
                System.out.println("Which date do you want to have your appointment on? (dd/MM/yy):");
                date = scanner.nextLine();
                parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yy"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yy (e.g., 25/12/23)");
            }
        }

        // Choose slot
        String time;
        LocalTime parsedTime;
        while (true) {
            try {
                System.out.println("Which timeslot do you want to have your appointment on? (HH:mm):");
                time = scanner.nextLine();
                parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:mm (e.g., 14:30)");
            }
        }

        final LocalDate finalParsedDate = parsedDate;
        final LocalTime finalParsedTime = parsedTime;

        // Find slot
        Appointment appointment = HMS.allAppointments
        .stream()
        .filter(a -> 
            a.getAppointmentStatus().equals(Appointment.AppointmentStatus.OPEN) 
            && a.getDoctorId().equals(doctorId) 
            && a.getAppointmentDate().equals(finalParsedDate) 
            && a.getAppointmentTime().equals(finalParsedTime))
        .findFirst()
        .orElse(null);

        if (appointment == null) {
            System.out.println("No appointment slot found for this doctor");
        }
        else {
            // Schedule slot
            appointment.schedulePatient(p.getId());
            System.out.println("Appointment slot successfully requested");
        }
    }

    public boolean cancelAppointment(Patient p, Scanner scanner) {
        // Choose doctor
        System.out.println("Which doctor did you schedule your appointment with? (id):");
        String doctorId = scanner.nextLine();

        // Choose date
        String date;
        LocalDate parsedDate;
        while (true) {
            try {
                System.out.println("Which date did you schedule your appointment on? (dd/MM/yy):");
                date = scanner.nextLine();
                parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yy"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yy (e.g., 25/12/23)");
            }
        }

        // Choose slot
        String time;
        LocalTime parsedTime;
        while (true) {
            try {
                System.out.println("Which timeslot did schedule your appointment on? (HH:mm):");
                time = scanner.nextLine();
                parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:mm (e.g., 14:30)");
            }
        }

        final LocalDate finalParsedDate = parsedDate;
        final LocalTime finalParsedTime = parsedTime;

        // Find scheduled appointment
        Appointment appointment = HMS.allAppointments
            .stream()
            .filter(a -> 
                Objects.equals(a.getPatientId(), p.getId()) 
                && Objects.equals(a.getDoctorId(), doctorId)
                && (a.getAppointmentStatus().equals(Appointment.AppointmentStatus.PENDING) || a.getAppointmentStatus().equals(Appointment.AppointmentStatus.SCHEDULED))
                && a.getAppointmentDate().equals(finalParsedDate) 
                && a.getAppointmentTime().equals(finalParsedTime))
            .findFirst()
            .orElse(null);

        if (appointment == null) {
            System.out.println("No appointment slot found to cancel");   
            return false;
        }
        else {
            // Reset slot
            appointment.resetSlot();
            System.out.println("Appointment slot successfully cancelled");
            return true;
        }
    }

    public void viewScheduledAppointments(User p) {
        List<Appointment> scheduledAppointments = HMS.allAppointments
            .stream()
            .filter(a -> 
                a.getPatientId().equals(((Patient) p).getId())
                && a.getAppointmentStatus().equals(Appointment.AppointmentStatus.SCHEDULED))
            .collect(Collectors.toList());
    
        if (scheduledAppointments.isEmpty()) {
            System.out.println("You have no scheduled appointments");
        }
        else {
            System.out.println("----YOUR SCHEDULED APPOINTMENT SLOTS----");
            int count = 1;
            for (Appointment a : scheduledAppointments) {
                System.out.println("SLOT " + count++ + "");
                a.print();
            }
        }
    }

    public void viewAppointmentOutcomeRecords(Patient p) {
        List<Appointment> completedAppointments = HMS.allAppointments
            .stream()
            .filter(a -> 
                a.getPatientId().equals(p.getId())
                && a.getAppointmentStatus().equals(Appointment.AppointmentStatus.COMPLETED))
            .collect(Collectors.toList());

        if (completedAppointments.isEmpty()) {
            System.out.println("You have no completed appointments");
            }
        else {
            System.out.println("----YOUR APPOINTMENT OUTCOMES----");
            for (Appointment a : completedAppointments) {
                AppointmentOutcome ao = HMS.allAppointmentOutcomes.get(a.getAppointmentId());
                ArrayList<Prescription> pList = HMS.allPrescriptions.get(a.getAppointmentId());

                System.out.println(ao.getOutcomeDateTime());
                System.out.println("Services Provided: " + ao.getServicesProvided());
                System.out.println("Prescriptions: ");
                for (Prescription pre : pList) {
                    pre.print();
                }

                System.out.println("Cost of Appointment: " + ao.getAppointmentCost());
                System.out.println("Consultation Notes: " + ao.getConsultationNotes());
                
            }
        }
    }
}
