# Zomato: Food Delivery Mobile Application
# All Rights Reserved.
## You may not copy, reproduce, modify, or redistribute this code without explicit permission.

## Project Overview
Zomato is a food delivery mobile application inspired by the popular Zomato platform. This app allows users to browse, search, and order food from various restaurants. The app offers various features such as filtering, sorting, and promotional banners to enhance user experience. The backend is powered by **Firebase** for user authentication, real-time data syncing, and order management.

## Features
- **User Authentication**: Google authentication and phone number login.
- **Search & Browse Restaurants**: Find food from nearby restaurants and order your favorites.
- **Cart Management**: Users can add, remove, and view items in their cart.
- **Order Tracking**: Track your order status in real-time.
- **Location Management**: Set or update the delivery location.
- **Responsive UI**: The app works seamlessly across different screen sizes and orientations.
- **Backend Integration**: Firebase is used for authentication, real-time database, and user management.

## Setup Instructions

### Frontend (Mobile App)
1. **Install Android Studio**: Ensure you have Android Studio installed on your machine.
2. **Use a Physical Android Device**: It’s recommended to test the app on a physical Android device.
3. **Firebase Setup**:
    - Configure Firebase for your project by linking the Firebase console with your Android app.
    - Add the `google-services.json` to your app's root directory.

### Backend (Firebase)
1. **Firebase Authentication**: Set up Firebase authentication for Google and phone number logins.
2. **Firebase Firestore**: Use Firebase Firestore to store restaurant data, user profiles, and order information.
3. **Firebase Real-time Database**: Real-time syncing of cart and order status.

## Screens Designed and Their Purpose

### 1. **Splash Screen**
- **Purpose**: This screen is shown when the app is launched. It briefly introduces the app and transitions to the login/signup screen.

### 2. **Login/Sign Up Screen**
- **Purpose**: Allows users to log in using their Google account or phone number. This screen authenticates users and navigates them to the home screen after a successful login.

### 3. **Home Screen**
- **Purpose**: The main screen where users can browse food items and restaurants.
- **Key Features**:
    - Search bar for filtering restaurants and dishes.
    - Sorting and filtering options (by rating, delivery type, etc.).
    - Horizontal scrolling lists of top brands (e.g., KFC, Burger King, Subway).
    - Display of promotional banners for sales and offers.
    - Integrated with **Firebase Firestore** to fetch restaurant and food data.

### 4. **Cart Screen**
- **Purpose**: Users can view and manage the items they’ve added to their cart.
- **RecyclerView**:
    - Displays a list of cart items with details like food name, quantity, and price.
    - Allows users to update the quantity or remove items from the cart.
- **Firebase Integration**:
    - Cart data is stored in **Firebase Firestore**, and changes are synced in real-time.

### 5. **Profile Screen**
- **Purpose**: Displays the user's details and allows them to update their profile information such as name, email, and phone number.
- **Firebase Integration**:
    - Syncs profile data with **Firebase Authentication** and **Firestore**.

### 6. **Settings Screen**
- **Purpose**: Allows users to configure app settings, such as notification preferences, theme, and language.

### 7. **Navigation Drawer**
- **Purpose**: Provides easy navigation to various sections of the app, such as Profile, Settings, Cart, Orders, and Logout.

### 8. **Bottom Navigation**
- **Purpose**: Quick navigation between the main areas of the app. The bottom navigation includes:
    - Home
    - Cart
    - Profile
    - Settings

### 9. **Payment Screen**
- **Purpose**: Allows users to manage payment methods and complete the payment process for their orders.

### 10. **Edit Profile Screen**
- **Purpose**: Allows users to edit their personal information, such as phone number or address.

### 11. **Set Location Screen**
- **Purpose**: Users can set or update their delivery location to ensure the restaurant can deliver the food.

### 12. **Delivery and Dining Fragments**
- **Purpose**: Separate fragments for:
    - **Delivery**: Users can opt for food delivery.
    - **Dining In**: Users can opt to dine in at the restaurant.
- These fragments interact with **Firebase Firestore** to manage the user’s order status and preferences.

## RecyclerViews Used in the App

- **Cart RecyclerView**:
    - Displays the items in the user's cart.
    - Supports item removal and quantity adjustment.
    - Integrated with **Firebase** to fetch and update cart data in real-time.

- **Restaurant List RecyclerView**:
    - Displays the list of restaurants on the Home screen.
    - Supports searching, sorting, and filtering using **Firebase Firestore** queries.

- **Order List RecyclerView**:
    - Displays a list of the user’s past orders on the Orders screen.
    - Each order is retrieved from **Firebase Firestore** and displayed with its status.

## Firebase Integration

### Firebase Authentication
Firebase Authentication is used to authenticate users via Google or phone number login. The following methods are implemented:
- **Google Sign-In**: Users can sign in using their Google account.
- **Phone Number Authentication**: Users can authenticate using their phone number.

### Firebase Firestore
Firebase Firestore is used to store and manage the following data:
- **Users**: Stores user details such as name, email, phone number, and profile picture.
- **Restaurants**: Stores restaurant information including name, location, menu, and available dishes.
- **Cart**: Stores the user's cart items and their real-time updates.
- **Orders**: Stores orders placed by users, including details like the restaurant, food items, total cost, and order status.

### Firebase Real-Time Database
The Real-Time Database is used to sync the cart and order status in real-time across devices.

## Navigation Structure

### Bottom Navigation
- **Home**: Displays a list of restaurants and dishes, allows searching and filtering.
- **Cart**: Displays cart items and allows users to modify the cart.
- **Profile**: Displays user details and allows editing.
- **Settings**: Allows the user to manage app settings.

### Navigation Drawer
The drawer contains the following items:
- **Profile**: Access the Profile screen to view and edit user information.
- **Cart**: Quick access to the Cart screen.
- **Orders**: View past orders and track current orders.
- **Settings**: Access to app settings and preferences.
- **Logout**: Logs the user out and returns to the Login screen.

## Responsiveness
All screens have been designed to be responsive, ensuring that the app works seamlessly across various screen sizes and orientations. The use of **ConstraintLayout** and **ScrollViews** ensures that the layout adjusts to different devices.

## Technical Challenges Faced
- Initially, I struggled with Firebase setup and real-time data syncing. However, after thorough documentation review, I was able to integrate Firebase Authentication and Firestore seamlessly.
- Managing real-time updates with Firebase's real-time database presented some challenges with syncing cart and order statuses across multiple devices.

## Future Plans
- **Backend Enhancements**: Integrate additional Firebase features like Cloud Functions for more advanced features such as order notifications and push notifications.
- **Payment Integration**: Adding support for multiple payment methods, including credit/debit cards and mobile wallets.
- **User Feedback**: Implement a system for users to leave feedback on restaurants and food items.

## Conclusion
This README provides a detailed overview of the **Zomato-inspired** food delivery mobile application, its design, features, and integration with Firebase. The app is fully functional with a responsive UI, real-time cart updates, and Firebase-powered backend.

The project is now complete, and I will be pursuing further development on a personal basis for more advanced features such as enhanced user roles, AI-based food recommendations, and better payment gateway integration. Thank you for following this journey!

