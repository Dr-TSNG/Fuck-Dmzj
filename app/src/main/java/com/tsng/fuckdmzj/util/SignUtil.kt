package com.tsng.fuckdmzj.util

import com.github.kyuubiran.ezxhelper.utils.Log
import com.google.gson.JsonParser
import okhttp3.FormBody
import okhttp3.Request
import kotlin.random.Random

object SignUtil {
    private val uid: Long
        get() {
            TODO("Not implement yet")
        }

    private val token: String
        get() {
            TODO("Not implement yet")
        }

    private val sign: String
        get() {
            TODO("Not implement yet")
        }

    /**
     * 是否签到成功
     */
    fun isSigned(): Boolean {
        val request = Request.Builder().run {
            url("http://api.bbs.muwai.com/v1/sign/detail?uid=$uid&token=$token&sign=$sign&rand=${Random.nextDouble()}")
            build()
        }
        val response = HttpUtil.execNewCall(request)
        if (!response.isSuccessful || response.code != 200) {
            Log.e("Bad response!")
            return false
        }
        val result = JsonParser.parseString(response.body!!.string()).asJsonObject
        return result.get("data").asJsonObject.get("is_sign").asInt == 1
    }

    /**
     * 执行签到
     * 如果签到成功/已签到 返回true
     * 否则返回false
     */
    fun sign(): Boolean {
        val requestBody = FormBody.Builder().run {
            add("uid", uid.toString())
            add("token", token)
            add("sign", sign)
            build()
        }
        val request = Request.Builder().run {
            url("http://api.bbs.muwai.com/v1/sign/add")
            post(requestBody)
            build()
        }
        val response = HttpUtil.execNewCall(request)
        if (!response.isSuccessful || response.code != 200) {
            Log.e("Bad response!")
            return false
        }
        val jsonObject = JsonParser.parseString(response.body!!.string()).asJsonObject
        val msg = jsonObject.get("msg").asString
        Log.i("Sign result = $msg")
        return true
    }
}