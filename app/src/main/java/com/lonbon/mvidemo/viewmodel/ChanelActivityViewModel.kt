package com.lonbon.mvidemo.viewmodel

import android.util.Log
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
import kotlin.random.Random

/**
 * @Description
 * @author liwu
 * @date 2022/4/19-09:37
 */
class ChanelActivityViewModel : ViewModel() {


    /**
     * 接收事件
     */
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchNew -> fetchAdd()
                    is MainIntent.FetchNewError -> fetchNewsReset()
                }
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
            userIntent.send(viewAction)
        }
    }

    private val _state = MutableSharedFlow<Int>()
    val state: SharedFlow<Int>
        get() = _state


    var i = 0

    fun fetchAdd() {
        viewModelScope.launch {
            i++
            Log.d("live", "fetchNews1     " + i)
            _state.emit(i)
        }
    }

    fun fetchNewsReset() {
        viewModelScope.launch {
            i = 0
            Log.d("live", "fetchNewsReset     " + i)
            _state.emit(i)
        }
    }

}