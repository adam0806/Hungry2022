package com.bigcity.hungry2022.util

import android.content.Context
import android.content.SharedPreferences

class Preference {
    companion object{
        var instance : Preference = Preference()
    }
    private var pref : SharedPreferences?= null
    private fun getPref(context: Context): SharedPreferences{
        if(pref == null){
            pref =  context.getSharedPreferences("pref.ds", Context.MODE_PRIVATE)
        }
        return pref!!
    }
    fun putString(context: Context, key: String, string: String){
        getPref(context).edit().putString(key, string).commit()
    }
    fun getString(context: Context, key: String): String{
        return getString(context, key, "")
    }
    fun getString(context: Context, key: String, defaultString: String): String{
        return getPref(context).getString(key, defaultString)!!
    }
}