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
import com.mobdeve.s11.mco.databinding.FragmentChangePasswordBinding
import com.mobdeve.s11.mco.databinding.FragmentEditAccountBinding
import com.mobdeve.s11.mco.databinding.FragmentSignupBinding
import com.mobdeve.s11.mco.model.User

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
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
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**binding.buttonSecond.setOnClickListener {
         * findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
        val oldPasswordText = view.findViewById<TextInputLayout>(R.id.oldPasswordInput)
        val newPasswordText = view.findViewById<TextInputLayout>(R.id.newPasswordInput)

        val email = sessionManager.getUserEmail()!!

        val usersDatabase = UsersDatabase(requireContext())
        val user = usersDatabase.getUser(email)

        binding.savePasswordButton.setOnClickListener {
            var oldPasswordInput = oldPasswordText.editText?.text.toString()
            var newPasswordInput = newPasswordText.editText?.text.toString()

            val storedOldPassword = user?.password

            if(TextUtils.isEmpty(oldPasswordInput) || TextUtils.isEmpty(newPasswordInput)){
                Toast.makeText(requireContext(), "Empty fields not allowed!",
                    Toast.LENGTH_SHORT).show();
            }
            else{
                if(storedOldPassword != oldPasswordInput) {
                    Toast.makeText(requireContext(), "Old password does not match with current password!",
                        Toast.LENGTH_SHORT).show();
                }
                else{
                    if(storedOldPassword == newPasswordInput){
                        Toast.makeText(requireContext(), "Please use a new password!",
                            Toast.LENGTH_SHORT).show();
                    }else{
                        usersDatabase.updateUserPassword(email,newPasswordInput)
                        Toast.makeText(requireContext(), "Password saved!",
                            Toast.LENGTH_SHORT).show();
                        findNavController().navigate(R.id.profileFragment)
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}