/**
* Provides a base implementation for authenticated user menus in the system.
* This abstract class combines display functionality with authenticated menu launching,
* serving as a template for role-specific menu implementations.
*
* The class is abstract to enforce that specific menu implementations
* (like doctor, patient etc.) must provide their own
* concrete implementations of the required menu functionality.
*/

abstract class AbstractAuthMenu implements IDisplayOptions, ILaunchAuthMenu{
}
