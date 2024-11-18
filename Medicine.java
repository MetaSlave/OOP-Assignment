import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Tracks and prints medicine details (name, quantity, stock level alert) to the console
 * Implements Serializable for persistent storage.
 */
public class Medicine implements Serializable {
    /**
     * Name of medicine
     */
    private String medicineName;
    /**
     * Price of medicine
     */
    private double medicineCost;
    /**
     * Current stock quantity of medicine
     */
    private int stock;
    /**
     * Low stock level alert threshold of medicine
     */
    private int alertBelow;

   /**
    * Constructs a new Medicine with the specified details.
    *
    * @param medicineName The name of the medicine
    * @param medicineCost The cost per unit of the medicine
    * @param stock The initial stock quantity
    * @param alertBelow The threshold below which stock alerts are triggered
    */
    public Medicine(String medicineName, double medicineCost, int stock, int alertBelow) {
        this.medicineName = medicineName;
        this.medicineCost = medicineCost;
        this.stock = stock;
        this.alertBelow = alertBelow;
    }

    /**
     * Deserializes medicine data from a file.
     * 
     * @return Map of medicine names to Medicine objects, or null if deserialization fails
     */
    protected static Map<String, Medicine> deserialize() {
        Map<String, Medicine> allMedicines = null;
        try (FileInputStream fis = new FileInputStream("medicines.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            allMedicines = (Map<String, Medicine>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allMedicines;
    }

    /**
     * Using a new HashMap to serialize medical records to a file
     * 
     * @param allMedicines Map of medicines to serialize
     * @return true if serialization is successful, false otherwise
     */
    protected static boolean serialize(Map<String, Medicine> allMedicines) {
        try (FileOutputStream fos = new FileOutputStream("medicines.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new HashMap<>(allMedicines));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
   /**
    * Gets the cost per unit of the medicine.
    *
    * @return double The cost of one unit of this medicine
    */
    public double getMedicineCost() {
        return this.medicineCost;
    }

    /**
     * Increases the stock quantity by the specified amount.
     *
     * @param replenishAmount The amount to add to current stock
     */
    public void replenishStock(int replenishAmount) {
        this.stock += replenishAmount;
    }
   /**
    * Decreases the stock quantity by the specified amount.
    *
    * @param decreaseAmount The amount to subtract from current stock
    */
    public void decreaseStock(int decreaseAmount) {
        this.stock -= decreaseAmount;
    }

    /**
     * Updates the stock alert threshold.
     *
     * @param alertBelow The new threshold for low stock alerts
     */
    public void setAlertLevel(int alertBelow) {
        this.alertBelow = alertBelow;
    }
   /**
    * Checks if current stock is at or below alert threshold.
    *
    * @return boolean Returns true if stock is at or below alert level,
    *                 false if stock is above alert level
    */
    public boolean checkStockLevel() {
        if (this.stock <= this.alertBelow) {
            return true;
        }
        return false;
    }

    /**
     * Prints medicine details (name, quantity, stock level alert) to the console
     */
    public void print() {
        System.out.println("Medicine Name: " + this.medicineName);
        System.out.println("Stock: " + this.stock);
        if (this.stock < this.alertBelow) {
            System.out.println("Stock Level: Low");
        }
        else {
            System.out.println("Stock Level: Healthy");
        }
    }
}
