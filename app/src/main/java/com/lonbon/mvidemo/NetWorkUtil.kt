/*
 *
 *  * Copyright (C),2007-2021, LonBon Technologies Co. Ltd. All Rights Reserved.
 *
 */

package com.lonbon.mvidemo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.net.UnknownHostException
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

/**
 * @author huanglinqing
 * @project BasicComponentLibrary
 * @date 2020/8/3
 * @desc 网络相关工具类
 */
object NetWorkUtil {
    /**
     * 判断网络是否连接
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @param context 上下文
     * @return `true`: 是<br></br>`false`: 否
     */
    fun isConnected(context: Context): Boolean {
        val info = getActiveNetworkInfo(context)
        return info?.isConnected ?: false
    }

    /**
     * 获取网络连接信息
     * @param context 上下文
     * @return
     */
    private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }


}