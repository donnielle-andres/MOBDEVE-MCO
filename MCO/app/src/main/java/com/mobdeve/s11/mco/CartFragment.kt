package com.mobdeve.s11.mco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.mobdeve.s11.mco.adapter.CartAdapter
import com.mobdeve.s11.mco.adapter.MenuAdapter
import com.mobdeve.s11.mco.data.CartData
import com.mobdeve.s11.mco.data.CartData.Companion.cartItems
import com.mobdeve.s11.mco.data.DataHelper
import com.mobdeve.s11.mco.databinding.FragmentCartBinding
import com.mobdeve.s11.mco.databinding.FragmentMenuBinding
import com.mobdeve.s11.mco.model.Cart

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    companion object {
        val LOCATION = "location_key"

    }
    private lateinit var locationString: String

    private var _binding: FragmentCartBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationString = "No Location"
        arguments?.let {
            locationString = it.getString(LOCATION).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**binding.buttonSecond.setOnClickListener {
         * findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
        val dataset = cartItems

        println(cartItems.size)
        var totalPrice = 0.0

        for(item in dataset){
            var tempPrice = item.price.replace("₱", "").toFloat()
            tempPrice *= item.quantity.toFloat()
            totalPrice += tempPrice
        }
        val subtotalView = view.findViewById<TextView>(R.id.subtotal_price)
        subtotalView.text = ("₱${totalPrice}")

        val locationView = view.findViewById<TextView>(R.id.location)
        locationView.text = locationString
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CartAdapter(requireContext(), dataset)

        val checkoutButton = view.findViewById<MaterialButton>(R.id.checkout)

        checkoutButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("location_key", locationString)
            view.findNavController().navigate(R.id.cart_to_confirmation, bundle)
        }


//        // Adds a [DividerItemDecoration] between items
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}