/**
* Defines functionality for launching authenticated user menus.
* This interface standardizes how the system presents role-specific menus
* and options to users after they have successfully logged in.
*/
public interface ILaunchAuthMenu {
    /**
    * Launches the appropriate authenticated menu for a given user.
    * This method displays role-specific options and functionality based on
    * the user's permissions and role in the system.
    *
    * @param u The authenticated User object for whom the menu should be displayed.
    *          The user's role and permissions determine which menu options are available.
    */
    void launchAuthMenu(User u);
}
