import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DoctorManager implements IViewMedicalRecord, IViewScheduledAppointments, ICheckMedicineExists, IAuthChangePassword {
    // Use database singleton
    private final HMSDatabase db = HMSDatabase.getInstance();
    // Use scanner singleton
    private final Scanner scanner = HMSInput.getInstance().getScanner();

    public void viewMedicalRecord(User d) {
        // Get all patients associated with doctor
        List<String> patientIds = db.getAllAppointments().stream()
            .filter(a -> 
            a.getDoctorId().equals(((Doctor) d).getId())) 
            .map(a -> a.getPatientId())
            .distinct() 
            .collect(Collectors.toList());
        
        if (patientIds.isEmpty()) {
            System.out.println("You have no patients");
        }
        else {
            // Get patient objects from patientId
            List<Patient> patients = db.getAllUsers().stream()
                .filter(user -> patientIds.contains(user.getId()))
                .map(user -> (Patient) user)
                .collect(Collectors.toList());
            
            System.out.println("----PATIENT RECORDS----");
            // Print each patient details
            for (Patient p : patients) {
                p.print();
                ArrayList<MedicalRecord> medRecords = db.getAllMedicalRecords().getOrDefault(p.getId(), null);
                if (medRecords == null) {
                    System.out.println("Patient has no medical history");
                }
                else {
                    for (MedicalRecord mr : medRecords) {
                        mr.print();
                        System.out.println("");
                    }
                }
            }
        }
    }
    
    public void updateMedicalRecord(Doctor d) {
        // Update medical records (basically create a new record)
        // Choose patientId
        System.out.println("For which patient do you want to update records? (patient id):");
        String patientId = scanner.nextLine();

        // Find patient from patientId from allUsers
        Patient patient = (Patient) db.getAllUsers().stream()
            .filter(user -> user.getId().equals(patientId))
            .findFirst()
            .orElse(null);

        if (patient == null) {
            System.out.println("Patient does not exist");
        }
        else {
            Appointment appointment = db.getAllAppointments().stream()
                .filter(a -> a.getDoctorId().equals(d.getId()) && a.getPatientId().equals(patientId))
                .findFirst()
                .orElse(null);
            if (appointment == null) {
                System.out.println("Patient is not under your care");
            }
            else {
                System.out.println("Diagnosis:");
                String diagnosis = scanner.nextLine();

                System.out.println("Treatment:");
                String treatment = scanner.nextLine();

                MedicalRecord newMedicalRecord = MedicalRecord.createRecord(patientId, diagnosis, treatment);
                db.getAllMedicalRecords().computeIfAbsent(patientId, k -> new ArrayList<>()).add(newMedicalRecord);

                System.out.println("Medical record successfully updated");
            }
        }
    }
    
    public void viewPersonalSchedule(Doctor d) {
        // View appointment slots (both confirmed and not confirmed)
        List<Appointment> doctorAppointments = db.getAllAppointments()
            .stream()
            .filter(a -> 
                a.getDoctorId().equals(d.getId())
                && !a.getAppointmentStatus().equals(Appointment.AppointmentStatus.COMPLETED))
            .collect(Collectors.toList());

        if (doctorAppointments.isEmpty()) {
            System.out.println("You have no appointment slots");
        }
        else {
            System.out.println("----YOUR APPOINTMENT SLOTS----");
            int count = 1;
            for (Appointment a : doctorAppointments) {
                System.out.println("SLOT " + count++ + "");
                a.print();
            }
        }
    }
    
    public void createNewAppointment(Doctor d) {
        // Create new appointment
        // Choose date
        String date;
        LocalDate parsedDate;
        while (true) {
            try {
                System.out.println("Which date do you want to create an appointment slot on? (dd/MM/yy):");
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
                System.out.println("Which timeslot do you want to create an appointment slot on? (HH:mm):");
                time = scanner.nextLine();
                parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));

                // Validate minutes are in 30-minute increments
                if (parsedTime.getMinute() % 30 != 0) {
                    System.out.println("Please choose a time with minutes in 30-minute intervals (00 or 30)");
                }
                else {
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:mm (e.g., 14:30)");
            }
        }

        final LocalDate finalParsedDate = parsedDate;
        final LocalTime finalParsedTime = parsedTime;

        // Find if appointment already exists
        boolean alreadyExists = db.getAllAppointments()
            .stream()
            .filter(a -> a.getDoctorId().equals(d.getId()) 
                && a.getAppointmentDate().equals(finalParsedDate) 
                && a.getAppointmentTime().equals(finalParsedTime))
            .findFirst()
            .isPresent();
        
        if (alreadyExists) {
            System.out.println("You already have an existing appointment slot at that time");
        }
        else {
            // Create new slot and add to allAppointments
            db.getAllAppointments().add(Appointment.createSlot(d, parsedDate, parsedTime));
            System.out.println("Appointment slot successfully created.");
        }
    }
    
    public void acceptOrDeclineAppointmentRequests(Doctor d) {
        // Accept or decline appointment requests
        // Get appointments that are pending and belong to doctor
        List<Appointment> pendingAppointments = db.getAllAppointments()
            .stream()
            .filter(a -> a.getDoctorId().equals(d.getId())
                && a.getAppointmentStatus().equals(Appointment.AppointmentStatus.PENDING))
            .collect(Collectors.toList());

        if (pendingAppointments.isEmpty()) {
            // If no pending appointments
            System.out.println("You have no pending appointment requests");
        }
        else {
            // Print everything and allow doctor to accept or decline
            System.out.println("----YOUR PENDING APPOINTMENT SLOTS----");
            int count = 1;
            for (Appointment a : pendingAppointments) {
                System.out.println("SLOT " + count++ + "");
                a.print();
                System.out.println("Accept appointment? (y/n)");

                // Ensure only y/n input
                while (true) {
                    String yesNo = scanner.nextLine().trim().toLowerCase();
                    if (yesNo.equals("y")) {
                        a.approveSlot();
                        System.out.println("Appointment accepted");
                        break;
                    }
                    else if (yesNo.equals("n")) {
                        a.resetSlot();
                        System.out.println("Appointment declined");
                        break;
                    }
                    else {
                        System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no:");
                    }
                }
            }
        }
    }
    
    public void viewScheduledAppointments(User d) {
        // View appointment slots (confirmed only)
        List<Appointment> confirmedAppointments = db.getAllAppointments()
            .stream()
            .filter(a -> a.getDoctorId().equals(d.getId()) && a.getAppointmentStatus().equals(Appointment.AppointmentStatus.SCHEDULED))
            .collect(Collectors.toList());

        if (confirmedAppointments.isEmpty()) {
            System.out.println("You have no upcoming appointment slots");
        }
        else {
            System.out.println("----YOUR UPCOMING APPOINTMENT SLOTS----");
            for (Appointment a : confirmedAppointments) {
                a.print();
            }
        }
    }
    
    public void recordAppointmentOutcome(Doctor d) {
        // Record Appointment Outcome
        // Choose date
        String date;
        LocalDate parsedDate;
        while (true) {
            try {
                System.out.println("Which date is the appointment on? (dd/MM/yy):");
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
                // Choose slot
                System.out.println("Which timeslot is the appointment on? (HH:mm):");
                time = scanner.nextLine();
                parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:mm (e.g., 14:30)");
            }
        }

        final LocalDate finalParsedDate = parsedDate;
        final LocalTime finalParsedTime = parsedTime;

        // Find Appointment
        Appointment appointment = db.getAllAppointments()
        .stream()
        .filter(a -> a.getDoctorId().equals(d.getId()) 
            && a.getAppointmentDate().equals(finalParsedDate) 
            && a.getAppointmentTime().equals(finalParsedTime)
            && a.getAppointmentStatus().equals(Appointment.AppointmentStatus.SCHEDULED))
        .findFirst()
        .orElse(null);
        
        // If not found
        if (Objects.isNull(appointment)) {
            System.out.println("No scheduled appointment found");
            return;
        }

        // Input services provided
        System.out.println("What are the services provided?:");
        String servicesProvided = scanner.nextLine();

        // Input medications
        List<Prescription> prescribedMedicationsList = new ArrayList<>();
        while (true) {
            System.out.println("What are the prescribed medications? (type 'n' to finish):");
            String medication = scanner.nextLine();
            if (medication.equalsIgnoreCase("n")) {
                break;
            }
            if (!checkMedicineExists(medication)) {
                System.out.println("This medication does not exist");
            }
            else {
                System.out.println("What is the quantity of " + medication + "?");
                int quantity = scanner.nextInt();
                scanner.nextLine();
    
                prescribedMedicationsList.add(Prescription.createPrescription(appointment.getAppointmentId(), medication, quantity));
            }
        }

        // Input consultation notes
        System.out.println("Are there any consultation notes?:");
        String consultationNotes = scanner.nextLine();

        // Create new appointment outcome
        db.getAllAppointmentOutcomes().put(appointment.getAppointmentId(), AppointmentOutcome.createOutcome(appointment, servicesProvided, consultationNotes));
        
        // Associate all prescriptions in list to appointment
        for (Prescription prescription : prescribedMedicationsList) {
            db.getAllPrescriptions().computeIfAbsent(appointment.getAppointmentId(), k -> new ArrayList<>()).add(prescription);
        }

        // Set appointment to completed
        appointment.completeSlot();

        System.out.println("Appointment outcome created");
    }
    
    public void cancelAppointment(Doctor d) {
        // Choose date
        String date;
        LocalDate parsedDate;
        while (true) {
            try {
                System.out.println("Which date is the appointment on? (dd/MM/yy):");
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
                System.out.println("Which timeslot is the appointment on? (HH:mm):");
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
        Appointment appointment = db.getAllAppointments()
            .stream()
            .filter(a -> 
                a.getAppointmentStatus().equals(Appointment.AppointmentStatus.OPEN) 
                && a.getDoctorId().equals(d.getId()) 
                && a.getAppointmentDate().equals(finalParsedDate) 
                && a.getAppointmentTime().equals(finalParsedTime))
            .findFirst()
            .orElse(null);
        
        if (appointment == null) {
            System.out.println("No appointment slot found to cancel");
            return;
        }
        else {
            // Schedule slot
            appointment.cancelSlot();
            System.out.println("Appointment slot successfully cancelled");
        }
    }
}
