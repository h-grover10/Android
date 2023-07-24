package com.example.user_login_page

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * Screen to show to user for Login
 * After a successful Login, navigate the user to [LogoutFragment]
 * Also provides an entry point for User Signup
 */
class LoginFragment : Fragment() {
    // Store the user details in the [SharedPreferences]
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("ResourceType", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences =
            requireActivity().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        val loginBtn: Button = view.findViewById(R.id.btn_login)
        // If the user is in [SharedPreferences], navigate to [LogoutFragment] else to [SignUpFragment]
        loginBtn.setOnClickListener {
            val userPassword = sharedPreferences.getString(
                view.findViewById<EditText>(R.id.et_name).text.toString().trim(), ""
            )
            val enteredPassword =
                view.findViewById<EditText>(R.id.et_password).text.toString().trim()
            if (userPassword == enteredPassword) {
                findNavController().navigate(R.id.action_fragmentLogin_to_fragmentLogout)
            } else {
                Toast.makeText(
                    context,
                    "Please enter a valid Username or Password",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        val signUpBtn: Button = view.findViewById(R.id.btn_sign_up)
        signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentSignUp)
        }
        return view
    }
}
