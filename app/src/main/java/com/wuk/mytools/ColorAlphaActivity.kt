package com.wuk.mytools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_color_alpha.*
import kotlinx.coroutines.*

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

        //协程测试
        CoroutineScope(Dispatchers.Main).launch {

            val result = withContext(Dispatchers.IO) {
                Log.e("TAG", "onCreate: "+3333 )
                delay(5000)
            }
            delay(2000)
            Log.e("TAG", "onCreate: "+1111 )
        }
        Log.e("TAG", "onCreate: "+2222 )

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