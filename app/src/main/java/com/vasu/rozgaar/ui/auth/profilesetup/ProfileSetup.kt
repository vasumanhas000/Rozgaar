package com.vasu.rozgaar.ui.auth.profilesetup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.RadioButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.vasu.rozgaar.R


class ProfileSetup : Fragment() {
    private lateinit var radioYes : RadioButton
    private lateinit var radioNo : RadioButton
    private lateinit var nameEditText: TextInputEditText
    private lateinit var pinCodeEditText: TextInputEditText
    private lateinit var organizationEditText : TextInputEditText
    private lateinit var organizationEditTextLayout : TextInputLayout
    private lateinit var submitProfileButton : Button

    private var PROFILE_SET_UP_FRAG = "profilesetupfrag"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_set_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViewByID(view)
        radioListener()
        submitProfileButton.setOnClickListener{
            var name = nameEditText.text.toString().trim()
            var pinCode = pinCodeEditText.text.toString().trim()
            var isOrganization = radioYes.isChecked
            Log.i(PROFILE_SET_UP_FRAG,isOrganization.toString())
            var organization = if(isOrganization) organizationEditText.text.toString().trim() else ""
            if(checkInput(name, pinCode, isOrganization)){
                Log.i(PROFILE_SET_UP_FRAG,"Input verified")
            }
        }
    }

    private fun findViewByID(view: View){
        radioYes = view.findViewById(R.id.radio_yes)
        radioNo = view.findViewById(R.id.radio_no)
        nameEditText = view.findViewById(R.id.name_text_input)
        pinCodeEditText = view.findViewById(R.id.pincode_text_input)
        organizationEditText = view.findViewById(R.id.org_name_text_input)
        organizationEditTextLayout = view.findViewById(R.id.org_text_input_layout)
        submitProfileButton = view.findViewById(R.id.submit_profile_btn)
    }

    private fun radioListener(){
      radioNo.setOnCheckedChangeListener{ _, isChecked ->
          if(isChecked){
              Log.i(PROFILE_SET_UP_FRAG,"in no")
             organizationEditTextLayout.isEnabled = false
          }
      }
      radioYes.setOnCheckedChangeListener{_,isChecked ->
          if(isChecked){
              Log.i(PROFILE_SET_UP_FRAG,"in yes")
              organizationEditTextLayout.isEnabled = true
          }
      }
    }

    private fun checkInput(name : String,pinCode: String,isOrganization:Boolean):Boolean{
        var flag : Number = 1
        if(name.isEmpty()){
            flag=-1
            nameEditText.error = "Name cannot be empty"
        }
        if(!checkRegex(pinCode)){
            flag=-1
        }
        if(isOrganization){
            var org : String = organizationEditText.text.toString().trim()
            if(org.isEmpty()){
                flag=-1
                organizationEditText.error = "Organization name cannot be empty"
            }
        }
        return flag != -1
    }

    private fun checkRegex(pinCode : String) : Boolean{
        val pattern = Regex("^[1-9][0-9]{5}$")
        if(pattern.containsMatchIn(pinCode)){
            return true
        }else{
            pinCodeEditText.error = "Invalid pincode"
            return false
        }
    }

}