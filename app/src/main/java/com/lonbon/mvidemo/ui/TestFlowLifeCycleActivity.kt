package com.lonbon.mvidemo.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lonbon.mvidemo.R
import com.lonbon.mvidemo.viewmodel.LiveDataViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * 测试flow的生命周期
 *
 * 不光停止了接收,还停止了发送
 */
class TestFlowLifeCycleActivity : AppCompatActivity() {

    companion object {
        fun startFlowActivity(context: Context) {
            context.startActivity(Intent(context, TestFlowLifeCycleActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_livedata)

        val viewModel by viewModels<LiveDataViewModel>()
        lifecycleScope.launch {
            try {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.alwaysSendMes().collectLatest {
                        Log.d("mesOne", it)
                    }
                }
            } catch (e: Exception) {
                Log.d("TestFlowLifeCycleActivity", e.message.toString())
            }
        }

    }
}