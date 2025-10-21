package com.example.huelllitas.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.huelllitas.R


class LoginFragment: Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container,false)
        val btnLogin = view.findViewById<Button>(R.id.buttonLogin)

        btnLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_content, HomeFragment())
                .addToBackStack(null)
                .commit()
        }
        return view
    }

}