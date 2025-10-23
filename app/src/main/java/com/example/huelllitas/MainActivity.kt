package com.example.huelllitas

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.huelllitas.fragments.LoginFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var header: View
    private lateinit var footer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        header = findViewById(R.id.header_container)
        footer = findViewById(R.id.bottom_navigation)
        if (savedInstanceState == null){
            initialFragment()
        }

        initViews()
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                    super.onFragmentResumed(fm, f)
                    updateMenuVisibility(f)
                }
            }, true
        )
    }
    fun updateMenuVisibility(currentFragment: Fragment?) {
        val current = supportFragmentManager.findFragmentById(R.id.fragment_content)
        val hide = current is LoginFragment

        findViewById<View>(R.id.header_container).visibility = if (hide) View.GONE else View.VISIBLE
        findViewById<View>(R.id.bottom_navigation).visibility = if (hide) View.GONE else View.VISIBLE
        findViewById<View>(R.id.nav_view).visibility = if (hide) View.GONE else View.VISIBLE
    }
    private fun initialFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, LoginFragment())
            .commitNow()

    }
    private fun initViews() {
        drawerLayout = findViewById(R.id.main)
        navigationView= findViewById(R.id.nav_view)

        findViewById<android.widget.ImageView>(R.id.ivMenu).setOnClickListener {
            if(drawerLayout.isDrawerOpen(navigationView)){
                drawerLayout.closeDrawer(navigationView)
            } else {
                drawerLayout.openDrawer(navigationView)
            }
        }

    }
}