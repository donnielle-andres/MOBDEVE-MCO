package com.mobdeve.s11.mco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.mobdeve.s11.mco.adapter.CartAdapter
import com.mobdeve.s11.mco.adapter.MenuAdapter
import com.mobdeve.s11.mco.adapter.OrderConfirmationAdapter
import com.mobdeve.s11.mco.data.CartData
import com.mobdeve.s11.mco.data.CartData.Companion.cartItems
import com.mobdeve.s11.mco.data.DataHelper
import com.mobdeve.s11.mco.data.OrdersDatabase
import com.mobdeve.s11.mco.databinding.FragmentCartBinding
import com.mobdeve.s11.mco.databinding.FragmentMenuBinding
import com.mobdeve.s11.mco.databinding.FragmentOrderConfirmationBinding
import com.mobdeve.s11.mco.model.Cart
import com.mobdeve.s11.mco.model.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderConfirmationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    companion object {
        val LOCATION = "location_key"

    }
    private lateinit var locationString: String

    private var _binding: FragmentOrderConfirmationBinding? = null


    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManagement

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
        _binding = FragmentOrderConfirmationBinding.inflate(inflater, container, false)


        return binding.root
    }

    // Inside OrderConfirmationFragment
    private fun insertOrderIntoDatabase(cartItems: List<Cart>, location: String, username: String) {
        val user = username// Replace with the actual user email
        val orderItems = cartItems.joinToString { "${it.quantity} x ${it.title} - ${it.size}" }
        val orderAddress = location
        val orderTotal = cartItems.sumOf { it.price.replace("₱", "").toDouble() * it.quantity }
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        val orderDate = dateFormat.format(currentDate)

        val ordersDatabase = OrdersDatabase(requireContext())
        val order = Order(0, user, orderItems, orderAddress, orderTotal, orderDate)

        ordersDatabase.insertOrder(order)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**binding.buttonSecond.setOnClickListener {
         * findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
        val dataset = cartItems
        var userEmail = "Guest"
        var userNumber = "0000"
        println(cartItems.size)
        var subtotalPrice = 0.0
        var totalPrice = 0.0

        for(item in dataset){
            var tempPrice = item.price.replace("₱", "").toFloat()
            tempPrice *= item.quantity.toFloat()
            subtotalPrice += tempPrice
        }
        val subtotalView = view.findViewById<TextView>(R.id.subtotal_price)
        val totalView = view.findViewById<TextView>(R.id.total_price)

        subtotalView.text = ("₱${subtotalPrice}")
        totalView.text = ("₱${subtotalPrice+50.00}")

        val locationView = view.findViewById<TextView>(R.id.location)

        val usernameView = view.findViewById<TextView>(R.id.name)
        val userNumberView = view.findViewById<TextView>(R.id.user_number)
        sessionManager = SessionManagement(requireContext().applicationContext)
        locationString = sessionManager.getAddress()!!
        locationView.text = sessionManager.getAddress()

        if (sessionManager.isLoggedIn()) {
            // If logged in, get the user's email (assuming email is used as a username here)
            userEmail = sessionManager.getUserEmail()!!
            userNumber = sessionManager.getUserNumber()!!
        }
        usernameView.setText(userEmail)
        userNumberView.setText(userNumber)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = OrderConfirmationAdapter(requireContext(), dataset)

        val checkoutButton = view.findViewById<MaterialButton>(R.id.checkout)
        checkoutButton.setOnClickListener{
            if(sessionManager.getAddress()!=null){
                Toast.makeText(requireContext(), "Order confirmed! Please wait for 30 minutes.",
                    Toast.LENGTH_SHORT).show();
                insertOrderIntoDatabase(cartItems, locationString,userEmail)

                cartItems.clear()
                view.findNavController().navigate(R.id.order_now)
            }
            else{
                Toast.makeText(requireContext(), "Delivery Address is required!",
                    Toast.LENGTH_SHORT).show();

            }

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