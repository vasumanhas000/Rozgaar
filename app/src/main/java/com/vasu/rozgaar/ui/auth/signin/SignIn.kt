package com.vasu.rozgaar.ui.auth.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vasu.rozgaar.R



class SignIn : Fragment() {

    lateinit var submitButton: Button
    lateinit var phone:EditText
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
            var number = phone.text.toString().trim()
            var bundle = bundleOf("phoneNumber" to number)
            navController.navigate(R.id.action_signIn_to_verification,bundle)
        }

    }

    fun findViewByID(view: View){
        submitButton = view.findViewById(R.id.submit_mobile_btn)
        phone = view.findViewById(R.id.phone_edit_text)
    }

}