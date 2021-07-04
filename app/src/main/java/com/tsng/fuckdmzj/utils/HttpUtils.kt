package com.tsng.fuckdmzj.utils

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

private typealias OnResponse = (call: Call, response: Response) -> Unit
private typealias OnFailure = (call: Call, e: IOException) -> Unit


object HttpUtils {
    private val okHttpClient = OkHttpClient.Builder().run {
        callTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        build()
    }

    class CallbackFactory : Callback {
        private var onResponse: OnResponse? = null
        private var onFailure: OnFailure? = null

        override fun onResponse(call: Call, response: Response) {
            onResponse?.invoke(call, response)
        }

        override fun onFailure(call: Call, e: IOException) {
            onFailure?.invoke(call, e)
        }

        fun response(onResponse: OnResponse) {
            this.onResponse = onResponse
        }

        fun failure(onFailure: OnFailure) {
            this.onFailure = onFailure
        }
    }

    //处理新请求
    fun execNewCall(req: Request): Response {
        return okHttpClient.newCall(req).execute()
    }

    fun execNewCall(req: Request, callback: Callback) {
        okHttpClient.newCall(req).enqueue(callback)
    }

    fun execNewCall(req: Request, logic: CallbackFactory.() -> Unit) {
        val factory = CallbackFactory()
        factory.logic()
        okHttpClient.newCall(req).enqueue(factory)
    }
}