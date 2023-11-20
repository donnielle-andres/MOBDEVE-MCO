package com.mobdeve.s11.mco

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("Session", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_NUMBER = "user_number"

    }

    fun saveUserEmail(email: String) {
        editor.putString(KEY_USER_EMAIL, email)
        editor.apply()
    }
    fun saveUserNumber(number: String) {
        editor.putString(KEY_USER_NUMBER, number)
        editor.apply()
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }
    fun getUserNumber(): String? {
        return sharedPreferences.getString(KEY_USER_NUMBER, null)
    }

    fun logout() {
        editor.remove(KEY_USER_EMAIL)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return getUserEmail() != null
    }
}
