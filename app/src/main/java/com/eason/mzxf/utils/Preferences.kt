package com.eason.mzxf.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.eason.mzxf.extensions.emptyString
import com.eason.mzxf.extensions.unSafeLazy
import javax.inject.Singleton

/**
 * Created by Chatikyan on 29.07.2017.
 */
@Singleton
class Preferences(app: Application) {

    private val SHARED_PREF_NAME = "ribble_shared_pref"
    private val USER_LOGGED_IN = "user_logged_in"
    private val USER_TOKEN = "user_token"

    private val sharedPreferences by unSafeLazy {
        app.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    val isUserLoggedIn
        get() = sharedPreferences.getBoolean(USER_LOGGED_IN, false)
    val token: String
        get() = sharedPreferences.getString(USER_TOKEN, TOKEN)

    fun saveUserLoggedIn() {
        sharedPreferences.put {
            putBoolean(USER_LOGGED_IN, true)
        }
    }

    fun saveUserLoggedOut() {
        sharedPreferences.put {
            putBoolean(USER_LOGGED_IN, false)
        }
    }

    infix fun saveUserToken(token: String?) {
        sharedPreferences.put {
            putString(USER_TOKEN, token)
        }
    }

    fun deleteToken() {
        sharedPreferences.put {
            putString(USER_TOKEN, emptyString)
        }
    }
}

private inline fun SharedPreferences.put(body: SharedPreferences.Editor.() -> Unit) {
    val editor = this.edit()
    editor.body()
    editor.apply()
}