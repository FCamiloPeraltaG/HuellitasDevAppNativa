package com.example.miapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.R

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Inicializamos SharedPreferences (donde guardaste los datos en RegisterActivity)
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        // Ajustar insets al contenedor principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scrollView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inputs y botones según los ids del XML
        val email = findViewById<EditText>(R.id.editTextEmail)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)
        val txtRegister = findViewById<TextView>(R.id.txtRegister)

        // Link hacia Register
        txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Botón login
        btnLogin.setOnClickListener {
            val getEmail = email.text.toString().trim()
            val getPassword = password.text.toString().trim()

            if (validarLogin(getEmail, getPassword)) {
                // Si es correcto, lo dirigimos al perfil
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validarLogin(email: String, password: String): Boolean {
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")

        if (email.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese su correo", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese su contraseña", Toast.LENGTH_SHORT).show()
            return false
        }

        return if (email == savedEmail && password == savedPassword) {
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            false
        }
    }
}