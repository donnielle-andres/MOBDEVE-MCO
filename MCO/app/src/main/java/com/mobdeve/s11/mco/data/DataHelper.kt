package com.mobdeve.s11.mco.data

import com.mobdeve.s11.mco.R
import com.mobdeve.s11.mco.model.Menu

class DataHelper {
    companion object {
        fun initializeData(): ArrayList<Menu> {
            val coffeeNames = arrayOf("Papua New Guinea Arufa Espresso",
                "Wild Flower", "GRAPOS decaf",
                "Golden Ticket", "Legazpi")
            val coffeePrices = arrayOf("₱700.00",
                "₱800.00", "₱700.00",
                "₱700.00", "₱700.00")
            val coffeePricesMax = arrayOf("₱1800.00", "₱1790.00",
                "₱1800.00", "₱1550.00", "₱1790.00")
            val coffeeImages = intArrayOf(
                R.drawable.papua_new, R.drawable.wild_flower,
                R.drawable.grapos_decaf, R.drawable.golden_ticket,
                R.drawable.new_legazpi
            )

            val data = ArrayList<Menu>()
            data.add(
                Menu(
                    coffeeImages[0],
                    coffeeNames[0],
                    coffeePrices[0],
                    coffeePricesMax[0],
                    0
                )
            )
            data.add(
                Menu(
                    coffeeImages[1],
                    coffeeNames[1],
                    coffeePrices[1],
                    coffeePricesMax[1],
                    1
                )
            )
            data.add(
                Menu(
                    coffeeImages[2],
                    coffeeNames[2],
                    coffeePrices[2],
                    coffeePricesMax[2],
                    2
                )
            )
            data.add(
                Menu(
                    coffeeImages[3],
                    coffeeNames[3],
                    coffeePrices[3],
                    coffeePricesMax[3],
                    3
                )
            )
            data.add(
                Menu(
                    coffeeImages[4],
                    coffeeNames[4],
                    coffeePrices[4],
                    coffeePricesMax[4],
                    4
                )
            )

            data.shuffle()

            return data;
        }
    }
}