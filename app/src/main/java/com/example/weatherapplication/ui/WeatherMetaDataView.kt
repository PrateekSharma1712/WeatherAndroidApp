package com.example.weatherapplication.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.weatherapplication.R
import kotlinx.android.synthetic.main.view_weather_meta_data.view.*

class WeatherMetaDataView : LinearLayout {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        val view = View.inflate(context, R.layout.view_weather_meta_data, this)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WeatherMetaDataView)
            if (typedArray.hasValue(R.styleable.WeatherMetaDataView_label)) {
                view.label.text = typedArray.getString(R.styleable.WeatherMetaDataView_label)
            }

            typedArray.recycle()
        }

    }
}