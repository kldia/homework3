package com.example.authenticationproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()

    }

    private fun init(){
        auth = Firebase.auth
        SignUpButton2.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        val email :String = Email.text.toString()
        val password :String = Password.text.toString()
        val confirmpassword :String = ConfirmPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && confirmpassword.isNotEmpty()){
            if (password == confirmpassword){
                ProgressBar.visibility = View.VISIBLE
                SignUpButton2.isClickable = false
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            ProgressBar.visibility = View.GONE
                            if (task.isSuccessful) {
                                d("sign up", "createUserWithEmail:success")
                                Toast.makeText(this, "SignUp is Success!", Toast.LENGTH_SHORT).show()
                            } else {
                                d("sign up", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
            }
            else{
                Toast.makeText(this, "password must be same", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(this, "please fill all fields!", Toast.LENGTH_SHORT).show()
        }
    }
}