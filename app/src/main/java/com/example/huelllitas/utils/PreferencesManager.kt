package com.example.huelllitas.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    fun saveUser(
        name: String,
        lastName: String,
        address: String,
        email: String,
        password: String
    ) {
        prefs.edit()
            .putString("USER_NAME", name)
            .putString("USER_LAST_NAME", lastName)
            .putString("USER_ADDRESS", address)
            .putString("USER_EMAIL", email)
            .putString("USER_PASSWORD", password)
            .apply()
    }

    fun getUser(): String?{
        return prefs.getString("USER_EMAIL", null)
    }

    fun clearUser() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean{
        return prefs.contains("USER_EMAIL")
    }
}