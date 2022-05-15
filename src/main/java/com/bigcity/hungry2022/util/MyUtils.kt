package com.bigcity.hungry2022.util

import java.text.SimpleDateFormat
import java.util.*

class MyUtils {
    companion object{
        fun isInteger(s: String): Boolean {
            return if (s != null && "" != s.trim { it <= ' ' }) {
                s.matches(Regex("^[0-9]*$"))
            } else false
        }
        fun getDateFormat(date: Date, format: String): String{
            var formatter = SimpleDateFormat(format, Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
            return formatter.format(date)
        }
    }
}