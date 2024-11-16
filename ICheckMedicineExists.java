public interface ICheckMedicineExists {
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
