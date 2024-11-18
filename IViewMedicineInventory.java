/**
* This interface gives a default implementation to display comprehensive information about the medicine inventory
*/
public interface IViewMedicineInventory {
   /**
    * Includes all medicines in the inventory regardless of stock level or status
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
