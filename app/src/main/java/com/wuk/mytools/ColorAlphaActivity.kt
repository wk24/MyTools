package com.wuk.mytools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_color_alpha.*

/**
 * 百分比透明度, 转换成十六进制
 *
 * 弹性布局
 *
 */
class ColorAlphaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_alpha)

        setTitle("透明度转换")

        tv_btn.setOnClickListener{

            val inputStr = et.text.toString()
            val alpha = getAlpha(inputStr.toInt())
            tv_result.setText("对应的十六进制是:"+alpha)
        }
    }

    fun getAlpha(i:Int) : String{
        val double = i.toDouble()
        val d = double / 100;
        val alpha = Math.round(d * 255).toInt()
        var hex = Integer.toHexString(alpha).toUpperCase();
        if (hex.length == 1) {
            hex = "0" + hex;
        }
        return hex
    }

}