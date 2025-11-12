package com.example.huelllitas.fragments

import android.content.Context
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
import androidx.fragment.app.Fragment
import com.example.huelllitas.R
import com.example.huelllitas.utils.PreferencesManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlin.text.replace

@Suppress("DEPRECATION")
class LoginFragment : Fragment() {
    private lateinit var btnGoogle: Button
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView

    private lateinit var recoveryPassword: TextView

    companion object{
        private const val TAG="LoginFragment"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        preferencesManager = PreferencesManager(requireContext())

        initViews(view)
        setupListeners()

        btnGoogle=view.findViewById(R.id.btnGoogle)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1080398941603-ddbg9e2ol8jt7b2tg0d90o743robvuou.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient=GoogleSignIn.getClient(requireActivity(),gso)

        btnGoogle.setOnClickListener {
            signIn()
        }


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
    private fun signIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()){ task ->
                if (task.isSuccessful){
                    Log.d(TAG,"signInWithCredential:success")
                    Toast.makeText(context,"Bienvenido", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_content, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                } else {
                    Log.w(TAG, "signinWithCredential:failure", task.exception)
                    Toast.makeText(context,"Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        if (currentUser != null){
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_content, HomeFragment())
//                .addToBackStack(null)
//                .commit()
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val accountData = task.getResult(ApiException::class.java)!!
                Log.d(TAG,"firebaseAuthWithGoogle:"+ accountData.id)
                firebaseAuthWithGoogle(accountData.idToken!!)
            } catch (e: ApiException){
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(context,"Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}




