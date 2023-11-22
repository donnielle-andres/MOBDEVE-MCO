package com.mobdeve.s11.mco

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobdeve.s11.mco.databinding.FragmentMenuBinding
import com.mobdeve.s11.mco.adapter.MenuAdapter
import com.mobdeve.s11.mco.model.Cart
import com.mobdeve.s11.mco.data.CartData.Companion.cartItems
import com.mobdeve.s11.mco.data.MenuDatabase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MenuFragment : Fragment() {

    private lateinit var autocompleteSupportFragment: AutocompleteSupportFragment
    private var _binding: FragmentMenuBinding? = null
    private lateinit var sessionManager: SessionManagement

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManagement(requireContext().applicationContext)

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
        val db = MenuDatabase(requireContext())
        val dataset = db.getAllMenu()

        val cartButton = view.findViewById<FloatingActionButton>(R.id.fab)
        val beanButton = view.findViewById<Button>(R.id.button1)
        val espBarButton = view.findViewById<Button>(R.id.button2)
        val exclusButton = view.findViewById<Button>(R.id.button3)
        val noncafButton = view.findViewById<Button>(R.id.button4)

        val recyclerView = binding.recyclerView
        val beanItems = dataset.filter { it.menuType == "ICED" }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MenuAdapter(requireContext(), beanItems)


        cartButton.setOnClickListener{
            val bundle = Bundle()
            view.findNavController().navigate(R.id.menu_to_cart, bundle)
        }
        var inputAddressHint = "Enter address"
        val deliverToText = view.findViewById<TextView>(R.id.deliverTo)
        autocompleteSupportFragment = (childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as? AutocompleteSupportFragment)!!

        if (autocompleteSupportFragment != null) {
            if(sessionManager.getAddress()!=null){
                autocompleteSupportFragment.setHint(sessionManager.getAddress())
            }
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

            val beanItems = dataset.filter { it.menuType == "ICED" }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = MenuAdapter(requireContext(), beanItems)
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

            val beanItems = dataset.filter { it.menuType == "HOT" }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = MenuAdapter(requireContext(), beanItems)

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

            val beanItems = dataset.filter { it.menuType == "EXCLUSIVE" }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = MenuAdapter(requireContext(), beanItems)
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

            val beanItems = dataset.filter { it.menuType == "NON CAF" }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = MenuAdapter(requireContext(), beanItems)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}