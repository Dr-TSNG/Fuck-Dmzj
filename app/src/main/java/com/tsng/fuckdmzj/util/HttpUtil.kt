package com.tsng.fuckdmzj.util

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

object HttpUtil {
    private val okHttpClient = OkHttpClient.Builder().run {
        callTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        build()
    }

    /**
     * 处理新请求
     */
    fun execNewCall(req: Request): Response {
        return okHttpClient.newCall(req).execute()
    }
}