/**
* Main class for the Hospital Management System (HMS).
* This class serves as the entry point for the application, handling
* initialization, data loading, user interface launching, and data persistence.
*/
public class HMS {
   /**
    * Main method that initializes and runs the Hospital Management System.
    * This method:
    * 1. Handles command-line initialization if requested
    * 2. Loads existing system data
    * 3. Launches the main user interface
    * 4. Saves system data on exit
    * 
    * The system can be initialized with fresh data by passing "initialize"
    * as a command-line argument. Otherwise, it attempts to load existing data.
    *
    * @param args Command-line arguments. 
    *             If args[0] is "initialize", the system will be initialized with fresh data.
    *             Otherwise, existing data will be loaded from storage.
    *             
    * Flow:
    * - Obtains singleton instance of HMSDatabase
    * - Checks for initialization flag
    * - Loads existing data if not initializing
    * - Launches user interface
    * - Saves data before exit
    */
    public static void main(String[] args) {
        // Get instance of HMSDatabase
        HMSDatabase db = HMSDatabase.getInstance();

        // Initialization
        if (args.length > 0 && args[0].equals("initialize")) {
            db.initialize();
            return;
        }
        else {
            if (db.load() == true) {
                System.out.println("Loaded HMS");
            }
            else {
                System.out.println("Error loading HMS, terminating");
                return;
            }
        }

        // Create instance of HMSUI
        HMSUI newHMSUI = new HMSUI();

        // Launch HMS UI
        newHMSUI.launchMenu();

        System.out.println("Saving HMS");
        db.save();
    }
}
