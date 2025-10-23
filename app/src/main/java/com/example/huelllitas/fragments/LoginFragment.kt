package com.example.huelllitas.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.huelllitas.R
import com.example.huelllitas.utils.PreferencesManager
import kotlin.text.replace

class LoginFragment : Fragment() {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView

    private lateinit var recoveryPassword: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        preferencesManager = PreferencesManager(requireContext())

        initViews(view)
        setupListeners()

        return view
    }

    private fun initViews(view: View) {
        emailInput = view.findViewById(R.id.editTextEmail)
        passwordInput = view.findViewById(R.id.editTextPassword)
        loginButton = view.findViewById(R.id.buttonLogin)
        registerText = view.findViewById(R.id.registerText)
        recoveryPassword= view.findViewById(R.id.txtForgotPassword)
    }

    private fun setupListeners() {
        loginButton.setOnClickListener {
            handleLogin()
        }

        registerText.setOnClickListener {
            navigateToRegister()
        }
        recoveryPassword.setOnClickListener {
            navigateToRecoveryPassword()
        }

    }

    private fun handleLogin() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (!validateFields(email, password)) return
        val prefs = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val savedEmail= prefs.getString("USER_EMAIL",null)
        val savedPassword = prefs.getString("USER_PASSWORD",null)

        if (email == savedEmail && password == savedPassword) {
            Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
            navigateToHome()
        } else {
            Toast.makeText(requireContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        when {
            email.isEmpty() -> {
                emailInput.error = "Ingresa tu correo"
                emailInput.requestFocus()
                return false
            }
            password.isEmpty() -> {
                passwordInput.error = "Ingresa tu contraseña"
                passwordInput.requestFocus()
                return false
            }
        }
        return true
    }

    private fun navigateToHome() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToRegister() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }
    private fun navigateToRecoveryPassword() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, RecoveryPasswordFragment())
            .addToBackStack(null)
            .commit()
    }
}


