package com.lonbon.mvidemo.api

import com.lonbon.mvidemo.bean.HotKeyBean
import retrofit2.http.GET

/**
 * @Description 任意一个接口api
 * @author liwu
 * @date 2022/4/12-17:11
 */
interface MessApi {

    /**
     * 查询搜索热词
     *
     * @return
     */
    @GET("hotkey/json")
    suspend fun getHotKey(): HotKeyBean
}