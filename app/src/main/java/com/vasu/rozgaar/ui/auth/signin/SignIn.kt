package com.vasu.rozgaar.ui.auth.signin

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.vasu.rozgaar.R



class SignIn : Fragment() {

    lateinit var submitButton: Button
    lateinit var phoneNumberText:TextInputEditText
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViewByID(view)
        navController = Navigation.findNavController(view)
        submitButton.setOnClickListener{
            var number = phoneNumberText.text.toString().trim()
            if (validatePhoneNumber(number)){
            var bundle = bundleOf("phoneNumber" to number)
            navController.navigate(R.id.action_signIn_to_verification,bundle)
            }
        }
    }

    private fun validatePhoneNumber(phoneNumber : String): Boolean {
        if(phoneNumber.isNotEmpty()){
            val phoneNumeric = phoneNumber.substring(1 until phoneNumber.length)
            Log.d("Numeric","$phoneNumeric , $phoneNumber")
            if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length < 13 || phoneNumeric.toBigIntegerOrNull() == null) {
                phoneNumberText.error = "Invalid phone number."
                return false
            }
            return true
        }else{
            phoneNumberText.error = "Invalid phone number."
            return false
        }
    }

    fun findViewByID(view: View){
        submitButton = view.findViewById(R.id.submit_mobile_btn)
        phoneNumberText = view.findViewById(R.id.phone_text_input)
    }

}