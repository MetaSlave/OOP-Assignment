public interface IViewMedicineInventory {
    default void viewMedicineInventory() {
        System.out.println("\n----MEDICATION INVENTORY----");
        for (Medicine medicine : HMS.allMedicines.values()) {
            medicine.print();
        }
    }
}
