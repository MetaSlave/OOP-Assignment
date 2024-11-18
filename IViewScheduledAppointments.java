/**
* This interface standardizes how users can view their confirmed appointments
*/
public interface IViewScheduledAppointments {
    /**
    * Displays scheduled appointments for a specific user
    *
    * @param user The User object whose scheduled appointments should be displayed.
    *             The user's role determines which appointments are shown and
    *             how they are displayed.
    */
    void viewScheduledAppointments(User user);
}
