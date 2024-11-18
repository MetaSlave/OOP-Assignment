import java.util.Scanner;
/**
* Manages the Hospital Management System scanner input
*/
public class HMSInput {
    // Singleton instance
    private static HMSInput instance;
    private final Scanner scanner;
    
    // Private constructor to prevent instantiation
    private HMSInput() {
        scanner = new Scanner(System.in);
    }
    
   /**
    * Gets the singleton instance of HMSInput
    * Creates a new instance with an initialized Scanner if none exists
    *
    * @return The singleton HMSInput instance
    */
    public static HMSInput getInstance() {
        if (instance == null) {
            instance = new HMSInput();
        }
        return instance;
    }
    /**
    * Gets the Scanner instance for reading console input
    * The same Scanner instance is used throughout the application to prevent multiple System.in readers
    * @return Scanner The singleton Scanner instance
    */
    public Scanner getScanner() {
        return scanner;
    }
    /**
    * Closes the Scanner instance and releases system resources
    * Should be called when the application is shutting down to properly clean up the System.in resource
    */
    public void closeScanner() {
        scanner.close();
    }
}