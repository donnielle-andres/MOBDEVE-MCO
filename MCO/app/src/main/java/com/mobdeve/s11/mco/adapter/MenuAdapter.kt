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
import com.mobdeve.s11.mco.model.Cart
import com.mobdeve.s11.mco.model.Menu

class MenuAdapter(private val context: Context, private val dataset: List<Menu>, private val cartItems: List<Cart>):
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

        // Generates a [CharRange] from 'A' to 'Z' and converts it to a list

        /**
         * Provides a reference for the views needed to display items in your list.
         */
        class MenuViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val menuPhoto = view.findViewById<ImageView>(R.id.menu_item_photo)
            val menuTitle = view.findViewById<TextView>(R.id.menu_item_title)
            val menuPrice = view.findViewById<TextView>(R.id.order_total)
            val parent = view.findViewById<ConstraintLayout>(R.id.menu_title)
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
            holder.menuPhoto.setImageResource(item.imageId)
            holder.menuTitle.text = item.title
            holder.menuPrice.text = ("${item.price} to ${item.priceMax}")


            // Assigns a [OnClickListener] to the button contained in the [ViewHolder]
            holder.parent.setOnClickListener {
                // Create an action from WordList to DetailList
                // using the required argument
                val bundle = Bundle()
                bundle.putString("title_key", holder.menuTitle.text.toString())
                bundle.putString("price_key", item.price)
                bundle.putString("maxPrice_key", item.priceMax)
                bundle.putString("image_key", item.itemId.toString())
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