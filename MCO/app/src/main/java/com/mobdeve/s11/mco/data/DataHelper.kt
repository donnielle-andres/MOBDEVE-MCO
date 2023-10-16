package com.mobdeve.s11.mco.data

import com.mobdeve.s11.mco.R
import com.mobdeve.s11.mco.model.Menu

class DataHelper {
    companion object {
        fun initializeData(): ArrayList<Menu> {
            val coffeeNames = arrayOf("Papua New Guinea Arufa Espresso",
                "Secret Stash 041: Lourdes S. Intrinsic Cherry",
                "Wild Flower", "GRAPOS decaf",
                "Golden Ticket", "Legazpi")
            val coffeePrices = arrayOf("P700.00-P1800.00",
                "P800.00",
                "P800.00-P1790.00", "P700.00-P1800.00",
                "P700.00-P1550.00", "P700.00-P1790.00")
            val coffeeImages = intArrayOf(
                R.drawable.papua_new
            )

            val data = ArrayList<Menu>()
            data.add(
                Menu(
                    coffeeImages[0],
                    coffeeNames[0],
                    coffeePrices[0],
                )
            )
            data.add(
                Menu(
                    coffeeImages[0],
                    coffeeNames[1],
                    coffeePrices[1],
                )
            )
            data.add(
                Menu(
                    coffeeImages[0],
                    coffeeNames[2],
                    coffeePrices[2],
                )
            )
            data.add(
                Menu(
                    coffeeImages[0],
                    coffeeNames[3],
                    coffeePrices[3],
                )
            )
            data.add(
                Menu(
                    coffeeImages[0],
                    coffeeNames[4],
                    coffeePrices[4],
                )
            )
            data.add(
                Menu(
                    coffeeImages[0],
                    coffeeNames[5],
                    coffeePrices[5],
                )
            )

            data.shuffle()

            return data;
        }
    }
}