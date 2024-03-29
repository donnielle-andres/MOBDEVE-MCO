package com.mobdeve.s11.mco.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.mco.R
import com.mobdeve.s11.mco.model.Cart

class OrderConfirmationAdapter(private val context: Context, private val dataset: List<Cart>):
    RecyclerView.Adapter<OrderConfirmationAdapter.OrderConfirmationViewHolder>() {
    val coffeeImages = intArrayOf(
        R.drawable.papua_new, R.drawable.wild_flower,
        R.drawable.grapos_decaf, R.drawable.golden_ticket,
        R.drawable.new_legazpi
    )

    // Generates a [CharRange] from 'A' to 'Z' and converts it to a list

    /**
     * Provides a reference for the views needed to display items in your list.
     */
    class OrderConfirmationViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val menuPhoto = view.findViewById<ImageView>(R.id.history_item_photo)
        val menuTitle = view.findViewById<TextView>(R.id.menu_item_title)
        val menuPrice = view.findViewById<TextView>(R.id.order_total)
        val parent = view.findViewById<ConstraintLayout>(R.id.menu_title)
        val sizeView = view.findViewById<TextView>(R.id.menu_item_size)
        val quantityView = view.findViewById<TextView>(R.id.menu_item_quantity)
        val deleteButton = view.findViewById<TextView>(R.id.delete_button)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderConfirmationViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cart_item, parent, false)

        // Setup custom accessibility delegate to set the text read
        //layout.accessibilityDelegate = Accessibility
        return OrderConfirmationViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: OrderConfirmationViewHolder, position: Int) {
        val item = dataset[position]

        var tempPrice = item.price.replace("₱", "").toFloat()
        tempPrice *= item.quantity.toFloat()

        holder.menuPhoto.setImageResource(context.resources.getIdentifier(item.imageId, "drawable", context.packageName))
        holder.menuTitle.text = item.title
        holder.menuPrice.text = ("₱${tempPrice}")
        holder.sizeView.text = item.size
        holder.quantityView.text = item.quantity.toString()

        holder.deleteButton.isVisible = false

        // Assigns a [OnClickListener] to the button contained in the [ViewHolder]
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