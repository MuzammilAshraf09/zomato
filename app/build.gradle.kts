plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.zomato"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zomato"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.6.0")) // Firebase Bill of Materials (BoM)
    implementation("com.google.firebase:firebase-analytics")  // Firebase Analytics
    implementation("com.google.firebase:firebase-auth") // Firebase Authentication
    implementation("com.google.android.gms:play-services-auth:21.2.0") // Google Sign-In
    // Firebase SDK for Realtime Database
    implementation ("com.google.firebase:firebase-database:20.0.5") // Use the latest version
    // Firebase Authentication (if you're using it)
    implementation ("com.google.firebase:firebase-auth:21.0.5")
    // Firebase SDK (necessary for Firebase functions)
    implementation ("com.google.firebase:firebase-core:20.1.0")


    // AndroidX Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore.ktx)

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
