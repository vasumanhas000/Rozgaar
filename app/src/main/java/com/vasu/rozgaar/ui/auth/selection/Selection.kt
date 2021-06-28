package com.vasu.rozgaar.ui.auth.selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.vasu.rozgaar.R


class Selection : Fragment() {


    private lateinit var navController: NavController
    private lateinit var employerButton:Button
    private lateinit var organizationButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViewByID(view)
        navController = Navigation.findNavController(view)
        val action = SelectionDirections.actionSelectionToSignIn()
        employerButton.setOnClickListener{
            navController.navigate(action)
        }
        organizationButton.setOnClickListener{
            navController.navigate(action)
        }
    }

    fun findViewByID(view:View){
        employerButton = view.findViewById(R.id.employer_login_btn)
        organizationButton = view.findViewById(R.id.org_login_btn)
    }

}