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

    private lateinit var sessionManager: SessionManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext().applicationContext)

        binding.loginButton.setOnClickListener {
            val usernameText = view.findViewById<TextInputLayout>(R.id.username_text)
            val passwordText = view.findViewById<TextInputLayout>(R.id.password_text)

            val username = usernameText.editText?.text.toString()
            val password = passwordText.editText?.text.toString()


            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Empty field not allowed!",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                val usersDatabase = UsersDatabase(requireContext())
                val user = usersDatabase.getUser(username)
                if (user != null && user.password == password) {
                    // Authentication successful
                    sessionManager.saveUserEmail(username)
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }else {
                    // Authentication failed
                    Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}