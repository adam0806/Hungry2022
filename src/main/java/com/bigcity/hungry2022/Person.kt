package com.bigcity.hungry2022

import android.content.Context
import androidx.annotation.Nullable
import com.bigcity.hungry2022.util.Preference
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class Person: Cloneable{
    companion object{
        val PREF_KEY = "person"
        val CAREER_OPERATOR = 0
        val CAREER_OFFICE = 1
        val CAREER_ENGINEER = 2
        val CAREER_DELIVERY = 3
        val MAX_BLOOD = 100
        val MAX_MOOD = 100
        val GIFT_DIST_FREE = 1
        val GIFT_BUY = 2
        val GIFT_BUY_MID = 3
        val GIFT_BUY_BIG = 4
        val SALARY_HALF = 90
        val FIRE = 95

        lateinit var TOTAL_ADDICTION_LIST: MutableList<Good>
        lateinit var TOTAL_EXPENSIVE_LIST: MutableList<Good>
        lateinit var TOTAL_VEGETABLE_LIST: MutableList<Good>
        lateinit var TOTAL_GOOD_LIST: MutableList<Good>
        private var context: Context ?= null
        private var instance: Person ?= null;
        fun getInstance(context: Context): Person{
            this.context = context
            if(instance == null){
                instance = Gson().fromJson(Preference.instance.getString(context!!, PREF_KEY), Person::class.java)
                if(instance == null){
                    instance = Person()
                }
                initList()
            }
            return instance!!
        }
        fun initList(){
            TOTAL_ADDICTION_LIST = Arrays.asList(
                Good(context!!.getString(R.string.coke), 5, 10, 3,90, true),
                Good(context!!.getString(R.string.coffee), 5, 10, 20, 90, true),
                Good(context!!.getString(R.string.liquor), 5, 10, 6,90, true),
                Good(context!!.getString(R.string.cigarette), -5, 15, 12,180, true),
            )
            TOTAL_EXPENSIVE_LIST = Arrays.asList(
                Good(context!!.getString(R.string.beef), 10, 5, 40, 30, true),
                Good(context!!.getString(R.string.pork), 8, 5, 17, 30, true),
                Good(context!!.getString(R.string.chicken), 8, 5, 12,30, true),
                Good(context!!.getString(R.string.fish), 8, 5, 26,30, true),
                Good(context!!.getString(R.string.shrimp), 8, 5, 35, 30, true),
                Good(context!!.getString(R.string.dumpling), 10, 5, 19,30, true),
                Good(context!!.getString(R.string.instant_noodle), 10, 3,5, 180, true),
                Good(context!!.getString(R.string.oil), 5, 5, 21,180, true),
                Good(context!!.getString(R.string.salt), 5, 5, 9, 180, true),
            )
            TOTAL_VEGETABLE_LIST = Arrays.asList(
                Good(context!!.getString(R.string.potato), 5, 0, 1, 4, true),
                Good(context!!.getString(R.string.tomato), 5, 0, 2, 4, true),
                Good(context!!.getString(R.string.carrot), 5, 0, 1, 5, true),
                Good(context!!.getString(R.string.daikon), 5, 0, 1, 5, true),
                Good(context!!.getString(R.string.onion), 5, 0, 1, 4, true),
                Good(context!!.getString(R.string.cabbage), 5, 0, 1, 3, true),
                Good(context!!.getString(R.string.cucumbers), 5, 0, 2, 5, true),
                Good(context!!.getString(R.string.rice), 8, 0, 50,30, true),
            )
            TOTAL_GOOD_LIST = Arrays.asList(
                Good(context!!.getString(R.string.toilet_paper), 0, 5, 10, 180, false),
                Good(context!!.getString(R.string.medicine), 10, 5, 25, 180, false),
                Good(context!!.getString(R.string.alcohol), 5, 5, 8, 180, false),
                Good(context!!.getString(R.string.mask), 5, 5, 13, 180, false),
            )
        }
    }

    fun getGift(type: Int): MutableList<Good>{
        var list: MutableList<Good> = ArrayList()
        var basic = 1
        var bonus = 1
        when(type){
            GIFT_DIST_FREE->{
                basic = 3
                bonus = 3
                var size = basic + if(bonus != 0) Random().nextInt(bonus) else 0
                for(i in 0 until size) {
                    var index = Random().nextInt(TOTAL_VEGETABLE_LIST.size)
                    list.add(foodlist.get(index))
                }

                basic = 0
                bonus = 2
                size = basic + if(bonus != 0) Random().nextInt(bonus) else 0
                for(i in 0 until size) {
                    var index = Random().nextInt(TOTAL_EXPENSIVE_LIST.size)
                    list.add(foodlist.get(index))
                }
            }
            GIFT_BUY->{
                basic = 5
                bonus = 3
                var size = basic + if(bonus != 0) Random().nextInt(bonus) else 0
                for(i in 0 until size) {
                    var index = Random().nextInt(TOTAL_VEGETABLE_LIST.size)
                    list.add(foodlist.get(index))
                }

                basic = 1
                bonus = 2
                size = basic + if(bonus != 0) Random().nextInt(bonus) else 0
                for(i in 0 until size) {
                    var index = Random().nextInt(TOTAL_EXPENSIVE_LIST.size)
                    list.add(foodlist.get(index))
                }
            }
            GIFT_BUY_MID->{
                basic = 6
                bonus = 4
                var size = basic + if(bonus != 0) Random().nextInt(bonus) else 0
                for(i in 0 until size) {
                    var index = Random().nextInt(TOTAL_VEGETABLE_LIST.size)
                    list.add(foodlist.get(index))
                }

                basic = 2
                bonus = 1
                size = basic + if(bonus != 0) Random().nextInt(bonus) else 0
                for(i in 0 until size) {
                    var index = Random().nextInt(TOTAL_EXPENSIVE_LIST.size)
                    list.add(foodlist.get(index))
                }
            }
            GIFT_BUY_BIG->{
                basic = 10
                bonus = 6
                var size = basic + if(bonus != 0) Random().nextInt(bonus) else 0
                for(i in 0 until size) {
                    var index = Random().nextInt(TOTAL_VEGETABLE_LIST.size)
                    list.add(foodlist.get(index))
                }

                basic = 3
                bonus = 3
                size = basic + if(bonus != 0) Random().nextInt(bonus) else 0
                for(i in 0 until size) {
                    var index = Random().nextInt(TOTAL_EXPENSIVE_LIST.size)
                    list.add(foodlist.get(index))
                }
            }
            else->{
                var basic = 1
                var bonus = 4
                var size = basic + if(bonus != 0) Random().nextInt(bonus) else 0
                for(i in 0 until size) {
                    var index = Random().nextInt(TOTAL_VEGETABLE_LIST.size)
                    list.add(foodlist.get(index))
                }

                basic = 0
                bonus = 1
                size = basic + if(bonus != 0) Random().nextInt(bonus) else 0
                for(i in 0 until size) {
                    var index = Random().nextInt(TOTAL_EXPENSIVE_LIST.size)
                    list.add(foodlist.get(index))
                }
            }
        }
        return list
    }

    var rent_pay = 0
    var blood = 0
    var mood = 0
    var salary = 0
    var salary_half = false
    var fired = false
    var money = 0
    var buyPrice = 0
    var cook = 1
    var foodlist: MutableList<Good> = ArrayList()
    var chooseFoodList: MutableList<Good> = ArrayList()
    var goodlist: MutableList<Good> = ArrayList()
    var waitGoodList: MutableList<Good> = ArrayList()
    var roommate_spouse = 0
    var roommate_kid = 0
    var roommate_parent = 0
    var career = CAREER_OPERATOR
    var initFinish = 0
    var init: Person ?= null

    fun lyingFlat(): String{
        var str = ""
        var blood_modify = 0
        var mood_modify = 0
        blood_modify = (1 + Random().nextInt(1))
        mood_modify = (4 + Random().nextInt(4))
        str = context!!.getString(R.string.lying_good)
        blood += blood_modify
        mood += mood_modify
        return str + "\n\n" + getBloodStr(blood_modify) +", " + getMoodStr(mood_modify)
    }
    fun work(): String{
        var str = ""
        var i = Random().nextInt(100)
        var blood_modify = 0
        var mood_modify = 0
        var salary_modify = 0
        if(!fired && i > FIRE){
            blood_modify = -5
            mood_modify = -30
            salary_modify = -salary
            str = context!!.getString(R.string.fired)
        }else if(!salary_half && i > SALARY_HALF){
            blood_modify = -5
            mood_modify = -20
            salary_modify = -salary/2
            str = context!!.getString(R.string.salary_half)
        }else{
            blood_modify = -5
            mood_modify = -5
            str = context!!.getString(R.string.work_normal)
        }
        blood += blood_modify
        mood += mood_modify
        salary += salary_modify
        return str + "\n\n" + getBloodStr(blood_modify) +", " + getMoodStr(mood_modify) +", " + getSalaryStr(salary_modify)
    }
    private fun addCook(): Int{
        if(Random().nextInt(10) == 10){
            if(cook + 1 <= 3){
                cook++
                return 1
            }
        }
        return 0
    }
    fun cook(): String{
        if(chooseFoodList.isEmpty()){
            return context!!.getString(R.string.no_food)
        }
        var str = ""
        var blood_modify = 0
        var mood_modify = 0
        for(i in 0 until chooseFoodList.size){
            var food = chooseFoodList.get(i)
            if(food.date > 0) {
                blood_modify += (food.blood + cook)
                mood_modify += (food.mood + cook)
                str = context!!.getString(R.string.tasty)
            }else{
                blood_modify -= 5
                mood_modify -= 5
                str = context!!.getString(R.string.spoil)
            }
            foodlist.remove(food)
        }
        blood += blood_modify
        mood += mood_modify
        chooseFoodList.clear()
        return str + "\n\n" + getBloodStr(blood_modify) +", " + getMoodStr(mood_modify) + getCookSkillStr(addCook())
    }
    fun playGame(): String{
        var str = ""
        var mood_modify = 0
        mood_modify = (8 + Random().nextInt(5))
        mood += mood_modify
        str = context!!.getString(R.string.game_win)
        return str + "\n\n" + getMoodStr(mood_modify)
    }
    fun watchTV(): String{
        var str = ""
        var mood_modify = 0
        var i = Random().nextInt(100)
        if(i > 90){
            str = context!!.getString(R.string.tv_positive_increase)
            mood_modify = (7 + Random().nextInt(5))
        }else if(i > 80){
            str = context!!.getString(R.string.tv_positive_virus)
            mood_modify = (10 + Random().nextInt(5))
        }else if(i > 70){
            str = context!!.getString(R.string.tv_positive_tech)
            mood_modify = (8 + Random().nextInt(5))
        }else if(i > 60){
            str = context!!.getString(R.string.tv_positive_good_food)
            mood_modify = (4 + Random().nextInt(3))
        }else{
            mood_modify = (5 + Random().nextInt(5))
            str = context!!.getString(R.string.tv_normal)
        }
        mood += mood_modify
        return str + "\n\n" + getMoodStr(mood_modify)
    }
    fun socialNetwork(): String{
        var str = ""
        var mood_modify = 0
        var i = Random().nextInt(100)
        if(i > 90){
            str = context!!.getString(R.string.tv_group_food)
            mood_modify = -(5 + Random().nextInt(5))
        }else if(i > 80){
            str = context!!.getString(R.string.tv_expensive_food)
            mood_modify = -(5 + Random().nextInt(5))
        }else if(i > 70){
            str = context!!.getString(R.string.tv_sell_food)
            mood_modify = -(10 + Random().nextInt(5))
        }else if(i > 60){
            str = context!!.getString(R.string.tv_no_food)
            mood_modify = -(10 + Random().nextInt(5))
        }else if(i > 50){
            str = context!!.getString(R.string.tv_broken_hospital)
            mood_modify = -(5 + Random().nextInt(5))
        }else if(i > 40){
            str = context!!.getString(R.string.tv_trigger)
            mood_modify = -(10 + Random().nextInt(5))
        }else if(i > 30){
            str = context!!.getString(R.string.tv_cook_game)
            mood_modify = -(5 + Random().nextInt(5))
        }else if(i > 20){
            str = context!!.getString(R.string.tv_concert)
            mood_modify = -(5 + Random().nextInt(5))
        }else if(i > 10){
            str = context!!.getString(R.string.tv_leader)
            mood_modify = -(5 + Random().nextInt(5))
        }else {
            str = context!!.getString(R.string.tv_increase)
            mood_modify = -(5 + Random().nextInt(5))
        }
        mood += mood_modify
        return str + "\n\n" + getMoodStr(mood_modify)
    }
    fun exercise(): String{
        var str = ""
        var blood_modify = 0
        var mood_modify = 0
        blood_modify = (2 + Random().nextInt(2))
        mood_modify = (3 + Random().nextInt(3))
        str = context!!.getString(R.string.exercise_normal)
        blood += blood_modify
        mood += mood_modify
        return str + "\n\n" + getBloodStr(blood_modify) +", " + getMoodStr(mood_modify)
    }
    private fun getBloodStr(value: Int): String{
        return getValueStr(context!!.getString(R.string.blood), value)
    }
    private fun getMoodStr(value: Int): String{
        return getValueStr(context!!.getString(R.string.mood), value)
    }
    private fun getSalaryStr(value: Int): String{
        return getValueStr(context!!.getString(R.string.salary), value)
    }
    private fun getCookSkillStr(value: Int): String{
        return getValueStr(context!!.getString(R.string.cook_skill), value)
    }
    private fun getValueStr(base: String, value: Int): String{
        var str = ""
        if(value == 0){
            return ""
        }else if(value > 0){
            str = "+$value"
        }else{
            str = "$value"
        }
        return base + str
    }

    fun save(){
        Preference.instance.putString(context!!, PREF_KEY, Gson().toJson(getInstance(context!!)))
    }
    fun calculateDefaultValue(){
        calculateBloodAndMoney()
        calculateMood()
        init = Gson().fromJson(Gson().toJson(getInstance(context!!)), Person::class.java)
        initFinish = 1
    }
    private fun calculateBloodAndMoney(){
        var min = 0
        var mon_basic = 0
        var mon_random = 0
        if(career == CAREER_OPERATOR){
            min = 80
            mon_basic = 3000
            mon_random = 1000
        }else if(career == CAREER_OFFICE){
            min = 70
            mon_basic = 5000
            mon_random = 2000
        }else if(career == CAREER_ENGINEER){
            min = 60
            mon_basic = 5000
            mon_random = 15000
        }else if(career == CAREER_DELIVERY){
            min = 90
            mon_basic = 5000
            mon_random = 5000
        }

        rent_pay = 600 + mon_basic/5 + Random().nextInt(mon_random/5)
        blood = min + Random().nextInt(MAX_BLOOD - min)
        salary = mon_basic + Random().nextInt(mon_random)
        salary = (salary/100) * 100
        money = mon_basic/2 + Random().nextInt(mon_random/2)
        money = (money/100) * 100
    }
    private fun calculateMood(){
        var min = 60
        if(roommate_spouse == 1){
            min += 10
        }
        if(roommate_kid == 1){
            min += 10
        }
        if(roommate_parent == 1){
            min += 10
        }
        mood = min + Random().nextInt(MAX_MOOD - min)
    }
    fun pay(){
        money -= buyPrice
        buyPrice = 0
    }
    fun dayPassSafe(): Int{
        var member = 1
        var perMemberDayEat = 20
        if(roommate_spouse == 1){
            member++
        }
        if(roommate_kid == 1){
            member++
        }
        if(roommate_parent == 1){
            member+= 2
        }
        blood -= (member * perMemberDayEat)
        mood -= 5
        if(blood < 1){
            return 1
        }else if(mood < 1){
            return 2
        }else{
            return 3
        }//todo 決定遊戲失敗及文字
    }
    fun replay(){
        instance = init
        save()
    }
    fun clear(){
        Preference.instance.putString(context!!, PREF_KEY, "")
        instance = null
    }
}
open class Good(name: String, blood: Int, mood: Int, price: Int, date: Int, isFood: Boolean){
    var name = name
    var blood = blood
    private var priceNormal = price
    var mood = mood
    var date = date
    var isFood = isFood

    @Nullable
    var select = false
    @Nullable
    var select_volume = 1

    companion object {
        var type = 0
        var volume_limit = 1
        var times = 1
        open fun setPriceTimes(type: Int){
            this.type = type
            times =  (3 + Random().nextInt(type))
        }
    }
    fun getPrice(): Int{
        var price = priceNormal * times
        if(price < 10){
            if(price * 2 < 10){
                price = 10
            }else{
                price *= 2
            }
        }
        return price
    }
    fun setPriceNormal(priceNormal: Int){
        this.priceNormal = priceNormal
    }
    open fun initVolumeLimit(){
        volume_limit =  (1 + Random().nextInt(1 + type))
    }
    open fun setVolumeLimit(limit: Int){
        volume_limit = limit
    }
    fun getVolumeLimit(): Int{
        return volume_limit
    }
    fun clone(): Good {
        return Gson().fromJson(Gson().toJson(this), Good::class.java)!!
    }

    override fun equals(other: Any?): Boolean {
        if(other !is Good){
            return false
        }
        if(other.name != name){
            return false
        }
        return true
    }
}