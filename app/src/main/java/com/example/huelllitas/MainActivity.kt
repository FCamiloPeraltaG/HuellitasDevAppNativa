package com.example.huelllitas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

//    private lateinit var drawerLayout: DrawerLayout
//    private lateinit var navigationView: NavigationView
//    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        initViews()
//        setupSharedPreferences()
//        setupNavigationView()

    }

//    private fun initViews() {
//        drawerLayout = findViewById(R.id.main)
//        navigationView= findViewById(R.id.main)
//
//
//
//    }
}