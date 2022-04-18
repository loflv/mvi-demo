package com.lonbon.mvidemo

import com.lonbon.mvidemo.api.MessApi
import com.lonbon.mvidemo.bean.HotKeyBean

/**
 * @Description  仓库获取查询热词
 * @author liwu
 * @date 2022/4/14-16:56
 */
class NetRepository {

    /**
     *获取搜索热词
     */
    suspend fun getHotkey(): HotKeyBean? {
        val network = RetrofitFactory.createRetrofitService(
            MessApi::class.java
        )
        return network?.getHotKey()
    }
}