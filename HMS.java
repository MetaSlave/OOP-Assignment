

public class HMS {
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
