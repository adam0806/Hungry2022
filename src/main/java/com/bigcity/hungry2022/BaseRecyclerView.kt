package com.bigcity.hungry2022

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerView : RecyclerView {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
}