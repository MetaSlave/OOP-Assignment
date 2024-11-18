/**
* Provides functionality for viewing the hospital's medicine inventory.
* This interface includes a default implementation that displays comprehensive
* information about all medicines currently in stock.
*/
public interface IViewMedicineInventory {
   /**
    * Displays a formatted list of all medicines in the hospital inventory.
    * This method:
    * - Accesses the central medicine database
    * - Prints a formatted header for the inventory listing
    * - Lists all medicines with their complete details using each
    *   medicine's print method
    * 
    * The display includes all medicines regardless of stock level or status,
    * providing a complete view of the hospital's medication inventory.
    */
    default void viewMedicineInventory() {
        // Use database singleton
        HMSDatabase db = HMSDatabase.getInstance();
        
        System.out.println("\n----MEDICATION INVENTORY----");
        for (Medicine medicine : db.getAllMedicines().values()) {
            medicine.print();
        }
    }
}
