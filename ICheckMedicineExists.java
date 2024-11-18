/**
* This interface is used for checking if a specific medicine exists in the system's database.
*/
public interface ICheckMedicineExists {
    /**
    * Verifies if a specified medicine exists in the hospital's inventory.
    *
    * @param medicine The name of the medicine to check
    * @return boolean Returns true if the medicine exists in inventory,
    *                 false if it does not exist
    */
    default boolean checkMedicineExists(String medicine) {
        // Use database singleton
        HMSDatabase db = HMSDatabase.getInstance();
        
        // Check if medicine exists
        boolean medicineExists = db.getAllMedicines().containsKey(medicine);
        if (!medicineExists) {
            System.out.println("Medicine does not exist in the inventory.");
            return false;
        }
        return true;
    }
}
