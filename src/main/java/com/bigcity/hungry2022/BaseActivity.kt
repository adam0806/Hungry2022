package com.bigcity.hungry2022

import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.bigcity.hungry2022.util.Preference
import kotlinx.android.synthetic.main.activity_main.*

open class BaseActivity : AppCompatActivity() {
    fun startActivity(cls: Class<*>?){
        startActivity(Intent(this, cls))
    }
    fun putStr(key: String, str: String){
        Preference.instance.putString(this, key, str)
    }
    fun getStr(key: String, defStr: String){
        Preference.instance.getString(this, key, defStr)
    }
    fun addView(pageId: Int){
        root.addView(LayoutInflater.from(this).inflate(pageId,  root, false))
    }
}