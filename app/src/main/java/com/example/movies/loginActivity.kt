package com.example.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        login()
    }

    private fun login(){
        val loginButton = findViewById<Button>(R.id.loginButton)
        val username = findViewById<TextView>(R.id.usernameInput)
        val password = findViewById<TextView>(R.id.passwordInput)
        val register = findViewById<TextView>(R.id.RegisterText)



        loginButton.setOnClickListener{

           if (TextUtils.isEmpty(username.text.toString())){
                username.setError("Please Enter Username")
                return@setOnClickListener
            }else if (TextUtils.isEmpty((password.text.toString()))){
                password.setError("Please Enter password")
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(username.text.toString(), password.text.toString())

                .addOnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this@loginActivity, profileActivity::class.java))
                        Toast.makeText(this@loginActivity, "Login successful", Toast.LENGTH_LONG).show()
                        finish()

                    }else{
                        Toast.makeText(this@loginActivity, "Login failed", Toast.LENGTH_LONG).show()

                    }
                }

        }

        register.setOnClickListener{
            startActivity(Intent(this@loginActivity, RegistrationActivity::class.java))

        }
    }

}