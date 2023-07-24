package com.example.user_login_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


/**
 * Screen to show the user after a Successful Signup or Login
 * This screen will have entry points to both [SignUpFragment] and [LoginFragment]
 */
class LogoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logout, container, false)
        val loginBtn: Button = view.findViewById(R.id.btn_login)
        val signUpBtn: Button = view.findViewById(R.id.btn_sign_up)
        loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLogout_to_fragmentLogin)
        }
        signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLogout_to_fragmentSignUp)
        }
        return view
    }
}
