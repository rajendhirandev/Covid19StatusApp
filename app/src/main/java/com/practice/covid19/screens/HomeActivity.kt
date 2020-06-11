package com.practice.covid19.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practice.covid19.R
import com.practice.covid19.screens.status.HomeCovidFragment

/*
Author: Rajendhiran Easu
Date: 24-Apr-20
*/
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeCovidFragment.newInstance()).commitNow()
        }
    }
}
