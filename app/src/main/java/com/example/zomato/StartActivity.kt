package com.example.zomato

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class StartActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private val RC_SIGN_IN = 100

    private lateinit var phoneAuthCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationId: String? = null
    private lateinit var otpEditText: EditText
    private lateinit var phoneNumberEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Required for Firebase Auth
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Google Sign-In Button
        val googleButton: ImageView = findViewById(R.id.googleButton)
        googleButton.setOnClickListener {
            Toast.makeText(this, "Authenticating using Google account...", Toast.LENGTH_SHORT).show()

            signInWithGoogle()
            Toast.makeText(this, "Authenticated...", Toast.LENGTH_SHORT).show()

        }

        // Initialize views for phone authentication
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        otpEditText = findViewById(R.id.otpEditText)
        val sendOtpButton: Button = findViewById(R.id.sendOtpButton)
        val verifyOtpButton: Button = findViewById(R.id.verifyOtpButton)

        // Set up Phone Authentication Callbacks
        phoneAuthCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Automatically sign in the user

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@StartActivity, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                this@StartActivity.verificationId = verificationId
                Toast.makeText(this@StartActivity, "OTP sent to your phone.", Toast.LENGTH_SHORT).show()
            }
        }

        sendOtpButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()

            // Validate phone number inline
            val regex = Regex("^\\+92[0-9]{10}\$")
            if (phoneNumber.matches(regex)) {
                sendOtp(phoneNumber)
            } else {
                Toast.makeText(this, "Please enter a valid phone number starting with +92.", Toast.LENGTH_SHORT).show()
            }
        }
        verifyOtpButton.setOnClickListener {
            val otpCode = otpEditText.text.toString()
            if (!otpCode.isNullOrEmpty() && verificationId != null) {
                val credential = PhoneAuthProvider.getCredential(verificationId!!, otpCode)
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "Please enter the OTP.", Toast.LENGTH_SHORT).show()
            }
        }



        // Text animation
        val transitionText: TextView = findViewById(R.id.transitionText)
        applyTextAnimation(transitionText)
    }

    private fun sendOtp(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(phoneAuthCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Log.d("FirebaseAuth", "Phone sign-in success: ${user?.phoneNumber}")
                    navigateToLocationActivity(user)
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signInWithGoogle() {
        // Clear any previously signed-in accounts to force the account picker to show
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.e("GoogleSignInError", "Sign-in failed: ${e.statusCode}", e)
                Toast.makeText(this, "Google Sign-In failed. Try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        if (account == null) {
            Toast.makeText(this, "Google Sign-In failed. Try again.", Toast.LENGTH_SHORT).show()
            return
        }

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Log.d("FirebaseAuth", "Google sign-in success: ${user?.email}")
                    navigateToLocationActivity(user)
                } else {
                    Log.e("FirebaseAuth", "Authentication failed: ${task.exception}")
                    Toast.makeText(this, "Authentication failed. Try again.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToLocationActivity(user: FirebaseUser?) {
        val intent = Intent(this, LocationActivity::class.java).apply {
            putExtra("USER_NAME", user?.displayName ?: "Guest")
            putExtra("USER_EMAIL", user?.email ?: "N/A")
            putExtra("USER_PHONE", user?.phoneNumber ?: "N/A")
        }
        startActivity(intent)
        finish()
    }

    private fun applyTextAnimation(transitionText: TextView) {
        val fadeIn = AlphaAnimation(0.3f, 1f).apply {
            duration = 1000
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
        val scale = ScaleAnimation(
            1.0f, 1.1f, 1.0f, 1.1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
        val animationSet = AnimationSet(true).apply {
            addAnimation(fadeIn)
            addAnimation(scale)
        }
        transitionText.visibility = View.VISIBLE
        transitionText.startAnimation(animationSet)
    }
}
