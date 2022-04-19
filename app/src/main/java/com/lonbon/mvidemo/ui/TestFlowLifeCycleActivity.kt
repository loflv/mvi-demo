package com.lonbon.mvidemo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lonbon.mvidemo.databinding.ActivityTestLivedataBinding
import com.lonbon.mvidemo.viewmodel.LiveDataViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        val inflate = ActivityTestLivedataBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        val viewModel by viewModels<LiveDataViewModel>()
        inflate.testLife.setOnClickListener {
            lifecycleScope.launch {
                try {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.alwaysSendMes().collectLatest {
                            Log.d("测试生命周期", it)
                        }
                    }
                } catch (e: Exception) {
                    Log.d("测试生命周期", e.message.toString())
                }
            }
        }

        inflate.testLife2.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                try {
                    viewModel.alwaysSendMes().collectLatest {
                        Log.d("测试生命周期2", it)
                    }
                } catch (e: Exception) {
                    Log.d("测试生命周期2", e.message.toString())
                }

            }
        }



        lifecycleScope.launch {
            viewModel.shareF.collectLatest {
                Log.d("测试防抖", "sssss")
            }
        }

        lifecycleScope.launch {
            viewModel.stateF.collect {
                Log.d("测试防抖", "kkkkkkkk")
            }
        }

        inflate.testTwo.setOnClickListener {
            viewModel.testOne()
        }


        inflate.test1.setOnClickListener {
            viewModel.test1()
            lifecycleScope.launch {
                viewModel.stateF1.collect {
                    Log.d("测试先发送", "11111111     " + it)
                }
            }
        }

        inflate.test2.setOnClickListener {
            viewModel.test2()
            lifecycleScope.launch {
                viewModel.shareF2.collect {
                    Log.d("测试先发送", "2222222    " + it)
                }
            }

        }

        inflate.test3.setOnClickListener {
            viewModel.test3()
            lifecycleScope.launch {
                viewModel.shareF3.collect {
                    Log.d("测试先发送", "333333    " + it)
                }
            }
        }


    }
}