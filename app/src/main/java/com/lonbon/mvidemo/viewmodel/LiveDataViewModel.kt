package com.lonbon.mvidemo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @Description
 * @author liwu
 * @date 2022/4/19-14:07
 */
class LiveDataViewModel() : ViewModel() {

    private val mutableStateFlow = MutableStateFlow(1)
    val stateF: StateFlow<Int> = mutableStateFlow

    private val mutableSharedFlow = MutableSharedFlow<Int>()
    val shareF = mutableSharedFlow.asSharedFlow()


    /**
     * 测试生命周期
     *
     * @return
     */
    fun alwaysSendMes() = flow {
        while (true) {
            delay(1000)
            emit("1")
            Log.d("测试生命周期", "send")
        }
    }

    /**
     * 测试防抖
     *
     */
    fun testOne() {
        viewModelScope.launch {
            try {
                mutableSharedFlow.emit(1)
                mutableSharedFlow.emit(1)
                mutableSharedFlow.emit(1)
                mutableStateFlow.value = 2
                mutableStateFlow.value = 2
            } catch (e: Exception) {

            }
        }
    }

    val mutableStateFlow1 = MutableStateFlow(1)
    val stateF1: StateFlow<Int> = mutableStateFlow1
    fun test1() {
        viewModelScope.launch {
            mutableStateFlow1.value = 2
            mutableStateFlow1.value = 3
            mutableStateFlow1.value = 4
        }
    }


    val mutableStateFlow2 = MutableSharedFlow<Int>()
    val shareF2 = mutableStateFlow2.asSharedFlow()
    fun test2() {
        viewModelScope.launch {
            mutableStateFlow2.emit(1)
            mutableStateFlow2.emit(2)
            mutableStateFlow2.emit(3)
            mutableStateFlow2.emit(4)

        }
    }


    val mutableStateFlow3 = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 2)
    val shareF3: SharedFlow<Int> = mutableStateFlow3
    fun test3() {
        viewModelScope.launch {
            mutableStateFlow3.emit(1)
            mutableStateFlow3.emit(2)
            mutableStateFlow3.emit(3)
            mutableStateFlow3.emit(4)
            mutableStateFlow3.emit(5)

        }
    }


}