package com.tsng.fuckdmzj.utils

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

object HttpUtils {
    private val okHttpClient = OkHttpClient.Builder().run {
        callTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        build()
    }

    //处理新请求
    fun execNewCall(req: Request): Response {
        return okHttpClient.newCall(req).execute()
    }

    fun execNewCall(req: Request, callback: Callback) {
        okHttpClient.newCall(req).enqueue(callback)
    }
}