package com.apriega77.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun setSignedIn(username: String) {
        prefs.edit { putString("signed_in_user", username) }
    }

    fun getSignedInUser(): String? = prefs.getString("signed_in_user", null)

    fun isUserSignedIn(): Boolean = getSignedInUser() != null

    fun signOut(): Boolean {
        return try {
            prefs.edit {
                remove("signed_in_user")
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}