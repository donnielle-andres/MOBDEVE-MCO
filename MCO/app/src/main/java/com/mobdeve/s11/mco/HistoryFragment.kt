package com.mobdeve.s11.mco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.mco.adapter.HistoryAdapter
import com.mobdeve.s11.mco.data.OrdersDatabase
import com.mobdeve.s11.mco.model.Order
import com.mobdeve.s11.mco.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManagement

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManagement(requireContext().applicationContext)
        val user = sessionManager.getUserEmail()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val ordersDatabase = OrdersDatabase(requireContext().applicationContext)

        recyclerView.adapter = HistoryAdapter(requireContext(), ordersDatabase.getOrders(user!!))

    }

    //Ignore this. For dummy data only
    private fun initializeOrderData(): List<Order> {
        // Replace with your logic to initialize order data
        // Example:
        val orderList = mutableListOf<Order>()
        orderList.add(Order(1,"john_doe@gmail.com","1 x Golden Ticket","DLSU Gokongwei Hall", 500.0,"Janury 3, 2023"))
        orderList.add(Order(2,"jane_doe@gmail.com", "1 x Legazpi","Las Pinas", 500.0,"February 5, 2023"))
        // Add more orders as needed
        return orderList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
