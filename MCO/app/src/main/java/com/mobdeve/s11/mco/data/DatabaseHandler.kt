package com.mobdeve.s11.mco.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler (private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // All constant variables needed for the database; Do not modify
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "YardstickDatabase.sql"
        const val ORDERS_TABLE = "orders_table"

        const val USER_EMAIL = "user_email"
        const val ORDER_ID = "id"
        const val ORDER_ITEMS = "order_items"
        const val ORDER_ADDRESS = "order_address"
        const val ORDER_TOTAL = "order_total"
        const val ORDER_DATE = "order_date"

        const val USERS_TABLE = "users_table"

        const val EMAIL = "email"
        const val FULL_NAME = "full_name"
        const val PASSWORD = "password"
        const val NUMBER = "number"
    }

    // Handles creation of the database
    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_ORDERS_TABLE = "CREATE TABLE $ORDERS_TABLE (" +
                "	$ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                "	,$USER_EMAIL TEXT" +
                "	,$ORDER_ITEMS TEXT" +
                "	,$ORDER_ADDRESS TEXT" +
                "	,$ORDER_TOTAL FLOAT" +
                "	,$ORDER_DATE TEXT" +
                ")"
        db?.execSQL(CREATE_ORDERS_TABLE)

        val CREATE_USERS_TABLE = "CREATE TABLE $USERS_TABLE (" +
                "	$EMAIL TEXT PRIMARY KEY" +
                "	,$FULL_NAME TEXT" +
                "	,$PASSWORD TEXT" +
                "	,$NUMBER TEXT"+
                ")"
        db?.execSQL(CREATE_USERS_TABLE)
        Log.d("DatabaseHandler", "onCreate called")

        // Insert two sample users during initialization
        val user1Username = "user1@yahoo.com"
        val user1Fullname = "User 1"
        val user1Password = "password1"
        val user1Number = "09771230987"

        val user2Username = "user2@yahoo.com"
        val user2Fullname = "User 2"
        val user2Password = "password2"
        val user2Number = "09175329876"

        // Insert user1
        val insertUser1 = "INSERT INTO $USERS_TABLE ($EMAIL, $FULL_NAME, $PASSWORD, $NUMBER) VALUES ('$user1Username','$user1Fullname', '$user1Password', '$user1Number')"
        db?.execSQL(insertUser1)

        // Insert user2
        val insertUser2 = "INSERT INTO $USERS_TABLE ($EMAIL, $FULL_NAME, $PASSWORD, $NUMBER) VALUES ('$user2Username', '$user2Fullname','$user2Password', '$user2Number')"
        db?.execSQL(insertUser2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $ORDERS_TABLE")
        db!!.execSQL("DROP TABLE IF EXISTS $USERS_TABLE")

        onCreate(db)
    }

}