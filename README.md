# Zomato: Food Delivery Mobile Application

## Project Overview
This project is a food delivery mobile application inspired by Zomato. It allows users to browse, search, and order from various restaurants and brands. The application also provides sorting and filtering options to streamline the search process for users. Additional features include promotional banners, a list of top brands, and options for users to manage their profiles and preferences. Much more will be added next, Insha'Allah.

## Setup Instructions
To run this project, follow these setup instructions:

1. **Install Android Studio**: Make sure you have the latest version of Android Studio installed on your machine.
2. **Use a Physical Android Device**: Testing should be performed on a physical Android device for optimal performance.
3. **Internet Connection**: An active internet connection is required to download SDKs and dependencies for Gradle and the project.
4. **GitHub Repository**: The code is pushed to a remote GitHub repository named `Zomato` after testing to maintain version control.

## Screens Designed and Their Purpose

- **Splash Screen**:
    - The initial screen shown when the app launches, designed to provide a brief welcome while the app loads.

- **Login/Sign Up Screen**:
    - Allows users to log in or sign up using their Google account or phone number.

- **Home Screen**:
    - The main screen of the app where users can browse food items, search for restaurants, and see promotional banners.
    - Features include:
        - A search bar for filtering restaurants and dishes.
        - Sorting and filtering options (by rating, delivery type, etc.).
        - Promotional banners for sales and top deals.
        - Horizontal scrolling lists of top brands (e.g., Burger King, KFC, Subway).
        - Implemented ScrollView for enhanced navigation.

- **Profile Screen**:
    - Displays user details and allows for profile management.

- **Settings Screen**:
    - Enables users to configure app settings.

- **Navigation Drawer**:
    - Provides easy access to different sections of the app.

- **Bottom Navigation**:
    - Facilitates quick navigation between the main areas of the application.

- **Payment Screen**:
    - Enables users to manage their payment methods.

- **Edit Profile Screen**:
    - Allows users to update their profile information.

- **Set Location Screen**:
    - Lets users set or update their delivery location.
    - 
- **Delivery and Dining Fragment**:
    - Separate pages for the user to Make a delivery and To dine In.

## Responsiveness
All screens have been designed to be responsive across different device sizes and orientations to ensure a seamless user experience.

## Technical Challenges Faced
I encountered several challenges during development:
- Initially, I was unfamiliar with Android Studio, which led me to spend 4-5 hours learning how to create XML layouts.
- After discovering that Constraint Layout is the best option for responsive design, I faced complexities when implementing features like ScrollView and Spinners.
- Transitioning to more complex elements took time, but after dedicating four days to the learning process, I successfully created activities and modified the `AndroidManifest.xml` to set the splash screen as the launcher.

## Future Plans
- **Backend Integration**: Currently, the project is front-end only. Future steps include integrating the application with a backend for user authentication and restaurant details.

- **User Roles**: Implementing different user roles, such as "admin" for managing restaurants and "user" for ordering food, with tailored access and functionality.

- **Payment Integration**: Adding support for multiple payment methods, including credit/debit cards, mobile wallets, and cash on delivery.

- **Improved UI/UX**: Continuously refining the user interface and user experience based on feedback and testing, including animations, better transitions, and quicker load times.

## Current Status
As of now, I have completed the following:
- Developed the UI for the delivery and dining features.
- Implemented login and sign-up functionalities.
- Created location settings, user settings, account deactivation, and cart functionalities.
- Built out both activities and fragments while ensuring responsiveness across all screens.

### Conclusion
This README serves as a comprehensive overview of the Zomato-inspired food delivery mobile application, detailing its current state, setup instructions, and future plans. Stay tuned for more updates as I progress with backend integration and additional features!

