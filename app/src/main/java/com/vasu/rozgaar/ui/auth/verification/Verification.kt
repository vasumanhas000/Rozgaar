package com.vasu.rozgaar.ui.auth.verification

import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.vasu.rozgaar.R
import com.vasu.rozgaar.data.network.RetrofitService
import com.vasu.rozgaar.data.repository.AuthRepository
import java.util.concurrent.TimeUnit
import kotlin.math.sign

class Verification : Fragment() {

    private lateinit var phone : String
    private val VERIFICATION_FRAG = "verificationfrag"
    private var verificationToken :String? = null
    private lateinit var resendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var otpEditText : TextInputEditText
    private lateinit var submitOtpButton: Button
    private lateinit var viewModel: VerificationViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var navController: NavController
    private lateinit var progressBar : CircularProgressIndicator
    private lateinit var authorizationToken : String
    private var flag : Number =1;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        findViewByID(view)
        phone = arguments?.getString("phoneNumber") ?: " "
        Log.i("tag",phone)
        initiateSignIn(phone)
        getAuthResponse()
        submitOtpButton.setOnClickListener{
            if(verificationToken!=null && flag!=-1){
                flag=-1
            disableUI()
            verifyPhoneNumberWithCode(otpEditText.text.toString().trim()!!)
        }}
    }

    private fun userListener(){
        viewModel.firebaseUser.observe(viewLifecycleOwner,{
            if(it!=null){

              it.getIdToken(true).addOnCompleteListener{task->
                    if(task.isComplete){
                        authorizationToken = task.result?.token.toString()
                        Log.i(VERIFICATION_FRAG,"The generated token :"+authorizationToken)
                        userProfileListener()
                        var headers : Map<String,String> = mapOf("Authorization" to "Bearer $authorizationToken")
                        checkUser(headers)
                    }
                }

            }
        })
    }

    private fun userProfileListener(){
        viewModel.profileCreated.observe(viewLifecycleOwner,{
            if(it){
                Log.i(VERIFICATION_FRAG,"Profile Created - true")
            }else{
                var bundle = bundleOf("authorizationToken" to authorizationToken,"phone" to phone)
                navController.navigate(R.id.action_verification_to_profileSetup,bundle)
                Log.i(VERIFICATION_FRAG,"Profile Created - false")
            }
        })
    }
    private fun checkUser(headers:Map<String,String>){
        viewModel.checkUser(headers)
    }
    private fun getAuthResponse(){
        viewModel.response.observe(viewLifecycleOwner, {
            if(it){
//                check if user exists or not
                Toast.makeText(context,"Valid OTP!",Toast.LENGTH_LONG).show()
                userListener()
                viewModel.getCurrentUser()
            }else{
                flag = 1
                enableUI()
                Toast.makeText(context,"Invalid OTP!",Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun initiateSignIn(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder().setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun verifyPhoneNumberWithCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationToken!!, code)
        signInWithFirebase(credential)
    }

    private fun signInWithFirebase(credential: PhoneAuthCredential){
        viewModel.signInWithFirebase(credential)
    }

    private fun findViewByID(view: View){
        otpEditText = view.findViewById(R.id.otp_text_input)
        submitOtpButton=view.findViewById(R.id.submit_otp_btn)
        navController = Navigation.findNavController(view)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this,VerificationViewModelFactory(AuthRepository(retrofitService))).get(VerificationViewModel::class.java)
    }

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            Log.d(VERIFICATION_FRAG, "onVerificationCompleted:$credential")
            signInWithFirebase(credential)

        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(VERIFICATION_FRAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }

        }
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(VERIFICATION_FRAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            verificationToken =verificationId
            resendingToken = token
        }
    }

    private fun disableUI(){
        progressBar.visibility= View.VISIBLE
        submitOtpButton.isEnabled=false
        otpEditText.isEnabled=false
    }

    private fun enableUI(){
        progressBar.visibility= View.INVISIBLE
        submitOtpButton.isEnabled=true
        otpEditText.isEnabled=true
    }
}