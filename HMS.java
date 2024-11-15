import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.time.LocalDate;

public class HMS {
    // List to store all users
    public static ArrayList<User> allUsers = new ArrayList<>();
    // List to store all appointments
    public static ArrayList<Appointment> allAppointments = new ArrayList<>();
    // List to store all replenishment requests
    public static ArrayList<ReplenishmentRequest> allReplenishmentRequests = new ArrayList<>();

    // Hashmap to store appointment outcomes
    public static Map<String, AppointmentOutcome> allAppointmentOutcomes = new HashMap<>();    // AppointmentId as Key
    // Hashmap to store prescriptions
    public static Map<String, ArrayList<Prescription>> allPrescriptions = new HashMap<>();     // AppointmentId as Key
    // Hashmap to store medical records
    public static Map<String, ArrayList<MedicalRecord>> allMedicalRecords = new HashMap<>();   // PatientId as Key

    // Hashmap to store medicine inventory
    public static Map<String, Medicine> allMedicines = new HashMap<>();                        // Medicine name as Key

    // Run initialization
    public static void initialize() {
        // Initialize Patient_List.csv
        ArrayList<Patient> allPatients = new ArrayList<Patient>();
        try (Scanner scanner = new Scanner(new File("Patient_List.csv"))) {
            // Skip Title
            scanner.nextLine();
            // Set Delimiters
            scanner.useDelimiter(",|\\n");
            while (scanner.hasNext()) 
            {  
                String p_Id = scanner.next();
                String p_Name = scanner.next();
                String p_DOB = scanner.next();
                LocalDate p_parsedDOB = LocalDate.parse(p_DOB);
                String p_Gender = scanner.next();
                String p_BloodType = scanner.next();
                String p_ContactInformation = scanner.next();

                Patient p = new Patient(p_Id, "Patient", p_Name, p_Gender, p_BloodType, p_parsedDOB, p_ContactInformation);
                allPatients.add(p);
            }   
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Put Patient_List into Users
        for (Patient p : allPatients) {
            allUsers.add((User) p);
        }

        // Initialize Staff_List.csv
        ArrayList<Staff> allStaff = new ArrayList<Staff>();

        try (Scanner scanner = new Scanner(new File("Staff_List.csv"))) {
            // Skip Title
            scanner.nextLine();
            // Set Delimiters
            scanner.useDelimiter(",|\\n");
            while (scanner.hasNext())
            {  
                String s_Id = scanner.next();
                String s_Name = scanner.next();
                String s_Role = scanner.next();
                String s_Gender = scanner.next();
                int s_Age = Integer.parseInt(scanner.next().trim());

                Staff s;
                if (s_Role.equals("Doctor")) {
                    s = new Doctor(s_Id, s_Role, s_Name, s_Gender, s_Age);
                }
                else if (s_Role.equals("Pharmacist")){
                    s = new Pharmacist(s_Id, s_Role, s_Name, s_Gender, s_Age);
                }
                else if (s_Role.equals("Administrator")){
                    s = new Administrator(s_Id, s_Role, s_Name, s_Gender, s_Age);
                }
                else {
                    throw new IllegalArgumentException("Unknown staff role: " + s_Role);
                }
                allStaff.add(s);
            }   
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Put Staff list into Users
        for (Staff s : allStaff) {
            
            allUsers.add((User) s);
        }

        // Initialize Medicine_List.csv
        try (Scanner scanner = new Scanner(new File("Medicine_List.csv"))) {
            // Skip Title
            scanner.nextLine();
            // Set Delimiters
            scanner.useDelimiter(",|\\n");
            while (scanner.hasNext()) 
            {  
                String m_Name = scanner.next();
                double m_Cost = Double.parseDouble(scanner.next().trim());
                int m_InitialStock = Integer.parseInt(scanner.next().trim());
                int m_LowStockAlert = Integer.parseInt(scanner.next().trim());

                Medicine m = new Medicine(m_Name, m_Cost, m_InitialStock, m_LowStockAlert);
                allMedicines.put(m_Name, m);
            }   
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        save();
    }

    // Call all serialize methods in classes
    public static boolean save() {
        try {
            // Populate Staff and Patients
            ArrayList<Patient> allPatients = new ArrayList<>();
            ArrayList<Doctor> allDoctor = new ArrayList<>();
            ArrayList<Pharmacist> allPharmacist = new ArrayList<>();
            ArrayList<Administrator> allAdministrator = new ArrayList<>();
            for (User u : allUsers) {
                if (u.getRole().equals("Patient")) {
                    allPatients.add((Patient) u);
                }
                else if (u.getRole().equals("Doctor")){
                    allDoctor.add((Doctor) u);
                }
                else if (u.getRole().equals("Pharmacist")){
                    allPharmacist.add((Pharmacist) u);
                }
                else if (u.getRole().equals("Administrator")){
                    allAdministrator.add((Administrator) u);
                }
            }

            // Serialize
            Patient.serialize(allPatients);
            Doctor.serialize(allDoctor);
            Pharmacist.serialize(allPharmacist);
            Administrator.serialize(allAdministrator);
            Appointment.serialize(allAppointments);
            AppointmentOutcome.serialize(allAppointmentOutcomes); 
            Prescription.serialize(allPrescriptions);
            MedicalRecord.serialize(allMedicalRecords);
            Medicine.serialize(allMedicines);
            ReplenishmentRequest.serialize(allReplenishmentRequests);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Call all deserialize methods in calsses and populate above lists and hashmaps
    public static boolean load() {
        try {
            // Deserialize everyone
            ArrayList<Patient> allPatients = Patient.deserialize();
            ArrayList<Doctor> allDoctors = Doctor.deserialize();
            ArrayList<Pharmacist> allPharmacists = Pharmacist.deserialize();
            ArrayList<Administrator> allAdministrators = Administrator.deserialize();
            
            // Downcast
            for (Patient p : allPatients) {
                allUsers.add((User) p);
            }
            for (Doctor d : allDoctors) {
                allUsers.add((User) d);
            }
            for (Pharmacist ph : allPharmacists) {
                allUsers.add((User)(Staff) ph);
            }
            for (Administrator a : allAdministrators) {
                allUsers.add((User)(Staff) a);
            }

            allAppointments = Appointment.deserialize();
            allAppointmentOutcomes = AppointmentOutcome.deserialize();
            allPrescriptions = Prescription.deserialize();
            allMedicalRecords = MedicalRecord.deserialize();
            allMedicines = Medicine.deserialize();
            allReplenishmentRequests = ReplenishmentRequest.deserialize();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        // Initialization
        if (args.length > 0 && args[0].equals("initialize")) {
            initialize();
            return;
        }
        else {
            if (load() == true) {
                System.out.println("Loaded HMS");
            }
            else {
                System.out.println("Error loading HMS, terminating");
                return;
            }
        }

        // Create instance of HMSMenu
        HMSMenu newHMSMenu = new HMSMenu();
        Scanner scanner = new Scanner(System.in);

        // Main program loop
        boolean programExited = false;
        while (!programExited) {
            newHMSMenu.display();
            int choice;
            User currentUser;
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    // Login
                    currentUser = newHMSMenu.login(allUsers, scanner);
                    boolean loggedIn = true;
                    // After login
                    if (currentUser.getRole().equals("Patient")) {
                        // Upcast user to patient
                        Patient p = (Patient) currentUser;

                        // Create instance of PatientMenu
                        PatientMenu newPatientMenu = new PatientMenu();

                        // Patient Menu Loop
                        while (loggedIn) {
                            newPatientMenu.display();
                            int patientChoice;
                            
                            while (true) {
                                try {
                                    System.out.print("Enter your choice: ");
                                    String input = scanner.nextLine();
                                    patientChoice = Integer.parseInt(input);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid choice");
                                }
                            }
                            
                            switch (patientChoice) {
                                case 1:
                                    // View Medical Record
                                    newPatientMenu.viewMedicalRecord(p);
                                    break;
                                case 2:
                                    // Update Personal Information
                                    newPatientMenu.updateContactDetails(p,scanner);
                                    break;
                                case 3:
                                    // View Available Appointment Slots
                                    newPatientMenu.viewAppointmentSlots();
                                    break;
                                case 4:
                                    // Schedule an Appointment
                                    newPatientMenu.scheduleAppointment(p,scanner);
                                    break;
                                case 5:
                                    // Reschedule an Appointment
                                    if (newPatientMenu.cancelAppointment(p,scanner)) {
                                        newPatientMenu.scheduleAppointment(p,scanner);
                                    }
                                    break;
                                case 6:
                                    // Cancel an Appointment
                                    newPatientMenu.cancelAppointment(p,scanner);
                                    break;
                                case 7:
                                    // View Scheduled Appointments
                                    newPatientMenu.viewScheduledAppointments(p);
                                    break;
                                case 8:
                                    // View Past Appointment Outcome Records
                                    newPatientMenu.viewAppointmentOutcomeRecords(p);
                                    break;
                                case 9:
                                    loggedIn = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
                    }
                    else if (currentUser.getRole().equals("Doctor")) {
                        // Upcast user to doctor
                        Doctor d = (Doctor) currentUser;

                        // Create instance of DoctorMenu
                        DoctorMenu newDoctorMenu = new DoctorMenu();

                        // Doctor Menu Loop
                        while (loggedIn) {
                            newDoctorMenu.display();
                            int doctorChoice;
                            while (true) {
                                try {
                                    System.out.print("Enter your choice: ");
                                    String input = scanner.nextLine();
                                    doctorChoice = Integer.parseInt(input);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid choice");
                                }
                            }
                            switch (doctorChoice) {
                                case 1:
                                    // View Patient Medical Records
                                    newDoctorMenu.viewMedicalRecord(d);
                                    break;
                                case 2:
                                    // Update Patient Medical Records
                                    newDoctorMenu.updateMedicalRecord(d, scanner);
                                    break;
                                case 3:
                                    // View Personal Schedule
                                    newDoctorMenu.viewPersonalSchedule(d);
                                    break;
                                case 4:
                                    // Set Availability for Appointments
                                    newDoctorMenu.createNewAppointment(d, scanner);
                                    break;
                                case 5:
                                    // Accept or Decline Appointment Requests
                                    newDoctorMenu.acceptOrDeclineAppointmentRequests(d, scanner);
                                    break;
                                case 6:
                                    // View Upcoming Appointments
                                    newDoctorMenu.viewScheduledAppointments(d);
                                    break;
                                case 7:
                                    // Record Appointment Outcome
                                    newDoctorMenu.recordAppointmentOutcome(d, scanner);
                                    break;
                                case 8:
                                    loggedIn = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
                    }
                    else if (currentUser.getRole().equals("Pharmacist")) {
                        // Upcast user to pharmacist
                        Pharmacist ph = (Pharmacist) currentUser;

                        // Create instance of Pharmacist Menu
                        PharmacistMenu newPharmacistMenu = new PharmacistMenu();

                        // Pharmacist Menu Loop
                        while (loggedIn) {
                            newPharmacistMenu.display();
                            int pharmacistChoice;
                            while (true) {
                                try {
                                    System.out.print("Enter your choice: ");
                                    String input = scanner.nextLine();
                                    pharmacistChoice = Integer.parseInt(input);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid choice");
                                }
                            }
                            switch (pharmacistChoice) {
                                case 1:
                                    // View Appointment Outcome Records
                                    newPharmacistMenu.viewAppointmentOutcomeRecords();
                                    break;
                                case 2:
                                    // Update Prescription Status
                                    newPharmacistMenu.updatePrescriptionStatus(scanner);
                                    break;
                                case 3:
                                    // View Medication Inventory
                                    newPharmacistMenu.viewMedicineInventory();
                                    break;
                                case 4:
                                    // Submit Replenishment Request
                                    newPharmacistMenu.createReplenishmentRequest(ph, scanner);
                                    break;
                                case 5:
                                    loggedIn = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
                    }
                    else if (currentUser.getRole().equals("Administrator")){
                        // Create instance of Administrator Menu
                        AdministratorMenu newAdminMenu = new AdministratorMenu();
                        // Administrator Menu Loop
                        while (loggedIn) {
                            newAdminMenu.display();
                            int adminChoice;
                            while (true) {
                                try {
                                    System.out.print("Enter your choice: ");
                                    String input = scanner.nextLine();
                                    adminChoice = Integer.parseInt(input);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid choice");
                                }
                            }
                            switch (adminChoice) {
                                case 1:
                                    // View Manage Hospital Staff Menu
                                    newAdminMenu.viewAndManageHospitalStaff(scanner);
                                    break;
                                case 2:
                                    // View Appointments Details
                                    newAdminMenu.viewAppointmentDetails();
                                    break;
                                case 3:
                                    // View and Manage Medication Inventory
                                    newAdminMenu.viewAndManageMedicationInventory(scanner);
                                    break;
                                case 4:
                                    // Approve Replenishment Requests
                                    newAdminMenu.approveReplenishmentRequests(scanner);
                                    break;
                                case 5:
                                    loggedIn = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
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
        System.out.println("Saving HMS");
        save();
    }
}
