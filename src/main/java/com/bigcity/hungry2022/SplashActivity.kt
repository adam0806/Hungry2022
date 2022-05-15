package com.bigcity.hungry2022

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ll_root.setOnClickListener{
            startActivity(MainActivity::class.java)
            finish()
        }
    }
}