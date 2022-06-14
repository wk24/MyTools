package com.wuk.mytools.utils

import android.util.Log
import com.google.gson.Gson

/**
 * @author wuk
 * @date 2022/6/13
 */
class JsonUtil {

    private var gson: Gson = Gson();
    private var dog: Dog = Dog();


    companion object {
        fun getInstance() = InstanceHelper.sSingle
    }
    object InstanceHelper {
        val sSingle = JsonUtil()
    }

    fun log(){
        Log.e("TAG", "log: JsonUtil.log" )
    }
}