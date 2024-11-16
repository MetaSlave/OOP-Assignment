import java.util.Scanner;

public class HMSInput {
    // Singleton instance
    private static HMSInput instance;
    private final Scanner scanner;
    
    // Private constructor to prevent instantiation
    private HMSInput() {
        scanner = new Scanner(System.in);
    }
    
    // Public method to get the single instance
    public static HMSInput getInstance() {
        if (instance == null) {
            instance = new HMSInput();
        }
        return instance;
    }
    
    // Method to get the scanner
    public Scanner getScanner() {
        return scanner;
    }
    
    public void closeScanner() {
        scanner.close();
    }
}