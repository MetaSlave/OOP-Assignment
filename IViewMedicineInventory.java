public interface IViewMedicineInventory {
    default void viewMedicineInventory() {
        // Use database singleton
        HMSDatabase db = HMSDatabase.getInstance();
        
        System.out.println("\n----MEDICATION INVENTORY----");
        for (Medicine medicine : db.getAllMedicines().values()) {
            medicine.print();
        }
    }
}
