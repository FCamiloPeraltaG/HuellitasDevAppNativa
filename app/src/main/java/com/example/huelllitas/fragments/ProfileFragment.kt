package com.example.huelllitas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.huelllitas.R
import com.example.huelllitas.utils.PreferencesManager

class ProfileFragment : Fragment() {
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var editName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editAddress: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editButton: Button
    private lateinit var profileImage: ImageView
    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        preferencesManager = PreferencesManager(requireContext())

        editName = view.findViewById(R.id.editName)
        editLastName = view.findViewById(R.id.editLastName)
        editAddress = view.findViewById(R.id.editAddress)
        editEmail = view.findViewById(R.id.editEmail)
        editPassword = view.findViewById(R.id.editPassword)
        editButton = view.findViewById(R.id.buttonLogin)
        profileImage = view.findViewById(R.id.profileImage)

        loadUserData()

        val loginType = preferencesManager.getLoginType()

        if (loginType == "google") {
            disableEditing()
            editButton.visibility = View.GONE
        } else {
            enableEditing(false)
        }

        editButton.setOnClickListener {
            if (!isEditing) {
                enableEditing(true)
                editButton.text = "Guardar cambios"
            } else {
                saveChanges()
                enableEditing(false)
                editButton.text = "Editar perfil"
            }
            isEditing = !isEditing
        }

        return view
    }

    private fun loadUserData() {
        val userData = preferencesManager.getUserData()
        val photoUrl = preferencesManager.getUserPhoto()

        editName.setText(userData["USER_NAME"])
        editLastName.setText(userData["USER_LAST_NAME"])
        editAddress.setText(userData["USER_ADDRESS"])
        editEmail.setText(userData["USER_EMAIL"])
        editPassword.setText(userData["USER_PASSWORD"])

        if (!photoUrl.isNullOrEmpty()){
            Glide.with(this)
                .load(photoUrl)
                .placeholder(R.drawable.profile_image)
                .circleCrop()
                .into(profileImage)
        }
        enableEditing(false)
    }

    private fun enableEditing(enabled: Boolean) {
        editName.isEnabled = enabled
        editLastName.isEnabled = enabled
        editAddress.isEnabled = enabled
        editEmail.isEnabled = enabled
        editPassword.isEnabled = enabled
    }

    private fun disableEditing() {
        enableEditing(false)
    }

    private fun saveChanges() {
        preferencesManager.saveUser(
            editName.text.toString(),
            editLastName.text.toString(),
            editAddress.text.toString(),
            editEmail.text.toString(),
            editPassword.text.toString()
        )
    }
}
