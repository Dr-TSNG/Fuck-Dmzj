package com.tsng.fuckdmzj.utils

import android.app.Activity
import com.github.kyuubiran.ezxhelper.utils.*
import com.google.gson.JsonParser
import okhttp3.FormBody
import okhttp3.Request
import kotlin.random.Random

object SignUtils {
    enum class SignStatus {
        YES, NO, ERROR
    }

    private var userModel: Any? = null

    private val uid: String
        get() = userModel!!.invokeMethodAs<String>("getUid")!!

    private val token: String
        get() = userModel!!.invokeMethodAs<String>("getDmzj_token")!!

    private val sign: String
        get() = loadClass("com.dmzjsq.manhua.utils.MD5")
            .invokeStaticMethodAuto("MD5Encode", token + uid + "d&m\$z*j_159753twt") as String

    fun loadUserModel(activity: Activity): Boolean {
        userModel = loadClass("com.dmzjsq.manhua.dbabst.db.UserModelTable")
            .invokeStaticMethodAuto("getInstance", activity)
            ?.invokeMethod("getActivityUser")
        return if (userModel != null) {
            Log.d("Load user: uid=$uid token=$token sign=$sign")
            true
        } else false
    }

    fun getSignStatus(): SignStatus {
        val request = Request.Builder().run {
            url("http://api.bbs.muwai.com/v1/sign/detail?uid=$uid&token=$token&sign=$sign&rand=${Random.nextDouble()}")
            build()
        }
        try {
            val response = HttpUtils.execNewCall(request)
            if (!response.isSuccessful || response.code != 200) {
                Log.w("Check sign status failed: Http bad response ${response.code}")
                return SignStatus.ERROR
            }
            val result = JsonParser.parseString(response.body!!.string()).asJsonObject
            return if (result.get("data").asJsonObject.get("is_sign").asInt == 1) SignStatus.YES else SignStatus.NO
        } catch (e: Throwable) {
            Log.w("Check sign status failed: ${e.localizedMessage}")
            Log.w(e.stackTraceToString())
            return SignStatus.ERROR
        }
    }

    fun sign(): String {
        val requestBody = FormBody.Builder().run {
            add("uid", uid)
            add("token", token)
            add("sign", sign)
            build()
        }
        val request = Request.Builder().run {
            url("http://api.bbs.muwai.com/v1/sign/add")
            post(requestBody)
            build()
        }
        try {
            val response = HttpUtils.execNewCall(request)
            if (!response.isSuccessful || response.code != 200) {
                Log.w("Sign failed: Http bad response ${response.code}")
                Log.w(response.message)
                return "签到失败: Bad response ${response.code}"
            }
            val jsonObject = JsonParser.parseString(response.body!!.string()).asJsonObject
            val msg = jsonObject.get("msg").asString
            return if (msg.contains("签到成功")) {
                Log.i("Sign msg: $msg")
                msg
            } else {
                Log.w("Sign failed: $msg")
                "签到失败: $msg"
            }
        } catch (e: Throwable) {
            Log.w("Sign failed: ${e.localizedMessage}")
            Log.w(e.stackTraceToString())
            return "签到失败: ${e.localizedMessage}"
        }
    }
}