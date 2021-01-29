package com.example.authenticationproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.activity_log_in.*


class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        init()
    }
    private fun init(){
        auth = Firebase.auth
        LogInButton.setOnClickListener {
            signIn()
        }
    }
    private fun signIn(){
        val email:String = EmailField.text.toString()
        val password = PasswordField.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()){
            ProgressBarLogIn.visibility = View.VISIBLE
            LogInButtonLogIn.isClickable = false
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        ProgressBarLogIn.visibility = View.GONE
                        LogInButtonLogIn.isClickable = true
                        if (task.isSuccessful) {
                            d("signIn", "signInWithEmail:success")
                            Toast.makeText(this, "Authentication is Success!",Toast.LENGTH_SHORT).show()
                        } else {
                            d("signIn", "signInWithEmail:failure", task.exception)
                            Toast.makeText(this, "User Authentication Failed:"+task.exception?.message,Toast.LENGTH_LONG).show()
                        }

                    }
        }else{Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()}
    }
}
