package com.mobdeve.s11.mco

import android.graphics.Typeface
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
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
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

    private lateinit var autocompleteSupportFragment: AutocompleteSupportFragment
    private var _binding: FragmentMenuBinding? = null

    private lateinit var titleString: String
    private lateinit var priceString: String
    private lateinit var imageId: String
    private lateinit var quantityString: String
    private lateinit var sizeString: String
    private lateinit var sessionManager: SessionManagement

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManagement(requireContext().applicationContext)

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
        if(!Places.isInitialized()){
            Places.initialize(requireContext().applicationContext,"AIzaSyDjoWTu_ftH9UpUFbpp86kYYCDzEi1d2go")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**binding.buttonSecond.setOnClickListener {
           * findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
        val dataset = DataHelper.initializeData()

        val cartButton = view.findViewById<FloatingActionButton>(R.id.fab)
        val beanButton = view.findViewById<Button>(R.id.button1)
        val espBarButton = view.findViewById<Button>(R.id.button2)
        val exclusButton = view.findViewById<Button>(R.id.button3)
        val noncafButton = view.findViewById<Button>(R.id.button4)

        println(cartItems.size)
        val recyclerView = binding.recyclerView
        val beanItems = dataset.filter { it.menuType == "Bean" }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MenuAdapter(requireContext(), beanItems, cartItems)
//        // Adds a [DividerItemDecoration] between items
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//        )

        cartButton.setOnClickListener{

            val bundle = Bundle()
            view.findNavController().navigate(R.id.menu_to_cart, bundle)
        }
        var inputAddressHint = "Enter address"
        val deliverToText = view.findViewById<TextView>(R.id.deliverTo)
        autocompleteSupportFragment = (childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as? AutocompleteSupportFragment)!!

        if (autocompleteSupportFragment != null) {
            autocompleteSupportFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.NAME))

            autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onError(p0: Status) {
                    // Handle error
                }
                override fun onPlaceSelected(p0: Place) {
                    sessionManager.saveAddress(p0.name!!)
                    deliverToText.setText(sessionManager.getAddress())
                }
            })
        } else {
            Log.e("MenuFragment", "AutocompleteSupportFragment not found")
        }
        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(p0: Status) {
                TODO("Not yet implemented")
            }

            override fun onPlaceSelected(p0: Place) {
                sessionManager.saveAddress(p0.name!!)
                deliverToText.setText("Deliver to: "+sessionManager.getAddress())
            }

        })

        // BEANS CATEG
        beanButton.setOnClickListener{
            beanButton.setBackgroundColor(resources.getColor(R.color.orange))
            beanButton.setTextColor(resources.getColor(android.R.color.white))
            beanButton.setTypeface(null, Typeface.BOLD)

            espBarButton.setBackgroundColor(resources.getColor(R.color.white))
            espBarButton.setTextColor(resources.getColor(android.R.color.black))
            espBarButton.setTypeface(null, Typeface.NORMAL)

            exclusButton.setBackgroundColor(resources.getColor(R.color.white))
            exclusButton.setTextColor(resources.getColor(android.R.color.black))
            exclusButton.setTypeface(null, Typeface.NORMAL)

            noncafButton.setBackgroundColor(resources.getColor(R.color.white))
            noncafButton.setTextColor(resources.getColor(android.R.color.black))
            noncafButton.setTypeface(null, Typeface.NORMAL)

            val beanItems = dataset.filter { it.menuType == "Bean" }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = MenuAdapter(requireContext(), beanItems, cartItems)
        }

        // ESPRESSO BAR CATEG
        espBarButton.setOnClickListener{
            espBarButton.setBackgroundColor(resources.getColor(R.color.orange))
            espBarButton.setTextColor(resources.getColor(android.R.color.white))
            espBarButton.setTypeface(null, Typeface.BOLD)

            beanButton.setBackgroundColor(resources.getColor(R.color.white))
            beanButton.setTextColor(resources.getColor(android.R.color.black))
            beanButton.setTypeface(null, Typeface.NORMAL)

            exclusButton.setBackgroundColor(resources.getColor(R.color.white))
            exclusButton.setTextColor(resources.getColor(android.R.color.black))
            exclusButton.setTypeface(null, Typeface.NORMAL)

            noncafButton.setBackgroundColor(resources.getColor(R.color.white))
            noncafButton.setTextColor(resources.getColor(android.R.color.black))
            noncafButton.setTypeface(null, Typeface.NORMAL)

            val beanItems = dataset.filter { it.menuType == "Espresso" }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = MenuAdapter(requireContext(), beanItems, cartItems)

        }

        // EXCLUSIVES CATEG
        exclusButton.setOnClickListener{
            exclusButton.setBackgroundColor(resources.getColor(R.color.orange))
            exclusButton.setTextColor(resources.getColor(android.R.color.white))
            exclusButton.setTypeface(null, Typeface.BOLD)

            beanButton.setBackgroundColor(resources.getColor(R.color.white))
            beanButton.setTextColor(resources.getColor(android.R.color.black))
            beanButton.setTypeface(null, Typeface.NORMAL)

            espBarButton.setBackgroundColor(resources.getColor(R.color.white))
            espBarButton.setTextColor(resources.getColor(android.R.color.black))
            espBarButton.setTypeface(null, Typeface.NORMAL)

            noncafButton.setBackgroundColor(resources.getColor(R.color.white))
            noncafButton.setTextColor(resources.getColor(android.R.color.black))
            noncafButton.setTypeface(null, Typeface.NORMAL)

            val beanItems = dataset.filter { it.menuType == "Exclusive" }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = MenuAdapter(requireContext(), beanItems, cartItems)
        }

        // NON CAF CATEG
        noncafButton.setOnClickListener{
            noncafButton.setBackgroundColor(resources.getColor(R.color.orange))
            noncafButton.setTextColor(resources.getColor(android.R.color.white))
            noncafButton.setTypeface(null, Typeface.BOLD)

            beanButton.setBackgroundColor(resources.getColor(R.color.white))
            beanButton.setTextColor(resources.getColor(android.R.color.black))
            beanButton.setTypeface(null, Typeface.NORMAL)

            espBarButton.setBackgroundColor(resources.getColor(R.color.white))
            espBarButton.setTextColor(resources.getColor(android.R.color.black))
            espBarButton.setTypeface(null, Typeface.NORMAL)

            exclusButton.setBackgroundColor(resources.getColor(R.color.white))
            exclusButton.setTextColor(resources.getColor(android.R.color.black))
            exclusButton.setTypeface(null, Typeface.NORMAL)

            val beanItems = dataset.filter { it.menuType == "NonCaf" }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = MenuAdapter(requireContext(), beanItems, cartItems)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}