package com.mobdeve.s11.mco.data
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getFloatOrNull
import com.mobdeve.s11.mco.model.Order
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
class OrdersDatabase (context: Context){
    // A private instance of the DB helper
    private lateinit var databaseHandler : DatabaseHandler

    // Initializes the databaseHandler instance using the context provided.
    init {
        this.databaseHandler = DatabaseHandler(context)
    }
    fun insertOrder(order: Order) {
        val db = databaseHandler.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseHandler.USER_EMAIL, order.userEmail)
            put(DatabaseHandler.ORDER_ITEMS, order.orderItems)
            put(DatabaseHandler.ORDER_ADDRESS, order.orderAddress)
            put(DatabaseHandler.ORDER_TOTAL, order.orderTotal)
            put(DatabaseHandler.ORDER_DATE, order.orderDate)
        }

        // Insert the order into the database
        db.insert(DatabaseHandler.ORDERS_TABLE, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getOrderById(orderId: Int): Order? {
        val selection = "${DatabaseHandler.ORDER_ID} = ?"
        val selectionArgs = arrayOf(orderId.toString())
        val db = databaseHandler.readableDatabase
        val fields = arrayOf(
            DatabaseHandler.ORDER_ID,
            DatabaseHandler.ORDER_TOTAL,
            DatabaseHandler.ORDER_ADDRESS,
            DatabaseHandler.ORDER_ITEMS,
            DatabaseHandler.ORDER_DATE,
            DatabaseHandler.USER_EMAIL
        )

        val cursor = db.query(
            DatabaseHandler.ORDERS_TABLE,
            fields,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.count > 0 && cursor.moveToFirst()) {
            val orderID = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ORDER_ID))
            val orderTotal = cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.ORDER_TOTAL))
            val orderAddress = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ORDER_ADDRESS))
            val orderItems = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ORDER_ITEMS))
            val orderDate = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ORDER_DATE))
            val userEmail = cursor.getString(cursor.getColumnIndex(DatabaseHandler.USER_EMAIL))

            cursor.close()

            return Order(orderID, userEmail, orderItems, orderAddress, orderTotal, orderDate)
        }

        cursor.close()
        return null
    }

    fun getOrders(user: String): ArrayList<Order>{
        val selection = "${DatabaseHandler.USER_EMAIL} = ?"
        val selectionArgs = arrayOf(user)
        val result = ArrayList<Order>()
        val db = databaseHandler.readableDatabase
        val fields = arrayOf(
            DatabaseHandler.ORDER_ID,
            DatabaseHandler.ORDER_TOTAL,
            DatabaseHandler.ORDER_ADDRESS,
            DatabaseHandler.ORDER_ITEMS,
            DatabaseHandler.ORDER_DATE,
            DatabaseHandler.USER_EMAIL
        )
        val cursor = db.query("${DatabaseHandler.ORDERS_TABLE}",fields, selection, selectionArgs, null, null, null)
        Log.d("Count", "${cursor.count}")
        if(cursor.count > 0){
            if(cursor.moveToFirst()){
                do{
                    val orderID= cursor.getIntOrNull(cursor.getColumnIndex("${DatabaseHandler.ORDER_ID}"))
                    val orderTotal= cursor.getDoubleOrNull(cursor.getColumnIndex("${DatabaseHandler.ORDER_TOTAL}"))
                    val orderAddress= cursor.getStringOrNull(cursor.getColumnIndex("${DatabaseHandler.ORDER_ADDRESS}"))
                    val orderItems= cursor.getStringOrNull(cursor.getColumnIndex("${DatabaseHandler.ORDER_ITEMS}"))
                    val orderDate = cursor.getStringOrNull(cursor.getColumnIndex("${DatabaseHandler.ORDER_DATE}"))
                    val userEmail =cursor.getStringOrNull(cursor.getColumnIndex("${DatabaseHandler.USER_EMAIL}"))
                    result.add(Order( orderID!!, userEmail!!,orderItems!!, orderAddress!!,orderTotal!!,orderDate!!))
                }while(cursor.moveToNext())
            }
        }

        return result
    }
}