package com.example.huelllitas.fragments

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

class RegisterFragment : Fragment() {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var nameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var addressInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var haveAccountText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        preferencesManager = PreferencesManager(requireContext())

        initViews(view)
        setupListeners()

        return view
    }
    private fun initViews(view: View) {
        nameInput = view.findViewById(R.id.editTextName)
        lastNameInput = view.findViewById(R.id.editTextLastName)
        addressInput = view.findViewById(R.id.editTextAddress)
        emailInput = view.findViewById(R.id.editTextEmail)
        passwordInput = view.findViewById(R.id.editTextPassword)
        registerButton = view.findViewById(R.id.buttonLogin)
        haveAccountText= view.findViewById(R.id.loginText)
    }
    private fun setupListeners() {
        registerButton.setOnClickListener {
            handleRegister()
        }

        haveAccountText.setOnClickListener {
            navigateToLogin()
        }
    }
    private fun handleRegister() {
        val name = nameInput.text.toString().trim()
        val lastName = lastNameInput.text.toString().trim()
        val address = addressInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (!validateFields(name, lastName, address, email, password)) return

        saveUser(name,lastName,address,email,password)
        Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
        navigateToLogin()
    }
    private fun validateFields(
        name: String,
        lastName: String,
        address: String,
        email: String,
        password: String
    ): Boolean {
        when {
            name.isEmpty() -> {
                nameInput.error = "Ingresa tu nombre"
                nameInput.requestFocus()
                return false
            }
            lastName.isEmpty() -> {
                lastNameInput.error = "Ingresa tu apellido"
                lastNameInput.requestFocus()
                return false
            }
            address.isEmpty() -> {
                addressInput.error = "Ingresa tu dirección"
                addressInput.requestFocus()
                return false
            }
            email.isEmpty() -> {
                emailInput.error = "Ingresa tu correo"
                emailInput.requestFocus()
                return false
            }
            password.isEmpty() -> {
                passwordInput.error = "Ingresa una contraseña"
                passwordInput.requestFocus()
                return false
            }
        }
        return true
    }
    private fun saveUser(name: String,lastName: String,address: String,email: String,password: String) {

        preferencesManager.saveUser(name,lastName,address,email,password)
    }
    private fun navigateToLogin() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, LoginFragment())
            .addToBackStack(null)
            .commit()
    }
}
