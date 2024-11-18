/**
 * This interface provides a standardized way to view medical record information based on user permissions and access rights.
 */
public interface IViewMedicalRecord {
    /**
     * Handles the presentation of medical record data, ensuring appropriate access control and data privacy.
     * 
     * @param user The User object whose medical record needs to be viewed.
     *             This determines both the record to be accessed and potentially
     *             the level of detail that can be viewed based on permissions.
     */
    void viewMedicalRecord(User user);
}
