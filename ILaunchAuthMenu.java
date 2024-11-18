/**
* This interface standardizes how the system presents role-specific menus and options to users after they have logged in
*/
public interface ILaunchAuthMenu {
    /**
    *  Launches an authenticated menu instance
    * @param u The authenticated User object for whom the menu should be displayed.
    */
    void launchAuthMenu(User u);
}
