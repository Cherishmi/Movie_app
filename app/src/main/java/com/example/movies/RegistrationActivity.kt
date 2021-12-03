package com.example.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Main")

        register()
    }

    private fun register(){

       val registerButton = findViewById<Button>(R.id.registerButton)
        val firstname = findViewById<TextView>(R.id.firstnameInput)
        val lastname = findViewById<TextView>(R.id.lastnameInput)
        val username = findViewById<TextView>(R.id.userInput)
        val password = findViewById<TextView>(R.id.password)



        registerButton.setOnClickListener{
            if(TextUtils.isEmpty(firstname.text.toString())){
                firstname.setError("Please Enter First name")
                return@setOnClickListener
            }else if (TextUtils.isEmpty(lastname.text.toString())){
                lastname.setError("Please Enter Last name")
                return@setOnClickListener
            }else if (TextUtils.isEmpty(username.text.toString())){
                username.setError("Please Enter Username")
                return@setOnClickListener
            }else if (TextUtils.isEmpty((password.text.toString()))){
                password.setError("Please Enter password")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(username.text.toString(), password.text.toString())
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        val currentUser= auth.currentUser
                       val currentUserDB =  databaseReference?.child((currentUser?.uid!!))
                        currentUserDB?.child("Firstname")?.setValue(firstname.text.toString())
                        currentUserDB?.child("Lastname")?.setValue(lastname.text.toString())

                        Toast.makeText(this@RegistrationActivity, "Registration successful", Toast.LENGTH_LONG).show()
                        finish()


                    }else{
                        Toast.makeText(this@RegistrationActivity, "Registration failed", Toast.LENGTH_LONG).show()
                    }
                }


        }



    }
}