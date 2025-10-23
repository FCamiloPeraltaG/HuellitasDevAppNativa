package com.example.huelllitas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.huelllitas.R

class RecoveryPasswordFragment : Fragment(){

    private lateinit var backToLogin: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recovery_password, container,false)
        backToLogin= view.findViewById(R.id.buttonBack)
        backToLogin.setOnClickListener {
            navigateToLogin()
        }
        return view

    }

    private fun navigateToLogin(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, LoginFragment())
            .addToBackStack(null)
            .commit()
    }
}
