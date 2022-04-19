package com.lonbon.mvidemo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @Description
 * @author liwu
 * @date 2022/4/19-14:07
 */
class LiveDataViewModel() : ViewModel() {


    fun alwaysSendMes() = flow {
        while (true) {
            delay(1000)
            emit("1")
            Log.d("viewModel mesOne", "send")
        }
    }

}