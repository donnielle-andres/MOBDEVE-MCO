package com.mobdeve.s11.mco

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.mobdeve.s11.mco.data.UsersDatabase
import com.mobdeve.s11.mco.databinding.FragmentSignupBinding
import com.mobdeve.s11.mco.model.User

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**binding.buttonSecond.setOnClickListener {
         * findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
        binding.signupButton.setOnClickListener {
            val usernameText = view.findViewById<TextInputLayout>(R.id.newUsernameInput)
            val passwordText = view.findViewById<TextInputLayout>(R.id.newPasswordInput)
            val numberText = view.findViewById<TextInputLayout>(R.id.newNumberInput)


            val username = usernameText.editText?.text.toString()
            val password = passwordText.editText?.text.toString()
            val number = numberText.editText?.text.toString()


            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(number)) {
                Toast.makeText(requireContext(), "Empty field not allowed!",
                    Toast.LENGTH_SHORT).show();
            }
            else{
                val usersDatabase = UsersDatabase(requireContext())
                val user = usersDatabase.getUser(username)
                if (user != null ) {
                    // Username found in db; Invalid Signup
                    Toast.makeText(requireContext(),"Username used already. Please use another Username!", Toast.LENGTH_SHORT).show()
                }else {
                    // Valid signup
                    val newUser = User(username,password,number)
                    usersDatabase.insertUser(newUser)
                    Toast.makeText(requireContext(), "Signup Successful!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signupFragment_to_Login)
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}