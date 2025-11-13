package com.example.huelllitas

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.huelllitas.fragments.ProfileFragment
import com.example.huelllitas.fragments.HomeFragment
import com.example.huelllitas.fragments.LoginFragment
import com.example.huelllitas.fragments.RecoveryPasswordFragment
import com.example.huelllitas.fragments.RegisterFragment
import com.example.huelllitas.utils.PreferencesManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
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

        val hideAll = current is LoginFragment || current is RegisterFragment || current is RecoveryPasswordFragment
        val hideOnlyFooter = current is ProfileFragment

        findViewById<View>(R.id.header_container).visibility = when {
            hideAll -> View.GONE
            else -> View.VISIBLE
        }

        findViewById<View>(R.id.bottom_navigation).visibility = when {
            hideAll || hideOnlyFooter -> View.GONE
            else -> View.VISIBLE
        }

        findViewById<View>(R.id.nav_view).visibility = when {
            hideAll -> View.GONE
            else -> View.VISIBLE
        }
    }

    private fun initialFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, LoginFragment())
            .commitNow()

    }
    private fun initViews() {
        drawerLayout = findViewById(R.id.main)
        navigationView= findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        findViewById<android.widget.ImageView>(R.id.ivMenu).setOnClickListener {
            if(drawerLayout.isDrawerOpen(navigationView)){
                drawerLayout.closeDrawer(navigationView)
            } else {
                drawerLayout.openDrawer(navigationView)
            }
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home->{
                replaceFragment(HomeFragment())
            }
            R.id.profile-> {
                replaceFragment(ProfileFragment())
            }
            R.id.logout -> {
                logout()
                replaceFragment(LoginFragment())
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun logout() {

        val googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        )

        googleSignInClient.signOut().addOnCompleteListener {
            supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_content, LoginFragment())
                .commit()
        }
    }
    override fun onResume() {
        super.onResume()
        val ivAvatar = findViewById<android.widget.ImageView>(R.id.ivAvatar)
        val preferencesManager = PreferencesManager(this)
        val loginType = preferencesManager.getLoginType()
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (loginType == "google" && account?.photoUrl != null) {
            Glide.with(this)
                .load(account.photoUrl)
                .placeholder(R.drawable.avatar)
                .circleCrop()
                .into(ivAvatar)
        } else {
            ivAvatar.setImageResource(R.drawable.avatar)
        }
    }

}