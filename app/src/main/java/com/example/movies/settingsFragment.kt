package com.example.movies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.Adapter.WatchListAdapter
import android.widget.Toast.makeText as makeText1


class settingsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settingss, container, false)

        val loginButton = view.findViewById<Button>(R.id.logButton)
        val accountbutton = view.findViewById<Button>(R.id.accountButton)
        val crashButton = view.findViewById<Button>(R.id.crashButton)
        var log: Boolean
        log = false


        loginButton.setOnClickListener {
            val intent = Intent(this@settingsFragment.requireContext(), loginActivity::class.java)
            startActivity(intent)
            log = true
        }

        accountbutton.setOnClickListener {
            if(log==true){
                val intent = Intent(this@settingsFragment.requireContext(), profileActivity::class.java)
                startActivity(intent)
            }
            else if(log==false)
            {
                Toast.makeText(activity,"Please login or sign up first", Toast.LENGTH_LONG).show()
                val intent = Intent(this@settingsFragment.requireContext(), loginActivity::class.java)
                log = true
                startActivity(intent)
        }

            }



        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }


        return view
    }


}