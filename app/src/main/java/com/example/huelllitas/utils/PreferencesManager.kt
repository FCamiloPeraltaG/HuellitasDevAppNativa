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
    fun saveGoogleUserData(name: String?, email: String?, photoUrl: String?) {
        prefs.edit()
            .putString("USER_NAME", name)
            .putString("USER_EMAIL", email)
            .putString("USER_PHOTO", photoUrl)
            .putString("LOGIN_TYPE", "google")
            .apply()
    }

    fun getUserPhoto(): String? {
        return prefs.getString("USER_PHOTO", null)
    }

    fun getUserData(): Map<String, String?> {
        return mapOf(
            "USER_NAME" to prefs.getString("USER_NAME", ""),
            "USER_LAST_NAME" to prefs.getString("USER_LAST_NAME", ""),
            "USER_ADDRESS" to prefs.getString("USER_ADDRESS", ""),
            "USER_EMAIL" to prefs.getString("USER_EMAIL", ""),
            "USER_PASSWORD" to prefs.getString("USER_PASSWORD", "")
        )
    }

    fun clearUser() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.contains("USER_EMAIL")
    }

    fun saveLoginType(type: String) {
        prefs.edit().putString("LOGIN_TYPE", type).apply()
    }

    fun getLoginType(): String? {
        return prefs.getString("LOGIN_TYPE", null)
    }
}
