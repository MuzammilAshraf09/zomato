Project Name: Zomato
Project Overview
This project is a food delivery mobile application inspired by Zomato. It allows users to browse, search, and order from various restaurants and brands. The application provides sorting and filtering options to make the search process easier for users. The app features banners for promotions, a list of top brands, and options for users to manage their profiles and preferences. Much more will be added next, Insha’Allah.

Setup Instructions
Android Studio was installed.
Used a physical Android device to test the app.
An internet connection is always necessary to download the SDKs for the Project Gradle, etc.
I created a remote repository (GitHub) of the same name as Zomato and push the code after testing it there.
Screens Designed and Their Purpose
Splash Screen

The splash screen is the initial screen that shows when the app launches. It’s designed to provide a brief welcome while the app is loading.
Login/SignUp Screen

Allows users to log in or sign up using their Google account or phone number.
Home Screen

The main screen of the app, where users can browse food items, search for restaurants, and see promotional banners.
Features include:
A search bar for filtering restaurants and dishes.
Sorting and filtering options (by rating, delivery type, etc.).
Promotional banners for sales and top deals.
Horizontal scrolling lists of top brands (Burger King, KFC, Subway, etc.).
Added ScrollView.
Profile Screen

Displays user details.
Settings Screen

Allows users to manage app settings.
Navigation Drawer

Provides easy access to different sections of the app.
Bottom Navigation

Quick navigation to major app sections.
Payment Screen

Facilitates the payment process for users.
Edit Profile Screen

Enables users to update their profile information.
Set Location Screen

Allows users to set or change their delivery location.
Technical Challenges Faced
I faced many issues; I started working on this on Thursday but didn't know much about Android Studio. I spent 4-5 hours on YouTube to learn how to create XML layouts in Android Studio, and I discovered that Constraint Layout is the best layout to use. When I moved to the home page, I encountered more complex elements like ScrollView and Spinner, which were quite difficult at first. However, after spending 4 days on it, I became more proficient and created activities and the AndroidManifest to set the splash screen as the launcher.

Future Plans
Backend Integration: Currently, the project is a front-end-only design. The next steps involve integrating it with a backend for user authentication and restaurant details.
User Roles: Implementing different user roles like "admin" for managing restaurants and "user" for ordering food, with tailored access and functionality.
Payment Integration: Adding support for multiple payment methods, including credit/debit cards, mobile wallets, and cash on delivery.
Improved UI/UX: Continuously refining the user interface and user experience based on feedback and testing, including animations, better transitions, and quicker load times.
Responsiveness: Ensured responsiveness across all screens and implemented spinners for sorting and filtering options.