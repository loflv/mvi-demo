package com.lonbon.mvidemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lonbon.mvidemo.NetRepository
import com.lonbon.mvidemo.event.MainIntent
import com.lonbon.mvidemo.status.MainViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * @Description 主页面对应的viewmodel
 * @author liwu
 * @date 2022/4/14-11:27
 */
class MainViewModel : ViewModel() {

    private val repository = NetRepository()

    private val _state = MutableSharedFlow<MainViewState>()
    val state: SharedFlow<MainViewState>
        get() = _state

    /**
     * 接收事件
     * 使用livedata或者stateflow会导致事件丢失
     */
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)

    init {
        //所有的intent集中处理
        viewModelScope.launch {
            try {
                userIntent.consumeAsFlow().collect {
                    when (it) {
                        is MainIntent.FetchNew -> fetchNews()
                        is MainIntent.FetchNewError -> fetchNewsError()
                    }
                }
            } catch (e: java.lang.Exception) {

            }

        }
    }


    /**
     * 分发事件
     *
     * @param viewAction
     */
    fun dispatch(viewAction: MainIntent) {
        viewModelScope.launch {
            try {
                userIntent.send(viewAction)
            } catch (e: java.lang.Exception) {

            }

        }
    }

    /**
     * 查询热词
     *
     */
    private fun fetchNews() {
        viewModelScope.launch {
            try {
                //返回状态
                _state.emit(MainViewState.Loading)
                val hotkey = repository.getHotkey()
                _state.emit(
                    when {
                        hotkey == null -> {
                            MainViewState.NoNetWork
                        }
                        hotkey.errorCode != 0 -> {
                            MainViewState.Error(1, "请求失败了")
                        }
                        else -> {
                            MainViewState.RequestOneSuccess(1, hotkey.dataStr)
                        }
                    }
                )
            } catch (e: Exception) {
                _state.emit(MainViewState.Error(1, e.localizedMessage))
            }
        }
    }

    /**
     * 模拟错误情况
     *
     */
    private fun fetchNewsError() {
        viewModelScope.launch {
            try {
                //返回状态
                _state.emit(MainViewState.Loading)
                delay(2000)
                _state.emit(MainViewState.Error(2, "出错了"))
            } catch (e: java.lang.Exception) {

            }
        }
    }
}




