package com.example.huelllitas.fragments // La declaración del paquete va primero

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.huelllitas.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {

    // --- Vistas del Layout ---
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView
    private lateinit var recoveryPassword: TextView
    private lateinit var googleLoginButton: Button // Botón de Google

    // --- NUEVO: Variables para la autenticación de Google y Firebase ---
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Configurar las opciones de Google Sign-In
        //    Pedimos el ID del usuario y su email. El 'default_web_client_id' es generado por Firebase.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Clave fundamental para Firebase
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        firebaseAuth = FirebaseAuth.getInstance()

        // 2. Registrar el callback para cuando Google nos devuelva el resultado del inicio de sesión
        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        val account = task.getResult(ApiException::class.java)!!
                        Log.d("GoogleSignIn", "Autenticación con Google exitosa: ${account.id}")
                        firebaseAuthWithGoogle(account)
                    } catch (e: ApiException) {
                        Log.w("GoogleSignIn", "Falló el inicio de sesión con Google", e)
                        Toast.makeText(
                            context,
                            "Falló el inicio de sesión con Google. Intenta de nuevo.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Se canceló el inicio de sesión con Google",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        initViews(view)
        setupListeners()
        return view
    }

    // Opcional: Revisar si el usuario ya inició sesión al volver al fragmento
    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            // Si ya hay un usuario logueado, lo mandamos directo al Home
            Log.d("LoginFragment", "Usuario ya logueado: ${currentUser.displayName}")
            // navigateToHome() // Descomenta si quieres esta funcionalidad
        }
    }

    private fun initViews(view: View) {
        emailInput = view.findViewById(R.id.editTextEmail)
        passwordInput = view.findViewById(R.id.editTextPassword)
        loginButton = view.findViewById(R.id.buttonLogin)
        registerText = view.findViewById(R.id.registerText)
        recoveryPassword = view.findViewById(R.id.txtForgotPassword)
        googleLoginButton = view.findViewById(R.id.btnGoogle) // Enlazamos el botón de Google
    }

    private fun setupListeners() {
        loginButton.setOnClickListener { handleTraditionalLogin() }
        registerText.setOnClickListener { navigateToRegister() }
        recoveryPassword.setOnClickListener { navigateToRecoveryPassword() }

        // 3. Añadir el listener para el botón de Google
        googleLoginButton.setOnClickListener {
            Log.d("GoogleSignIn", "Botón de Google presionado. Iniciando flujo...")
            signInWithGoogle()
        }
    }

    // 4. Iniciar el flujo de Google Sign-In
    private fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    // 5. Una vez que obtenemos la cuenta de Google, la usamos para autenticarnos en Firebase
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // ¡Éxito! El usuario ha iniciado sesión
                    val user = firebaseAuth.currentUser
                    Toast.makeText(context, "Bienvenido, ${user?.displayName}", Toast.LENGTH_SHORT)
                        .show()
                    navigateToHome()
                } else {
                    // Fallo en la autenticación con Firebase
                    Toast.makeText(
                        context,
                        "Falló la autenticación con Firebase.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun handleTraditionalLogin() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (!validateFields(email, password)) return

        // Aquí iría tu lógica de login con email/password usando Firebase o tu backend
        Toast.makeText(
            requireContext(),
            "Lógica de login tradicional no implementada",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun validateFields(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            emailInput.error = "Ingresa tu correo"
            emailInput.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            passwordInput.error = "Ingresa tu contraseña"
            passwordInput.requestFocus()
            return false
        }
        return true
    }

    private fun navigateToHome() {
        // Al navegar a Home, quitamos la pila de atrás para que el usuario no pueda volver al login
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, HomeFragment())
            .commit()
    }

    private fun navigateToRegister() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToRecoveryPassword() {
        // Aquí puedes agregar la navegación al fragmento de recuperar contraseña
        Toast.makeText(
            context,
            "Navegación a recuperar contraseña no implementada",
            Toast.LENGTH_SHORT
        ).show()
    }
}

