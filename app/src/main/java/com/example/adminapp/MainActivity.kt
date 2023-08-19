package com.example.adminapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hbb20.CountryCodePicker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var phoneNumber: String
    private lateinit var vId: String
    private var requestcode = 1234
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        val phoneno = findViewById<EditText>(R.id.phone_number)
        val ccp = findViewById<CountryCodePicker>(R.id.ccp)
        val sendOtpButton = findViewById<Button>(R.id.sendOtp)

        //        for phone auth
        sendOtpButton.setOnClickListener {
            ccp.registerCarrierNumberEditText(phoneno)
            phoneNumber = ccp.fullNumberWithPlus.replace(" ", "")
            initiateOtp()
        }
        // verify otp


        val enterOtp=findViewById<EditText>(R.id.otp)
        val verifyButton=findViewById<Button>(R.id.verifyOtp)
        verifyButton.setOnClickListener {

            val credential = PhoneAuthProvider.getCredential(vId, enterOtp.text.toString())
            auth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Toast.makeText(this, "verified", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Registation successfull", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,SendNoticeActivity::class.java))
                }
                .addOnFailureListener {
                    Toast.makeText(this, "not verify", Toast.LENGTH_SHORT).show()
                }

        }

}
    // send otp message
    private fun initiateOtp() {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onCodeSent(
                    verificationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    vId = verificationId
                    Toast.makeText(this@MainActivity, "send $verificationId", Toast.LENGTH_SHORT).show()

                }

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(e: FirebaseException) {

                }

            })

            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
}