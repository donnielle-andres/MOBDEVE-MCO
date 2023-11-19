package com.mobdeve.s11.mco

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.mobdeve.s11.mco.databinding.FragmentMenuBinding
import com.mobdeve.s11.mco.adapter.MenuAdapter
import com.mobdeve.s11.mco.data.DataHelper
import com.mobdeve.s11.mco.model.Cart
import com.mobdeve.s11.mco.data.CartData
import com.mobdeve.s11.mco.data.CartData.Companion.cartItems

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MenuFragment : Fragment() {

    companion object {
        val TITLE = "title_key"
        val PRICE = "price_key"
        val IMAGE = "image_key"
        val QUANTITY = "quantity_key"
        val SIZE = "size_key"
    }

    private var _binding: FragmentMenuBinding? = null

    private lateinit var titleString: String
    private lateinit var priceString: String
    private lateinit var imageId: String
    private lateinit var quantityString: String
    private lateinit var sizeString: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titleString = ""
        arguments?.let {
            titleString = it.getString(TITLE).toString()
            priceString = it.getString(PRICE).toString()
            imageId = it.getString(IMAGE).toString()
            quantityString= it.getString(QUANTITY).toString()
            sizeString= it.getString(SIZE).toString()
        }
        if(titleString != "") {
            cartItems.add(Cart(imageId.toInt(), titleString, priceString, sizeString, quantityString.toInt()))
            Toast.makeText(requireContext(), "Order added to cart!",
                Toast.LENGTH_SHORT).show();
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
//        val locations = resources.getStringArray(R.array.locations)
//        val spinner = binding.spinnerLocations
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, locations)
//
//        spinner.adapter = adapter
//
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                // Handle item selection here
//                val selectedItem = locations[position]
//                // You can perform actions based on the selected item here
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Handle nothing selected here
//            }
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**binding.buttonSecond.setOnClickListener {
           * findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
        val dataset = DataHelper.initializeData()

        val cartButton = view.findViewById<FloatingActionButton>(R.id.fab)
        val favButton = view.findViewById<Button>(R.id.button2)

        println(cartItems.size)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MenuAdapter(requireContext(), dataset, cartItems)

//        // Adds a [DividerItemDecoration] between items
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//        )

        cartButton.setOnClickListener{
            val locationInput = view.findViewById<TextInputLayout>(R.id.address)
            val locationString = locationInput.editText?.text.toString()
            val bundle = Bundle()
            bundle.putString("location_key", locationString)
            view.findNavController().navigate(R.id.menu_to_cart, bundle)
        }

        favButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_Menu_to_historyFragment)
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}