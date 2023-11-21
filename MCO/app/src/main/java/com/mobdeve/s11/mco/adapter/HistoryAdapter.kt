package com.mobdeve.s11.mco.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.mco.R
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
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = dataset[position]
        holder.orderId.text = "Order #" + item.orderId
        holder.orderAddress.text = item.orderAddress
        holder.orderDetails.text = item.orderItems
        holder.orderDate.text = item.orderDate
        holder.orderTotal.text = "Php " + item.orderTotal

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