package com.mobdeve.s11.mco.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.mco.R
import com.mobdeve.s11.mco.model.Menu

class MenuAdapter(private val context: Context, private val dataset: List<Menu>):
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

        companion object {
            val TITLE = "title_key"
            val PRICE = "price_key"
            val IMAGE = "image_key"
            val QUANTITY = "quantity_key"
            val SIZE = "size_key"
            val DESCRIPTION = "description_key"
            val MAX_PRICE = "maxPrice_key"
        }

        /**
         * Provides a reference for the views needed to display items in your list.
         */
        class MenuViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val menuPhoto = view.findViewById<ImageView>(R.id.history_item_photo)
            val menuTitle = view.findViewById<TextView>(R.id.menu_item_title)
            val menuPrice = view.findViewById<TextView>(R.id.order_total)
            val parent = view.findViewById<ConstraintLayout>(R.id.menu_title)
            val description = view.findViewById<TextView>(R.id.menu_description)
        }

        override fun getItemCount(): Int {
            return dataset.size
        }

        /**
         * Creates new views with R.layout.item_view as its template
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
            val layout = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.menu_item, parent, false)

            // Setup custom accessibility delegate to set the text read
            //layout.accessibilityDelegate = Accessibility
            return MenuViewHolder(layout)
        }

        /**
         * Replaces the content of an existing view with new data
         */
        override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
            val item = dataset[position]
            val resourceId = context.resources.getIdentifier(item.menuImage, "drawable", context.packageName)
            holder.menuPhoto.setImageResource(resourceId)
            holder.menuTitle.text = item.menuTitle
            holder.menuPrice.text = ("₱${item.menuPrice} to ₱${item.priceMax}")
            holder.description.text = item.menuDescription

            // Assigns a [OnClickListener] to the button contained in the [ViewHolder]
            holder.parent.setOnClickListener {
                // Create an action from WordList to DetailList
                // using the required argument
                val bundle = Bundle()
                bundle.putString("title_key", holder.menuTitle.text.toString())
                bundle.putString("price_key", "₱"+item.menuPrice.toString())
                bundle.putString("maxPrice_key", "₱"+item.priceMax.toString())
                bundle.putString("image_key", item.menuImage)
                bundle.putString("description_key",item.menuDescription)
                // Navigate using that action
                holder.view.findNavController().navigate(R.id.action_Menu_to_MenuItem, bundle)
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