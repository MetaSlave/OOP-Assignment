import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
* Singleton class that serves as the central data repository for the Hospital Management System
* Manages all system data including users, appointments, medical records, prescriptions and medicine inventory
*/
public class HMSDatabase {
    // Singleton instance
    private static HMSDatabase instance;

    /** List of all users in the system */
    private ArrayList<User> allUsers = new ArrayList<>();

    /** List of all appointments in the system */
    private ArrayList<Appointment> allAppointments = new ArrayList<>();

    /** List of all replenishment requests in the system */
    private ArrayList<ReplenishmentRequest> allReplenishmentRequests = new ArrayList<>();

    /** Map of appointment outcomes with appointment ID as key */
    private Map<String, AppointmentOutcome> allAppointmentOutcomes = new HashMap<>();

    /** Map of prescriptions with appointment ID as key */
    private Map<String, ArrayList<Prescription>> allPrescriptions = new HashMap<>();

    /** Map of medical records with patient ID as key */
    private Map<String, ArrayList<MedicalRecord>> allMedicalRecords = new HashMap<>();

    /** Map of medicines with medicine name as key */
    private Map<String, Medicine> allMedicines = new HashMap<>();

    /** Private constructor to initialize data structures */
    private HMSDatabase() {
        allUsers = new ArrayList<>();
        allAppointments = new ArrayList<>();
        allReplenishmentRequests = new ArrayList<>();
        allAppointmentOutcomes = new HashMap<>();
        allPrescriptions = new HashMap<>();
        allMedicalRecords = new HashMap<>();
        allMedicines = new HashMap<>();
    }

   /**
    * Gets the singleton instance of HMSDatabase.
    * Creates a new instance if none exists.
    *
    * @return The singleton HMSDatabase instance
    */
    public static HMSDatabase getInstance() {
        if (instance == null) {
            instance = new HMSDatabase();
        }
        return instance;
    }

    /**
    * Gets the list of all users in the system.
    * @return ArrayList<User> List containing all system users
    */
    public ArrayList<User> getAllUsers() { return allUsers; }
    /**
    * Gets the list of all appointments.
    * @return ArrayList<Appointment> List containing all appointments
    */
    public ArrayList<Appointment> getAllAppointments() { return allAppointments; }
    /**
    * Gets the list of all replenishment requests.
    * @return ArrayList<ReplenishmentRequest> List containing all replenishment requests
    */
    public ArrayList<ReplenishmentRequest> getAllReplenishmentRequests() { return allReplenishmentRequests; }
    /**
    * Gets the map of appointment outcomes indexed by appointment ID.
    * @return Map<String, AppointmentOutcome> Map of appointment outcomes
    */
    public Map<String, AppointmentOutcome> getAllAppointmentOutcomes() { return allAppointmentOutcomes; }
    /**
    * Gets the map of prescriptions indexed by appointment ID.
    * @return Map<String, ArrayList<Prescription>> Map of prescriptions
    */
    public Map<String, ArrayList<Prescription>> getAllPrescriptions() { return allPrescriptions; }
    /**
    * Gets the map of medical records indexed by patient ID.
    * @return Map<String, ArrayList<MedicalRecord>> Map of medical records
    */
    public Map<String, ArrayList<MedicalRecord>> getAllMedicalRecords() { return allMedicalRecords; }
    /**
    * Gets the map of medicines indexed by medicine name.
    * @return Map<String, Medicine> Map of medicines
    */
    public Map<String, Medicine> getAllMedicines() { return allMedicines; }

   /**
    * Initializes the database with data from CSV files
    * After loading, saves the initialized data through serialization
    *
    * @throws FileNotFoundException If any required CSV file is missing
    * @throws IllegalArgumentException If an unknown staff role is encountered
    */
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

   /**
    * Saves all system data through serialization
    * @return boolean True if save successful, false if any errors occur
    */
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

   /**
    * Loads all system data from serialized files
    * @return boolean True if load successful, false if any errors occur
    */
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
