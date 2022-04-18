package com.lonbon.mvidemo.status

/**
 * @Description 定义所有状态
 * @author liwu
 * @date 2022/4/14-16:05
 */
sealed class MainViewState {
    /**
     * 等待状态
     */
    object Loading : MainViewState()

    /**
     * 没有网络
     */
    object NoNetWork : MainViewState()

    /**
     * 请求失败
     *
     * @property error
     */
    data class Error(val error: String?) : MainViewState()

    /**
     * 请求成功事件
     *
     * @property news
     */
    data class RequestOneSuccess(val news: String) : MainViewState()


    data class RequestTwoSuccess(val news: String) : MainViewState()


    data class Error2(val error: String?) : MainViewState()

}