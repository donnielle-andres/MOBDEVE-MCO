package com.mobdeve.s11.mco.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.mco.R
import com.mobdeve.s11.mco.SessionManagement
import com.mobdeve.s11.mco.data.CartData
import com.mobdeve.s11.mco.data.DataHelper
import com.mobdeve.s11.mco.data.OrdersDatabase
import com.mobdeve.s11.mco.model.Cart
import com.mobdeve.s11.mco.model.Order

class HistoryAdapter(private val context: Context, private val dataset: List<Order>):
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    // Generates a [CharRange] from 'A' to 'Z' and converts it to a list
    /**
     * Provides a reference for the views needed to display items in your list.
     */
    class HistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val orderId = view.findViewById<TextView>(R.id.menu_item_title)
        val orderAddress = view.findViewById<TextView>(R.id.order_address)
        val orderDate = view.findViewById<TextView>(R.id.order_date)
        val orderTotal = view.findViewById<TextView>(R.id.order_total)
        val orderDetails = view.findViewById<TextView>(R.id.order_items)
        val orderAgainButton = view.findViewById<Button>(R.id.order_again)
        val currView = view
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.history_item, parent, false)

        // Setup custom accessibility delegate to set the text read
        //layout.accessibilityDelegate = Accessibility
        return HistoryViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */

    private fun prevOrderToCart(prevOrder: Order) {

        val ordersDatabase = OrdersDatabase(context)
        val sessionManager = SessionManagement(context)

        ordersDatabase.getOrderById(prevOrder.orderId)
            ?.let { sessionManager.saveAddress(it.orderAddress) }

        val data = DataHelper.initializeData()

        val orderItems = prevOrder.orderItems // Assuming that `orderItems` is a string in the format "quantity1 x title1, quantity2 x title2, ..."

        // Split the orderItems string and extract individual items
        val itemStrings = orderItems.split(", ")

        for (itemString in itemStrings) {
            val (quantityStr, titleSizeStr) = itemString.split(" x ")
            val (title, size) = titleSizeStr.split(" - ")
            val quantity = quantityStr.toInt()

            // Find the corresponding CartItem in your cart based on the title
            val existingCartItem = CartData.cartItems.find { it.title == title }

            if (existingCartItem != null) {
                // If a matching CartItem exists in the cart, update its quantity
                existingCartItem.quantity += quantity
            } else {
                // If no matching CartItem exists, create a new CartItem and add it to the cart
                val menuItem = data.find { it.title == title}
                val cartItem =
                    menuItem?.let { Cart(0,title, menuItem.price, size, quantity) } // TODO (change imageId that is passed; Need to refer from Menu)
                if (cartItem != null) {
                    CartData.cartItems.add(cartItem)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = dataset[position]
        holder.orderId.text = "Order #" + item.orderId
        holder.orderAddress.text = item.orderAddress
        holder.orderDetails.text = item.orderItems
        holder.orderDate.text = item.orderDate
        holder.orderTotal.text = "Php " + item.orderTotal
        holder.orderAgainButton.setOnClickListener{
            prevOrderToCart(item)
            holder.currView.findNavController().navigate(R.id.orderConfirmationFragment)
        }


    }

    // Setup custom accessibility delegate to set the text read with
    // an accessibility service
    /**companion object Accessibility : View.AccessibilityDelegate() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onInitializeAccessibilityNodeInfo(
    host: View,
    info: AccessibilityNodeInfo
    ) {
    super.onInitializeAccessibilityNodeInfo(host, info)
    // With `null` as the second argument to [AccessibilityAction], the
    // accessibility service announces "double tap to activate".
    // If a custom string is provided,
    // it announces "double tap to <custom string>".
    val customString = host.context?.getString(R.string.look_up_words)
    val customClick =
    AccessibilityNodeInfo.AccessibilityAction(
    AccessibilityNodeInfo.ACTION_CLICK,
    customString
    )
    info.addAction(customClick)
    }
    }*/

}