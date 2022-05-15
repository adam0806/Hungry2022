package com.bigcity.hungry2022

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adam.lib.util.AlertUtils
import com.adam.lib.util.AlertUtils.context
import com.adam.lib.util.TimeUtils
import com.bigcity.hungry2022.Daily.Companion.PLAN_COOK
import com.bigcity.hungry2022.Daily.Companion.PLAN_EXERCISE
import com.bigcity.hungry2022.Daily.Companion.PLAN_HANG_OUT
import com.bigcity.hungry2022.Daily.Companion.PLAN_PLAY_GAME
import com.bigcity.hungry2022.Daily.Companion.PLAN_SHOPPING
import com.bigcity.hungry2022.Daily.Companion.PLAN_SOCIAL_NETWORK
import com.bigcity.hungry2022.Daily.Companion.PLAN_WATCH_TV
import com.bigcity.hungry2022.Daily.Companion.PLAN_WORK
import com.bigcity.hungry2022.Person.Companion.CAREER_DELIVERY
import com.bigcity.hungry2022.Person.Companion.CAREER_ENGINEER
import com.bigcity.hungry2022.Person.Companion.CAREER_OFFICE
import com.bigcity.hungry2022.Person.Companion.CAREER_OPERATOR
import com.bigcity.hungry2022.Person.Companion.GIFT_BUY
import com.bigcity.hungry2022.Person.Companion.GIFT_BUY_BIG
import com.bigcity.hungry2022.Person.Companion.GIFT_BUY_MID
import com.bigcity.hungry2022.Person.Companion.GIFT_DIST_FREE
import com.bigcity.hungry2022.util.MyUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_shopping.view.*
import kotlinx.android.synthetic.main.view_day_common.*
import kotlinx.android.synthetic.main.view_day_plan.tv_select_plan
import kotlinx.android.synthetic.main.view_day_time.tv_time
import kotlinx.android.synthetic.main.view_good_list.*
import kotlinx.android.synthetic.main.view_good_list.view.*
import kotlinx.android.synthetic.main.view_introduction.*
import kotlinx.android.synthetic.main.view_main.*
import kotlinx.android.synthetic.main.view_main_basic.*
import kotlinx.android.synthetic.main.view_select_career.*
import kotlinx.android.synthetic.main.view_select_roomate.*
import kotlinx.android.synthetic.main.view_shopping.*
import kotlinx.android.synthetic.main.view_shopping.btn_shopping_confirm
import kotlinx.android.synthetic.main.view_shopping_list.*
import kotlinx.android.synthetic.main.view_shopping_list.rv
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AlertUtils.init(this)
        initGame()
    }
    private fun initGame(){
        if(Person.getInstance(this).initFinish == 1) {
            initPageMain()
        }else{
            initPage1()
        }
    }
    //遊戲背景
    private fun initPage1(){
        addView(R.layout.view_introduction)
        tv_introduction.setOnClickListener {
            if(tv_introduction.tag == "1"){
                tv_introduction.text = getString(R.string.introduction_2)
                tv_introduction.tag = "2"
            }else if(tv_introduction.tag == "2"){
                tv_introduction.text = getString(R.string.introduction_3)
                tv_introduction.tag = "3"
            }else if(tv_introduction.tag == "3"){
                tv_introduction.text = getString(R.string.introduction_4)
                tv_introduction.tag = "4"
            }else {
                root.removeAllViews()
                initPage2()
            }

        }
    }
    //選職業
    private fun initPage2(){
        addView(R.layout.view_select_career)
        btn_career_delivery_clerk.setOnClickListener{
            Person.getInstance(this).career = CAREER_DELIVERY
        }
        btn_career_office_clerk.setOnClickListener {
            Person.getInstance(this).career = CAREER_OFFICE
        }
        btn_career_operator.setOnClickListener {
            Person.getInstance(this).career = CAREER_OPERATOR
        }
        btn_career_software_engineer.setOnClickListener {
            Person.getInstance(this).career = CAREER_ENGINEER
        }
        btn_career_delivery_clerk.performClick()

        btn_career_confirm.setOnClickListener {
            Person.getInstance(this).save()
            root.removeAllViews()
            initPage3()
        }
    }
    //選室友
    private fun initPage3(){
        addView(R.layout.view_select_roomate)
        btn_roommate_spouse.setOnClickListener{
            Person.getInstance(this).roommate_spouse = 1
        }
        btn_roommate_kid.setOnClickListener {
            Person.getInstance(this).roommate_spouse = 1
            Person.getInstance(this).roommate_kid = 1
        }
        btn_roommate_parent.setOnClickListener {
            Person.getInstance(this).roommate_spouse = 1
            Person.getInstance(this).roommate_kid = 1
            Person.getInstance(this).roommate_parent = 1
        }
        btn_roommate_single.setOnClickListener {
        }
        btn_roommate_single.performClick()

        btn_roommate_confirm.setOnClickListener {
            Person.getInstance(this).calculateDefaultValue()
            Person.getInstance(this).save()
            root.removeAllViews()
            initPage4()
        }
    }
    //遊戲開始前背景
    private fun initPage4(){
        addView(R.layout.view_introduction)
        tv_introduction.tag = "1"
        tv_introduction.text = getString(R.string.start_main_1)
        tv_introduction.setOnClickListener {
            if(tv_introduction.tag == "1"){
                tv_introduction.text = getString(R.string.start_main_2)
                tv_introduction.tag = "2"
            }else {
                root.removeAllViews()
                initPageMain()
            }
        }
    }
    //遊戲頁
    private fun initPageMain(){
        if(Daily.getInstance(this).nowTime == 0){
            doFromMorningAction()
            return
        }else if(Daily.getInstance(this).nowTime == 1){
            doFromAfternoonAction()
            return
        }else if(Daily.getInstance(this).nowTime == 2){
            doFromNightAction()
            return
        }
        addView(R.layout.view_main)

        tv_morning.setOnClickListener{
            tv_morning.isSelected = true
            tv_afternoon.isSelected = false
            tv_evening.isSelected = false
            rg_plan.getChildAt(Daily.getInstance(this).morning_plan).performClick()
        }
        tv_afternoon.setOnClickListener {
            tv_morning.isSelected = false
            tv_afternoon.isSelected = true
            tv_evening.isSelected = false
            rg_plan.getChildAt(Daily.getInstance(this).afternoon_plan).performClick()
        }
        tv_evening.setOnClickListener {
            tv_morning.isSelected = false
            tv_afternoon.isSelected = false
            tv_evening.isSelected = true
            rg_plan.getChildAt(Daily.getInstance(this).evening_plan).performClick()
        }
        rg_plan.setOnCheckedChangeListener{ _, id ->
            var index = 0
            for(i in 0 until rg_plan.childCount){
                if(id == rg_plan.getChildAt(i).id){
                    index = i
                    break
                }
            }
            if(tv_morning.isSelected){
                Daily.getInstance(this).morning_plan = index
                tv_morning.text = getPlanStr(index)
            }else if(tv_afternoon.isSelected){
                Daily.getInstance(this).afternoon_plan = index
                tv_afternoon.text = getPlanStr(index)
            }else{
                Daily.getInstance(this).evening_plan = index
                tv_evening.text = getPlanStr(index)
            }
        }
        btn_plan_confirm.setOnClickListener {
            doFromMorningAction()
        }

        updatePageMainValue()
    }
    private fun doFromMorningAction(){
        Daily.getInstance(this).nowTime = 0
        Daily.getInstance(this).save(this)
        root.removeAllViews()
        addView(getPlanViewId(Daily.getInstance(this).morning_plan))
        setViewText(Daily.getInstance(this).morning_plan, 0)
        var needMainClick = doAction(Daily.getInstance(this).morning_plan)
        setTimeView()
        if(needMainClick) {
            root.setOnClickListener {
                doFromAfternoonAction()
            }
        }
    }
    private fun doFromAfternoonAction(){
        Daily.getInstance(this).nowTime = 1
        Daily.getInstance(this).save(this)
        root.removeAllViews()
        addView(getPlanViewId(Daily.getInstance(this).afternoon_plan))
        setViewText(Daily.getInstance(this).afternoon_plan, 1)
        var needMainClick = doAction(Daily.getInstance(this).afternoon_plan)
        setTimeView()
        if(needMainClick) {
            root.setOnClickListener {
                doFromNightAction()
            }
        }
    }
    private fun doFromNightAction(){
        Daily.getInstance(this).nowTime = 2
        Daily.getInstance(this).save(this)
        root.removeAllViews()
        addView(getPlanViewId(Daily.getInstance(this).evening_plan))
        setViewText(Daily.getInstance(this).evening_plan, 2)
        var needMainClick = doAction(Daily.getInstance(this).evening_plan)
        setTimeView()
        if(needMainClick) {
            root.setOnClickListener {
                doFinishAction()
            }
        }
    }
    private fun doFinishAction(){
        Daily.getInstance(this).day++
        Daily.getInstance(this).nowTime = -1
        Daily.getInstance(this).save(this)
        root.removeAllViews()
        var result = Person.getInstance(this).dayPassSafe()
        if(result == 3){
            Person.getInstance(this).save()
        }else if(result == 1){//沒血
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.reset_no_blood))
                .setPositiveButton(R.string.btn_dead_replay) { _, _ ->
                    this.resetAllGameAndInitGame()
                }.setNegativeButton(R.string.btn_dead_leave) { _, _ ->
                    resetAllGame()
                    finish()
                }
                .create().show()

        }else if(result == 2){//沒心情
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.reset_no_mood))
                .setPositiveButton(R.string.btn_dead_replay) { _, _ ->
                    this.resetAllGameAndInitGame()
                }.setNegativeButton(R.string.btn_dead_leave) { _, _ ->
                    resetAllGame()
                    finish()
                }
                .create().show()
        }
        root.setOnClickListener(null)
        initPageMain()
    }
    private fun setTimeView(){
        if(Daily.getInstance(this).nowTime == 0) {
            tv_time.text = getString(R.string.morning)
            tv_time.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.morning), null, null, null)
        }else if(Daily.getInstance(this).nowTime == 1) {
            tv_time.text = getString(R.string.afternoon)
            tv_time.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.afternoon), null, null, null)
        }else {
            tv_time.text = getString(R.string.night)
            tv_time.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.morning), null, null, null)
        }
    }

    private fun updatePageMainBasicValue(){
        btn_replay.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.btn_replay_confirm))
                .setPositiveButton(R.string.btn_confirm) { _, _ ->
                    Daily.getInstance(this).clear(this)
                    Person.getInstance(this).replay()
                    root.removeAllViews()
                    initPage4()
                }
                .setNegativeButton(R.string.btn_cancel){ _, _ ->
                }
                .create().show()
        }
        btn_reset.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.btn_reset_confirm))
                .setPositiveButton(R.string.btn_confirm) { _, _ ->
                    this.resetAllGameAndInitGame()
                }
                .setNegativeButton(R.string.btn_cancel){ _, _ ->
                }
                .create().show()
        }
        tv_money.text = getString(R.string.money_value, Person.getInstance(this).money)
        tv_blood.text = getString(R.string.blood_value, Person.getInstance(this).blood)
        tv_mood.text = getString(R.string.mood_value, Person.getInstance(this).mood)
        tv_rent_pay.text = getString(R.string.rent_pay, Person.getInstance(this).rent_pay)
        tv_salary_pay.text = getString(R.string.salary_pay, Person.getInstance(this).salary)
        var date = Date(2022,3,1)
        date.time += (Daily.getInstance(this).day * 86400 * 1000)
        tv_date.text = MyUtils.getDateFormat(date, getString(R.string.date_format)) + "\n" + getString(R.string.today_date, Daily.getInstance(this).day)

        tv_refrigerator.setOnClickListener {
            checkMyGoodList(0)
        }
        tv_cabinet.setOnClickListener {
            checkMyGoodList(1)
        }
    }
    private fun checkMyGoodList(type: Int){
        var dialog = Dialog(this)
        dialog.setCanceledOnTouchOutside(true)
        var view = LayoutInflater.from(this).inflate(R.layout.view_good_list,  root, false)
        var adapter = GoodAdapter(this)
        adapter.type = 1
        var list = if(type == 0 ) Person.getInstance(this).foodlist
        else Person.getInstance(this).goodlist
        adapter.setDataList(list)
        var rv = view.rv
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        view.btn_good_list_confirm.setOnClickListener {
            dialog.cancel()
        }
        dialog.setContentView(view)
        dialog.show()
        dialog.window!!.attributes.width = WindowManager.LayoutParams.MATCH_PARENT

    }
    private fun resetAllGame(){
        Daily.getInstance(this).clear(this)
        Person.getInstance(this).clear()
    }

    private fun resetAllGameAndInitGame(){
        resetAllGame()
        root.removeAllViews()
        initGame()
    }
    private fun updatePageMainValue(){
        updatePageMainBasicValue()
        tv_morning.text = getPlanStr(Daily.getInstance(this).morning_plan)
        tv_afternoon.text = getPlanStr(Daily.getInstance(this).afternoon_plan)
        tv_evening.text = getPlanStr(Daily.getInstance(this).evening_plan)
        tv_morning.performClick()
    }
    private fun getPlanStr(plan: Int): String{
        var strId: Int
        when(plan){
            PLAN_WORK -> strId = R.string.plan_work
            PLAN_COOK -> strId = R.string.plan_cook
            PLAN_SHOPPING -> strId = R.string.plan_shopping
            PLAN_PLAY_GAME -> strId = R.string.plan_play_game
            PLAN_WATCH_TV -> strId = R.string.plan_watch_tv
            PLAN_SOCIAL_NETWORK -> strId = R.string.plan_social_network
            PLAN_EXERCISE -> strId = R.string.plan_exercise
            PLAN_HANG_OUT -> strId = R.string.plan_hang_out
            else -> strId = R.string.plan_lying_flat
        }
        return getString(strId)
    }
    private fun setViewText(plan: Int, time: Int){
        when(time){
            0 -> tv_time.text = getString(R.string.morning)
            1 -> tv_time.text = getString(R.string.afternoon)
            2 -> tv_time.text = getString(R.string.night)
        }
        when(plan){
            PLAN_WORK -> {
                tv_select_plan.text = getString(R.string.plan_work)
                tv_event.setTextColor(resources.getColor(R.color.white))
                ll_plan.setBackgroundResource(0)
            }
            PLAN_COOK -> {
                tv_select_plan.text = getString(R.string.plan_cook)
                tv_event.setTextColor(resources.getColor(R.color.white))
                ll_plan.setBackgroundResource(0)
            }
            PLAN_SHOPPING -> {
//                tv_select_plan.text = getString(R.string.plan_shopping)
//                tv_event.setTextColor(resources.getColor(R.color.white))
//                ll_plan.setBackgroundResource(0)
            }
            PLAN_PLAY_GAME -> {
                tv_select_plan.text = getString(R.string.plan_play_game)
                tv_event.setTextColor(resources.getColor(R.color.white))
                ll_plan.setBackgroundResource(0)
            }
            PLAN_WATCH_TV -> {
                tv_select_plan.text = getString(R.string.plan_watch_tv)
                tv_event.setTextColor(resources.getColor(R.color.black))
                ll_plan.setBackgroundResource(R.drawable.tv)
            }
            PLAN_SOCIAL_NETWORK -> {
                tv_select_plan.text = getString(R.string.plan_social_network)
                tv_event.setTextColor(resources.getColor(R.color.black))
                ll_plan.setBackgroundResource(R.drawable.phone)
            }
            PLAN_EXERCISE -> {
                tv_select_plan.text = getString(R.string.plan_exercise)
                tv_event.setTextColor(resources.getColor(R.color.white))
                ll_plan.setBackgroundResource(0)
            }
            PLAN_HANG_OUT -> {
                tv_select_plan.text = getString(R.string.plan_hang_out)
                tv_event.setTextColor(resources.getColor(R.color.white))
                ll_plan.setBackgroundResource(0)
            }
            else -> {
                tv_select_plan.text = getString(R.string.plan_lying_flat)
                tv_event.setTextColor(resources.getColor(R.color.white))
                ll_plan.setBackgroundResource(0)
            }
        }
    }
    private fun getPlanViewId(plan: Int): Int {
        var viewId: Int
        when(plan){
            PLAN_SHOPPING -> viewId = R.layout.view_shopping
            else -> viewId = R.layout.view_day_common
        }
        return viewId
    }
    private fun doAction(plan: Int): Boolean{
        var needMainClick = true
        when (plan) {
            PLAN_WORK -> {
                var str = Person.getInstance(this).work()
                if (!TextUtils.isEmpty(str)) {
                    tv_event.text = str
                }
            }
            PLAN_COOK -> {
                var str = Person.getInstance(this).cook()
                if (!TextUtils.isEmpty(str)) {
                    tv_event.text = str
                }
            }
            PLAN_SHOPPING -> {
                needMainClick = false
                root.setOnClickListener(null)
                rg_shopping.check(R.id.tv_shopping_app)
                btn_shopping_confirm.setOnClickListener{
                    var selectId = rg_shopping.checkedRadioButtonId
                    root.removeAllViews()
                    addView(R.layout.view_shopping_list)
                    setTimeView()
                    updatePageMainBasicValue()
//                    tv_shopping_money.text = getString(R.string.money_value, Person.getInstance(this).money)
                    var adapter = GoodAdapter(this)
                    when(selectId){
                        R.id.tv_shopping_app ->{
                            var list:MutableList<Good> = ArrayList()
                            for(i in 0 until (0 + Random().nextInt(3))) {
                                var good = Person.TOTAL_VEGETABLE_LIST[Random().nextInt(Person.TOTAL_VEGETABLE_LIST.size)].clone()
                                good.initVolumeLimit()
                                var index = list.indexOf(good)
                                if(index == -1) {
//                                    good.setPriceNormal(2000)
                                    list.add(good)
                                }else{
                                    list[index].setVolumeLimit(list[index].getVolumeLimit() * 2)
                                }
                            }
                            for(i in 0 until (0 + Random().nextInt(2))) {
                                var good = Person.TOTAL_EXPENSIVE_LIST[Random().nextInt(Person.TOTAL_EXPENSIVE_LIST.size)].clone()
                                good.initVolumeLimit()
                                var index = list.indexOf(good)
                                if(index == -1) {
//                                    good.setPriceNormal(2000)
                                    list.add(good)
                                }else{
                                    list[index].setVolumeLimit(list[index].getVolumeLimit() * 2)
                                }
                            }
                            for(i in 0 until (0 + Random().nextInt(2))) {
                                var good = Person.TOTAL_ADDICTION_LIST[Random().nextInt(Person.TOTAL_ADDICTION_LIST.size)].clone()
                                good.initVolumeLimit()
                                var index = list.indexOf(good)
                                if(index == -1) {
                                    list.add(good)
                                }else{
                                    list[index].setVolumeLimit(list[index].getVolumeLimit() * 2)
                                }
                            }
                            for(i in 0 until (0 + Random().nextInt(2))) {
                                var good = Person.TOTAL_GOOD_LIST[Random().nextInt(Person.TOTAL_GOOD_LIST.size)].clone()
                                good.initVolumeLimit()
                                var index = list.indexOf(good)
                                if(index == -1) {
                                    list.add(good)
                                }else{
                                    list[index].setVolumeLimit(list[index].getVolumeLimit() * 2)
                                }
                            }

                            adapter.setDataList(list)
                            Good.setPriceTimes(GIFT_BUY_MID)
                        }
                        R.id.tv_shopping_dist_group ->{
                            adapter.setDataList(Person.TOTAL_EXPENSIVE_LIST )
                            Good.setPriceTimes(GIFT_DIST_FREE)
                        }
                        R.id.tv_shopping_group ->{
                            adapter.setDataList(Person.TOTAL_ADDICTION_LIST )
                            Good.setPriceTimes(GIFT_BUY)
                        }
                        R.id.tv_shopping_private ->{
                            adapter.setDataList(Person.TOTAL_EXPENSIVE_LIST )
                            Good.setPriceTimes(GIFT_BUY_BIG)
                        }
                    }
                    rv.adapter = adapter
                    rv.layoutManager = LinearLayoutManager(context)
                    btn_shopping_list_cancel.setOnClickListener {
                        AlertUtils.showToast(
                            getString(R.string.shopping_no_buy),
                            Toast.LENGTH_SHORT
                        )
                        btn_shopping_list_confirm.postDelayed(Runnable {
                            shoppingToNextPage()
                        },200)
                    }
                    btn_shopping_list_confirm.setOnClickListener {
                        if(Person.getInstance(this).buyPrice > Person.getInstance(this).money) {
                            AlertUtils.showToast( getString(R.string.shopping_pay_not_enough), Toast.LENGTH_SHORT )
                        }else {
                            if (Person.getInstance(this).buyPrice > 0) {
                                Person.getInstance(this).pay()
                                for(i in 0 until adapter.getDataList().size){
                                    var good = adapter.getDataList().get(i)
                                    if(good.isFood){
                                        var index = Person.getInstance(this).foodlist.indexOf(good)
                                        if(index == -1) {
                                            Person.getInstance(this).foodlist.add(good)
                                        }else{
                                            Person.getInstance(this).foodlist.get(index).select_volume += good.select_volume
                                        }
                                    }else{
                                        var index = Person.getInstance(this).goodlist.indexOf(good)
                                        if(index == -1) {
                                            Person.getInstance(this).goodlist.add(good)
                                        }else{
                                            Person.getInstance(this).goodlist.get(index).select_volume += good.select_volume
                                        }
                                    }
                                }
                                Person.getInstance(this).save()
                                AlertUtils.showToast(
                                    getString(R.string.shopping_pay_already),
                                    Toast.LENGTH_SHORT
                                )
                            } else {
                                AlertUtils.showToast(
                                    getString(R.string.shopping_no_buy),
                                    Toast.LENGTH_SHORT
                                )
                            }
                            btn_shopping_list_confirm.postDelayed(Runnable {
                                shoppingToNextPage()
                            },200)
                        }
                    }
                }
            }
            PLAN_PLAY_GAME -> {
                var str = Person.getInstance(this).playGame()
                if (!TextUtils.isEmpty(str)) {
                    tv_event.text = str
                }
            }
            PLAN_WATCH_TV -> {
                var str = Person.getInstance(this).watchTV()
                if (!TextUtils.isEmpty(str)) {
                    tv_event.text = str
                }
            }
            PLAN_SOCIAL_NETWORK -> {
                var str = Person.getInstance(this).socialNetwork()
                if (!TextUtils.isEmpty(str)) {
                    tv_event.text = str
                }
            }
            PLAN_EXERCISE -> {
                var str = Person.getInstance(this).exercise()
                if (!TextUtils.isEmpty(str)) {
                    tv_event.text = str
                }
            }
            PLAN_HANG_OUT -> {
                //todo
            }
            else ->  {
                var str = Person.getInstance(this).lyingFlat()
                if (!TextUtils.isEmpty(str)) {
                    tv_event.text = str
                }
            }
        }
        return needMainClick
    }
    fun shoppingToNextPage(){
        if(Daily.getInstance(this).nowTime == 0){
            doFromAfternoonAction()
        }else if(Daily.getInstance(this).nowTime == 1){
            doFromNightAction()
        }else{
            doFinishAction()
        }
    }
    fun refreshPrice(){
        var list = (rv!!.adapter!! as GoodAdapter).getDataList()
        var price = 0
        for(i in 0 until list.size){
            if(list[i].select) {
                price += (list[i].getPrice() * list[i].select_volume)
            }
        }
        Person.getInstance(this).buyPrice = price
        tv_pay.text = getString(R.string.shopping_pay) + price.toString()
    }
    class GoodAdapter(var activity: MainActivity): RecyclerView.Adapter<RootHolder>() {
        var type = 0
        private var list: MutableList<Good> = ArrayList()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootHolder {
            var view:View
            if(type == 0){
                view = LayoutInflater.from(context).inflate(R.layout.item_shopping,  parent, false)
                return ShoppingGoodHolder(view)
            }else{
                view = LayoutInflater.from(context).inflate(R.layout.item_good,  parent, false)
                return GoodHolder(view)
            }
        }

        override fun onBindViewHolder(_holder: RootHolder, position: Int) {
            var pos = position
            var good = list.get(pos)
            if(type == 0){
                var holder = _holder as ShoppingGoodHolder
                holder.tv_name.text = good.name
                holder.tv_price.text = good.getPrice().toString()
                holder.tv_volume_limit.text = good.getVolumeLimit().toString()
                holder.cb.isChecked = good.select
                holder.tv_volume.text = list.get(pos).select_volume.toString()
                holder.tv_add.setOnClickListener{
                    var vol = list.get(pos).select_volume
                    if(vol + 1 <= list.get(pos).getVolumeLimit()){
                        list.get(pos).select_volume += 1
                        notifyDataSetChanged()
                        activity.refreshPrice()
                    }else{
                        AlertUtils.showToast(context!!.getString(R.string.shopping_over_volume_max), Toast.LENGTH_SHORT)
                    }
                }
                holder.tv_minus.setOnClickListener{
                    var vol = list.get(pos).select_volume
                    if(vol - 1 > 0){
                        list.get(pos).select_volume -= 1
                        notifyDataSetChanged()
                        activity.refreshPrice()
                    }
                }
                holder.cb.setOnClickListener {
                    holder.itemView.performClick()
                }
                holder.itemView.setOnClickListener {
                    good.select = !good.select
                    activity.refreshPrice()
                    notifyDataSetChanged()
                }
            }else {
                var holder = _holder as GoodHolder
                holder.tv_name.text = good.name
                holder.tv_volume.text = list.get(pos).select_volume.toString()
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }
        fun setDataList(_list: MutableList<Good>){
            list.clear()
            list.addAll(_list)
        }
        fun getDataList(): MutableList<Good>{
            return list
        }
    }
    open class RootHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
    class ShoppingGoodHolder(itemView: View) : RootHolder(itemView) {
        var cb: CheckBox = itemView.cb
        var tv_name: TextView = itemView.tv_name
        var tv_price: TextView = itemView.tv_price
        var tv_add: TextView = itemView.tv_add
        var tv_volume: TextView = itemView.ev_volume
        var tv_minus: TextView = itemView.tv_minus
        var tv_volume_limit: TextView = itemView.tv_volume_limit
    }
    class GoodHolder(itemView: View) : RootHolder(itemView) {
        var tv_name: TextView = itemView.tv_name
        var tv_volume: TextView = itemView.ev_volume
    }

}