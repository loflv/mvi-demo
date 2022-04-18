package com.lonbon.mvidemo.event

/**
 * @Description 定义所有事件
 * @author liwu
 * @date 2022/4/14-16:05
 */
sealed class MainIntent {

    /**
     * 查询热词
     */
    object FetchNew : MainIntent()

    /**
     * 模拟网络失败
     */
    object FetchNewError : MainIntent()

}