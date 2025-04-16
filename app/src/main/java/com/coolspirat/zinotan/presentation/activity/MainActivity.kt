package com.coolspirat.zinotan.presentation.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

//        if (resources.configuration.smallestScreenWidthDp < 600) {
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        }

        setContentView(R.layout.activity_main)
    }

}