package com.mobdeve.s11.mco

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.mobdeve.s11.mco.data.CartData
import com.mobdeve.s11.mco.data.UsersDatabase
import com.mobdeve.s11.mco.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManagement


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.createAccountButton.setOnClickListener{

            findNavController().navigate(R.id.action_Login_to_signupFragment)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManagement(requireContext().applicationContext)

        binding.loginButton.setOnClickListener {
            CartData.cartItems.clear()
            val emailText = view.findViewById<TextInputLayout>(R.id.username_text)
            val passwordText = view.findViewById<TextInputLayout>(R.id.password_text)

            val email = emailText.editText?.text.toString()
            val password = passwordText.editText?.text.toString()


            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Empty field not allowed!",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                val usersDatabase = UsersDatabase(requireContext())
                val user = usersDatabase.getUser(email)
                if (user != null) {
                    if (user.password == password && user.email == email) {
                        // Authentication successful
                        sessionManager.saveUserEmail(email)
                        sessionManager.saveUserNumber(user.number)
                        sessionManager.saveFullname(user.fullName)
                        sessionManager.clearAddress()
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    }else {
                        // Authentication failed
                        Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // No Records
                    Toast.makeText(requireContext(), "User not found! Create an account", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}