package com.example.movies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.movies.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class profileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Main")

        loadProfile()

    }

    private fun loadProfile(){

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        val firstname = findViewById<TextView>(R.id.fname)
        val lastname = findViewById<TextView>(R.id.lname)
        val username = findViewById<TextView>(R.id.uname)

        val user = auth.currentUser
        val userReference=databaseReference?.child(user?.uid!!)

        username.text ="Username: "+user?.email

        userReference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                firstname.text ="Firstname: "+snapshot.child("Firstname").value.toString()
                lastname.text = "Lastname: "+snapshot.child("Lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        logoutButton.setOnClickListener{

            auth.signOut()
            startActivity(Intent(this@profileActivity, loginActivity::class.java))
            Toast.makeText(this@profileActivity, "Logout successful", Toast.LENGTH_LONG).show()
            finish()
        }
    }



}