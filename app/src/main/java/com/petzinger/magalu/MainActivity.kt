package com.petzinger.magalu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.petzinger.magalu.di.DaggerAppComponent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerAppComponent.create().inject(this)
    }
}