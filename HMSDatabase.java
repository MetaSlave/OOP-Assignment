import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HMSDatabase {
    // Singleton instance
    private static HMSDatabase instance;

    // List to store all users
    public ArrayList<User> allUsers = new ArrayList<>();
    // List to store all appointments
    public ArrayList<Appointment> allAppointments = new ArrayList<>();
    // List to store all replenishment requests
    public ArrayList<ReplenishmentRequest> allReplenishmentRequests = new ArrayList<>();

    // Hashmap to store appointment outcomes
    public Map<String, AppointmentOutcome> allAppointmentOutcomes = new HashMap<>();    // AppointmentId as Key
    // Hashmap to store prescriptions
    public Map<String, ArrayList<Prescription>> allPrescriptions = new HashMap<>();     // AppointmentId as Key
    // Hashmap to store medical records
    public Map<String, ArrayList<MedicalRecord>> allMedicalRecords = new HashMap<>();   // PatientId as Key

    // Hashmap to store medicine inventory
    public Map<String, Medicine> allMedicines = new HashMap<>();                        // Medicine name as Key

    // Private constructor
    private HMSDatabase() {
        allUsers = new ArrayList<>();
        allAppointments = new ArrayList<>();
        allReplenishmentRequests = new ArrayList<>();
        allAppointmentOutcomes = new HashMap<>();
        allPrescriptions = new HashMap<>();
        allMedicalRecords = new HashMap<>();
        allMedicines = new HashMap<>();
    }

    // Public method to get instance
    public static HMSDatabase getInstance() {
        if (instance == null) {
            instance = new HMSDatabase();
        }
        return instance;
    }

    // Getters for the collections
    public ArrayList<User> getAllUsers() { return allUsers; }
    public ArrayList<Appointment> getAllAppointments() { return allAppointments; }
    public ArrayList<ReplenishmentRequest> getAllReplenishmentRequests() { return allReplenishmentRequests; }
    public Map<String, AppointmentOutcome> getAllAppointmentOutcomes() { return allAppointmentOutcomes; }
    public Map<String, ArrayList<Prescription>> getAllPrescriptions() { return allPrescriptions; }
    public Map<String, ArrayList<MedicalRecord>> getAllMedicalRecords() { return allMedicalRecords; }
    public Map<String, Medicine> getAllMedicines() { return allMedicines; }

    // Run initialization
    public void initialize() {
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
    public boolean save() {
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
    public boolean load() {
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
    
}
