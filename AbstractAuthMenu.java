/**
* Base implementation for authenticated user menus in the system
* The class is abstract to enforce that specific menu implementations(like doctor, patient etc.) must provide their own concrete implementations of the required menu functionality
*/

abstract class AbstractAuthMenu implements IDisplayOptions, ILaunchAuthMenu{
}
