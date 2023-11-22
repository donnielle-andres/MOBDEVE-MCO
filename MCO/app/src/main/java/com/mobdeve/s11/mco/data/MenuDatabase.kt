package com.mobdeve.s11.mco.data

import android.content.ContentValues
import android.content.Context
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.mobdeve.s11.mco.model.Menu
import java.util.ArrayList

class MenuDatabase(context: Context) {
    private val databaseHandler: DatabaseHandler = DatabaseHandler(context)

    fun getMenuByTitle(menuTitle: String): Menu? {
        val db = databaseHandler.readableDatabase
        val fields = arrayOf(
            DatabaseHandler.ITEM_ID,
            DatabaseHandler.MENU_TYPE,
            DatabaseHandler.MENU_IMAGE,
            DatabaseHandler.MENU_TITLE,
            DatabaseHandler.MENU_DESC,
            DatabaseHandler.MENU_PRICE,
            DatabaseHandler.PRICE_MAX
        )

        val selection = "${DatabaseHandler.MENU_TITLE} = ?"
        val selectionArgs = arrayOf(menuTitle)

        val cursor = db.query(
            DatabaseHandler.MENU_TABLE,
            fields,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val itemId = cursor.getIntOrNull(cursor.getColumnIndex(DatabaseHandler.ITEM_ID))
            val menuType = cursor.getStringOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_TYPE))
            val menuImage = cursor.getStringOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_IMAGE))
            val menuDesc = cursor.getStringOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_DESC))
            val menuPrice = cursor.getFloatOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_PRICE))
            val priceMax = cursor.getFloatOrNull(cursor.getColumnIndex(DatabaseHandler.PRICE_MAX))

            cursor.close()

            return Menu(menuType!!, menuImage!!, menuTitle, menuDesc!!, menuPrice!!, priceMax!!, itemId!!)
        }

        cursor.close()
        return null
    }

    fun getMenuListByType(menuType: String): List<Menu> {
        val db = databaseHandler.readableDatabase
        val fields = arrayOf(
            DatabaseHandler.ITEM_ID,
            DatabaseHandler.MENU_TYPE,
            DatabaseHandler.MENU_IMAGE,
            DatabaseHandler.MENU_TITLE,
            DatabaseHandler.MENU_DESC,
            DatabaseHandler.MENU_PRICE,
            DatabaseHandler.PRICE_MAX
        )

        val selection = "${DatabaseHandler.MENU_TYPE} = ?"
        val selectionArgs = arrayOf(menuType)

        val cursor = db.query(
            DatabaseHandler.MENU_TABLE,
            fields,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val menuList = ArrayList<Menu>()

        while (cursor.moveToNext()) {
            val itemId = cursor.getIntOrNull(cursor.getColumnIndex(DatabaseHandler.ITEM_ID))
            val menuImage = cursor.getStringOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_IMAGE))
            val menuTitle = cursor.getStringOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_TITLE))
            val menuDesc = cursor.getStringOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_DESC))
            val menuPrice = cursor.getFloatOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_PRICE))
            val priceMax = cursor.getFloatOrNull(cursor.getColumnIndex(DatabaseHandler.PRICE_MAX))

            val menu = Menu(menuType, menuImage!!, menuTitle!!, menuDesc!!, menuPrice!!, priceMax!!, itemId!!)
            menuList.add(menu)
        }

        cursor.close()
        return menuList
    }

    fun getAllMenu(): List<Menu> {
        val db = databaseHandler.readableDatabase
        val fields = arrayOf(
            DatabaseHandler.ITEM_ID,
            DatabaseHandler.MENU_TYPE,
            DatabaseHandler.MENU_IMAGE,
            DatabaseHandler.MENU_TITLE,
            DatabaseHandler.MENU_DESC,
            DatabaseHandler.MENU_PRICE,
            DatabaseHandler.PRICE_MAX
        )

        val cursor = db.query(
            DatabaseHandler.MENU_TABLE,
            fields,
            null,
            null,
            null,
            null,
            null
        )

        val menuList = ArrayList<Menu>()

        while (cursor.moveToNext()) {
            val itemId = cursor.getIntOrNull(cursor.getColumnIndex(DatabaseHandler.ITEM_ID))
            val menuType = cursor.getStringOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_TYPE))
            val menuImage = cursor.getStringOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_IMAGE))
            val menuTitle = cursor.getStringOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_TITLE))
            val menuDesc = cursor.getStringOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_DESC))
            val menuPrice = cursor.getFloatOrNull(cursor.getColumnIndex(DatabaseHandler.MENU_PRICE))
            val priceMax = cursor.getFloatOrNull(cursor.getColumnIndex(DatabaseHandler.PRICE_MAX))

            val menu = Menu(menuType!!, menuImage!!, menuTitle!!, menuDesc!!, menuPrice!!, priceMax!!, itemId!!)
            menuList.add(menu)
        }

        cursor.close()
        return menuList
    }
}
