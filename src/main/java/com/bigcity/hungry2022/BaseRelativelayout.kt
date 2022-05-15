package com.bigcity.hungry2022

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.adam.lib.callback.Callback


class BaseRelativelayout : RelativeLayout{
    var callback: Callback? = null
    var intercept = false
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs){
        init(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes){
        init(attrs)
    }
    fun init(attrs: AttributeSet?){
        if(attrs != null) {
            val array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseLayout)
            if(array != null) {
                intercept = array.getBoolean(R.styleable.BaseLayout_intercept, false)
            }
        }
    }
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        callback?.callback("LinearLayout($tag), dispatchTouchEvent, action:  ${event?.action}")
        var consume = super.dispatchTouchEvent(event)
        callback?.callback("LinearLayout($tag), dispatchTouchEvent, consume: $consume, action:  ${event?.action}")
        return consume
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        callback?.callback("LinearLayout($tag), onTouchEvent, action:  ${event?.action}")
        var consume = super.onTouchEvent(event)
        callback?.callback("LinearLayout($tag), onTouchEvent, consume: $consume, action:  ${event?.action}")
        return consume
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        callback?.callback("LinearLayout($tag), onInterceptTouchEvent, action:  ${event?.action}")
        var consume = intercept || super.onInterceptTouchEvent(event)
        callback?.callback("LinearLayout($tag), onInterceptTouchEvent, consume: $consume, action:  ${event?.action}")
        return consume
    }
}