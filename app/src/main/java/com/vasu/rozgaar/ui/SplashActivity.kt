package com.vasu.rozgaar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.vasu.rozgaar.R
import com.vasu.rozgaar.ui.auth.AuthActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }
    private fun initialize(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
//      check user here
        val i = Intent(this,AuthActivity::class.java)
        startActivity(i)
        finish()
        },3000)
    }
}