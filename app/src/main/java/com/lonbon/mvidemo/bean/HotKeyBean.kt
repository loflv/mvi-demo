package com.lonbon.mvidemo.bean

/**
 * @author liwu
 * @Description 接口请求返回对象
 * @date 2022/4/12-17:15
 */
class HotKeyBean {
    var data: List<DataBean>? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        var id = 0
        var link: String? = null
        var name: String? = null
        var order = 0
        var visible = 0
    }

    val dataStr: String
        get() {
            return data?.map { it.name }?.joinToString() ?: ""
        }
}