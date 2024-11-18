/**
 * Represents a staff member in the system
 */
public class Staff extends User {
    /**
     * Represents a staff's age
     */
    private int Age;

    /**
     * Constructs a new Staff member with specified attributes.
     *
     * @param id     The unique identifier for the staff member
     * @param role   The role/position of the staff member
     * @param name   The full name of the staff member
     * @param gender The gender of the staff member
     * @param age    The age of the staff member
     */
    Staff(String id, String role, String name, String gender, int age) {
        super(id, role, name, gender);
        this.Age = age;
    }

    /**
     * Creates and returns a new Staff instance with the specified attributes.
     *
     * @param id     The unique identifier for the staff member
     * @param role   The role/position of the staff member
     * @param name   The full name of the staff member
     * @param gender The gender of the staff member
     * @param age    The age of the staff member
     * @return A new Staff instance with the specified attributes
     */
    public static Staff addStaff(String id, String role, String name, String gender, int age) {
        Staff newStaff = new Staff(id, role, name, gender, age);
        return newStaff;
    }

    /**
     * @param updatedName The new name to set for the staff member
     */
    public void setName(String updatedName) {
        super.setName(updatedName);
    }

    /**

     * @param updatedGender The new gender to set for the staff member
     */
    public void setGender(String updatedGender) {
        super.setGender(updatedGender);
    }

    /**
     * @param updatedAge The new age to set for the staff member
     */
    public void setAge(int updatedAge) {
        this.Age = updatedAge;
    }

    /**
     * Prints all details of the staff member to the console.
     */
    public void print() {
        System.out.println("Id: " + this.getId());
        System.out.println("Role: " + this.getRole());
        System.out.println("Name: " + this.getName());
        System.out.println("Gender: " + this.getGender());
        System.out.println("Age: " + this.Age);
    }
}