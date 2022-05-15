package com.bigcity.hungry2022

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager

class BaseDialog : Dialog{
    constructor(context: Context): super(context)
    init {
        var lp = window!!.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window!!.attributes = lp
    }
}