/**
* Provides functionality for viewing scheduled appointments in the system.
* This interface standardizes how different types of users can view their
* confirmed appointments, adapting the display based on user role
* (e.g., doctors see their patient appointments, patients see their doctor appointments).
*/
public interface IViewScheduledAppointments {
    /**
    * Displays scheduled (confirmed) appointments for a specific user.
    * The display is tailored based on the user's role:
    * - For doctors: Shows all confirmed patient appointments
    * - For patients: Shows all confirmed doctor appointments
    *
    * @param user The User object whose scheduled appointments should be displayed.
    *             The user's role determines which appointments are shown and
    *             how they are displayed.
    */
    void viewScheduledAppointments(User user);
}
