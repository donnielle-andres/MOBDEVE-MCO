package com.mobdeve.s11.mco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.s11.mco.databinding.FragmentMenuBinding
import com.mobdeve.s11.mco.adapter.MenuAdapter
import com.mobdeve.s11.mco.data.DataHelper

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MenuAdapter(requireContext(), dataset)

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