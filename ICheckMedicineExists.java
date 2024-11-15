public interface ICheckMedicineExists {
    default boolean checkMedicineExists(String medicine) {
        // Check if medicine exists
        boolean medicineExists = HMS.allMedicines.containsKey(medicine);
        if (!medicineExists) {
            System.out.println("Medicine does not exist in the inventory.");
            return false;
        }
        return true;
    }
}
