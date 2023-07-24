package com.example.user_login_page

import androidx.fragment.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.json.JSONObject
import java.io.IOException

/**
 * Screen to show to the user for a fresh SignUp
 * After a successful SignUp, navigate the user to [LogoutFragment]
 */
class SignUpFragment : Fragment() {

    // Store the new Signed Up users in the [SharedPreferences]
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences =
            requireActivity().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        val signUpBtn: Button = view.findViewById(R.id.btn_sign_up)
        // Populate the spinner
        val countrySpinner: Spinner = view.findViewById(R.id.country_spinner)
        countrySpinner.populateSpinner(fetchDataFromAssetsAndPopulateSpinner())

        // Check for the validity of User entered details
        signUpBtn.setOnClickListener {
            val enteredPassword =
                view.findViewById<EditText>(R.id.et_password).text.toString().trim()
            val enteredName = view.findViewById<EditText>(R.id.et_name).text.toString().trim()
            if (!isPasswordValid(enteredPassword)) {
                view.findViewById<TextView>(R.id.error_msg).visibility = View.VISIBLE
            }
            if (!isNameValid(enteredName)) {
                Toast.makeText(context, "Please enter a valid name", Toast.LENGTH_LONG).show()
            }
            // Add the user details to the shared preferences & take the user to Logout Screen
            if (isNameValid(enteredName) && isPasswordValid(enteredPassword)) {
                val editor = sharedPreferences.edit()
                // Storing the Username & Password in the SharedPref here
                editor.putString(enteredName, enteredPassword)
                editor.apply()
                findNavController().navigate(R.id.action_fragmentSignUp_to_fragmentLogout)
            }
        }
        return view
    }

    /**
     * Function to check if the entered Password is valid or not
     */
    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[!@#\$%&()])(?=.*[a-z])(?=.*[A-Z]).{8,}$".toRegex()
        return password.matches(passwordPattern)
    }

    /**
     * Function to check if the entered name is valid or not
     */
    private fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    /**
     * Fetch the data from the Json file in Assets & populate the spinner
     */
    private fun fetchDataFromAssetsAndPopulateSpinner(): List<String> {
        val countryJsonObject: JSONObject
        try {
            val fileName = "Countries.json"
            val jsonString: String =
                context!!.assets.open(fileName).bufferedReader().use { it.readText() }
            countryJsonObject = JSONObject(jsonString)

            val countryList = mutableListOf<String>()

            countryJsonObject.let {
                val dataObject = it.optJSONObject("data")
                dataObject?.let { data ->
                    val keysIterator = data.keys()
                    while (keysIterator.hasNext()) {
                        val countryCode = keysIterator.next()
                        val countryObject = data.optJSONObject(countryCode)
                        val country = countryObject?.optString("country")
                        country?.let { countryName ->
                            countryList.add(countryName)
                        }
                    }
                }
            }
            return countryList
        } catch (e: IOException) {
            e.printStackTrace()
            return emptyList()
        }
    }

    /**
     * Function to populate the Spinner with country names
     */
    private fun Spinner.populateSpinner(countryNames: List<String>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, countryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.adapter = adapter
    }
}
