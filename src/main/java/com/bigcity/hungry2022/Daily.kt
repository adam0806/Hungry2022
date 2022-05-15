package com.bigcity.hungry2022

import android.content.Context
import com.bigcity.hungry2022.util.Preference
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class Daily {
    companion object{
        val PREF_KEY = "daily"
        val PLAN_LYING_FLAT = 0
        val PLAN_WORK = 1
        val PLAN_COOK = 2
        val PLAN_SHOPPING = 3
        val PLAN_PLAY_GAME = 4
        val PLAN_WATCH_TV = 5
        val PLAN_SOCIAL_NETWORK = 6
        val PLAN_EXERCISE = 7
        val PLAN_HANG_OUT = 8

        private var instance: Daily ?= null;
        fun getInstance(context: Context): Daily{
            if(instance == null){
                instance = Gson().fromJson(Preference.instance.getString(context, PREF_KEY), Daily::class.java)
                if(instance == null){
                    instance = Daily()
                }
            }
            return instance!!
        }
    }
    var nowTime = -1
    var morning_plan = PLAN_LYING_FLAT
    var afternoon_plan = PLAN_LYING_FLAT
    var evening_plan = PLAN_LYING_FLAT
    var day = 1
    fun save(context: Context){
        Preference.instance.putString(context, PREF_KEY, Gson().toJson(getInstance(context)))
    }
    fun clear(context: Context){
        Preference.instance.putString(context, PREF_KEY, "")
        instance = null
    }
}