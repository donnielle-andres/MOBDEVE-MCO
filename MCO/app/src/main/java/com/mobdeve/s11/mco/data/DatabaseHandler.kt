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
        const val ORDER_IMAGE = "order_image"

        const val USERS_TABLE = "users_table"

        const val EMAIL = "email"
        const val FULL_NAME = "full_name"
        const val PASSWORD = "password"
        const val NUMBER = "number"

        const val MENU_TABLE = "menu_table"
        const val ITEM_ID = "item_id"
        const val MENU_TYPE = "menu_type"
        const val MENU_IMAGE = "menu_image"
        const val MENU_TITLE = "menu_title"
        const val MENU_DESC = "menu_desc"
        const val MENU_PRICE = "menu_price"
        const val PRICE_MAX = "price_max"
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
                "	,$ORDER_IMAGE TEXT" +
                ")"
        db?.execSQL(CREATE_ORDERS_TABLE)

        val CREATE_USERS_TABLE = "CREATE TABLE $USERS_TABLE (" +
                "	$EMAIL TEXT PRIMARY KEY" +
                "	,$FULL_NAME TEXT" +
                "	,$PASSWORD TEXT" +
                "	,$NUMBER TEXT"+
                ")"
        db?.execSQL(CREATE_USERS_TABLE)

        val CREATE_MENU_TABLE = "CREATE TABLE $MENU_TABLE (" +
                "$ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$MENU_TYPE TEXT," +
                "$MENU_IMAGE TEXT," +
                "$MENU_TITLE TEXT," +
                "$MENU_DESC TEXT," +
                "$MENU_PRICE FLOAT," +
                "$PRICE_MAX FLOAT" +
                ")"
        db?.execSQL(CREATE_MENU_TABLE)

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

        val insertMenuItems = "INSERT INTO $MENU_TABLE ($MENU_TYPE, $MENU_TITLE, $MENU_DESC, $MENU_PRICE, $PRICE_MAX, $MENU_IMAGE) VALUES"+
        "('EXCLUSIVE', 'Manila Latte', 'A tribute to the Bay. Coffee on top of a secret slushie.', 200, 230, 'manila_latte'),"+
        "('EXCLUSIVE', 'Matcha Cloud Cream Coffee', 'House-made Matcha Cloud Cream on top of an iced brew.', 180, 200, 'matcha_cloud_cream_coffee'),"+
        "('EXCLUSIVE', 'Lavender Cafe Brevet', 'A sophisticated flavor that combines a rich reserve espresso shot with a scent of lavender.', 160, 180, 'lavender_cafe_brevet'),"+
        "('ICED', 'Iced Nitro Cold Brew', 'Nitrogen infused foam creating a sweet flavor without sugar and cascading, velvety crema.', 170, 185, 'iced_nitro_cold_brew'),"+
        "('ICED', 'Iced Coffee with Milk', 'Freshly brewed Iced Coffee Blend with milk served chilled and sweetened over ice.', 185, 200, 'iced_coffee_with_milk'),"+
        "('ICED', 'Iced Golden Foam', 'Cold foam contrasts with dark, smooth cold brew, yielding an inviting aroma with lush infused cold foam.', 185, 200, 'iced_golden_foam'),"+
        "('ICED', 'Iced Black', 'Smooth cup of coffee, perfect over ice.', 150, 160, 'iced_black'),"+
        "('ICED', 'Iced Sugar Cookie Latte', 'Smooth and subtly sweet Blonde Espresso, milk, ice and vanilla syrup come together to create a delightful twist on a beloved espresso classic.', 185, 200, 'iced_sugar_cookie_latte'),"+
        "('HOT', 'Hot Oat Caramel Latte', 'Lovers of oat milk? Look no further with this caramel latte.', 200, 220, 'hot_oat_caramel_latte'),"+
        "('HOT', 'Popcorn Latte', 'We turned our go-to movie snack into a delicious drink!', 180, 220, 'popcorn_latte'),"+
        "('HOT', 'Banana & Honey', 'A meal-replacing oat banana smoothie.', 170, 190, 'banana_honey'),"+
        "('HOT', 'Flat White', 'Smooth ristretto shots of espresso get the perfect amount of steamed whole milk', 170, 185, 'flat_white'),"+
        "('HOT', 'Hot Chocolate', 'Espresso combined with bittersweet mocha sauce and steamed milk, then topped with sweetened whipped cream', 200, 220, 'hot_chocolate'),"+
        "('NON CAF', 'Iced Youthberry Tea', 'Strawberry Açaí Refreshers —with hints of passion fruit and strawberry', 180, 200, 'iced_youthberry_tea'),"+
        "('NON CAF', 'Iced Tea', 'Awaken your taste buds with the zing of refreshing tea', 180, 200, 'iced_tea'),"+
        "('NON CAF', 'ICED MATCHA LATTE', 'Smooth and creamy matcha sweetened just right and served with milk over ice.', 175, 190, 'iced_matcha_latte')"
        db?.execSQL(insertMenuItems)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $ORDERS_TABLE")
        db!!.execSQL("DROP TABLE IF EXISTS $USERS_TABLE")

        onCreate(db)
    }

}