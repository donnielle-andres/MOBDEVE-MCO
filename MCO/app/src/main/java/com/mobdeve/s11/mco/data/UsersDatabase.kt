package com.mobdeve.s11.mco.data
import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getFloatOrNull
import com.mobdeve.s11.mco.model.Order
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.mobdeve.s11.mco.model.User

class UsersDatabase (context: Context){
    // A private instance of the DB helper
    private lateinit var databaseHandler : DatabaseHandler

    // Initializes the databaseHandler instance using the context provided.
    init {
        this.databaseHandler = DatabaseHandler(context)
    }
    fun insertUser(newUser: User) {
        val db = databaseHandler.writableDatabase
        val email = newUser.email
        val fullname = newUser.fullName
        val password = newUser.password
        val number = newUser.number
        val values = ContentValues().apply {
            put(DatabaseHandler.EMAIL, email)
            put(DatabaseHandler.FULL_NAME, fullname)
            put(DatabaseHandler.PASSWORD, password)
            put(DatabaseHandler.NUMBER, number)
        }

        // Insert the order into the database
        db.insert(DatabaseHandler.USERS_TABLE, null, values)
        db.close()
    }

    fun getUser(username: String): User? {
        val db = databaseHandler.readableDatabase
        val fields = arrayOf(
            DatabaseHandler.EMAIL,
            DatabaseHandler.FULL_NAME,
            DatabaseHandler.PASSWORD,
            DatabaseHandler.NUMBER
        )

        // Add a WHERE clause to filter users by username
        val selection = "${DatabaseHandler.EMAIL} = ?"
        val selectionArgs = arrayOf(username)

        val cursor = db.query(
            "${DatabaseHandler.USERS_TABLE}",
            fields,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val storedEmail = cursor.getStringOrNull(cursor.getColumnIndex("${DatabaseHandler.EMAIL}"))
            val fullName = cursor.getStringOrNull(cursor.getColumnIndex("${DatabaseHandler.FULL_NAME}"))

            val password = cursor.getStringOrNull(cursor.getColumnIndex("${DatabaseHandler.PASSWORD}"))
            val number = cursor.getStringOrNull(cursor.getColumnIndex("${DatabaseHandler.NUMBER}"))

            cursor.close()

            return User(storedEmail!!,fullName!!, password!!, number!!)
        }

        cursor.close()
        return null
    }

}