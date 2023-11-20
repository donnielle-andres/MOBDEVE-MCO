package com.mobdeve.s11.mco

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.mobdeve.s11.mco.data.UsersDatabase
import com.mobdeve.s11.mco.databinding.FragmentProfileBinding
import com.mobdeve.s11.mco.databinding.FragmentSignupBinding
import com.mobdeve.s11.mco.model.User

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManagement


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManagement(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**binding.buttonSecond.setOnClickListener {
         * findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/

        val usernameView = view.findViewById<TextView>(R.id.userName)
        val userNumberView = view.findViewById<TextView>(R.id.userPhone)
        usernameView.setText(sessionManager.getUserEmail())
        userNumberView.setText(sessionManager.getUserNumber())

        val orderHistoryButton = view.findViewById<Button>(R.id.orderHistoryBtn)
        val logoutButton = view.findViewById<Button>(R.id.logOutBtn)
        orderHistoryButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_profileFragment_to_historyFragment)
        }

        logoutButton.setOnClickListener{
            sessionManager.logout()
            view.findNavController().navigate(R.id.mainActivity)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}