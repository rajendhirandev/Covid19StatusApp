package com.practice.covid19.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practice.covid19.R
import com.practice.covid19.databinding.ActivityHomeBinding
import com.practice.covid19.screens.status.AcronymFragment

class HomeActivity : AppCompatActivity() {
    lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AcronymFragment.newInstance()).commitNow()
        }
    }
}
