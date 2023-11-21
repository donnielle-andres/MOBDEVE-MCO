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
import com.mobdeve.s11.mco.databinding.FragmentEditAccountBinding
import com.mobdeve.s11.mco.databinding.FragmentSignupBinding
import com.mobdeve.s11.mco.model.User

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditAccountFragment : Fragment() {

    private var _binding: FragmentEditAccountBinding? = null
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
        _binding = FragmentEditAccountBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**binding.buttonSecond.setOnClickListener {
         * findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
        val emailText = view.findViewById<TextInputLayout>(R.id.newEmailInput)
        val fullnameText = view.findViewById<TextInputLayout>(R.id.newNameInput)
        val numberText = view.findViewById<TextInputLayout>(R.id.newNumberInput)
        val email = sessionManager.getUserEmail()!!

        val usersDatabase = UsersDatabase(requireContext())
        val user = usersDatabase.getUser(email)

        emailText.setHint(email)
        if (user != null) {
            fullnameText.setHint(user.fullName)
            numberText.setHint(user.number)
        }
        binding.saveEditButton.setOnClickListener {
            var fullname = fullnameText.editText?.text.toString()
            var number = numberText.editText?.text.toString()

            if(TextUtils.isEmpty(number) && TextUtils.isEmpty(fullname)) {
                Toast.makeText(requireContext(), "No Changes Made!",
                    Toast.LENGTH_SHORT).show();
            }
            else{
                if(user!=null){
                    if(TextUtils.isEmpty(number)){
                        number = user.number
                    }
                    if(TextUtils.isEmpty(fullname)){
                        fullname = user.fullName
                    }
                    usersDatabase.updateUserByEmail(email,fullname,number)
                    val updatedUser = usersDatabase.getUser(email)
                    Toast.makeText(requireContext(), "Edit saved!", Toast.LENGTH_SHORT).show()
                    if (updatedUser != null) {
                        sessionManager.saveFullname(updatedUser.fullName)
                        sessionManager.saveUserNumber(updatedUser.number)
                        findNavController().navigate(R.id.profileFragment)

                    }
                    else{
                        Toast.makeText(requireContext(), "Error in database. User not found!",
                            Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(requireContext(), "Error here! User not in Database!",
                        Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}