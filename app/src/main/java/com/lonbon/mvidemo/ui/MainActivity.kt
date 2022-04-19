package com.lonbon.mvidemo.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lonbon.mvidemo.databinding.ActivityMainBinding
import com.lonbon.mvidemo.event.MainIntent
import com.lonbon.mvidemo.status.MainViewState
import com.lonbon.mvidemo.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        initClickEvent()
    }

    /**
     * 点击事件
     *
     */
    private fun initClickEvent() {
        binding.btnRequestList.setOnClickListener {
            getNetList()
        }
        binding.btntry.setOnClickListener {
            getNetList()
        }

        binding.btnRequestError.setOnClickListener {
            getError()
        }

        binding.btnClearMes.setOnClickListener {
            binding.tvShowResult.text = ""
        }


        binding.btnGoTestFlow.setOnClickListener {
            TestFlowLifeCycleActivity.startFlowActivity(this)
        }
    }

    /**
     * 事件返回事件
     *
     */
    private fun observeViewModel() {
        lifecycleScope.launch {
            try {
                mainViewModel.state.collect {
                    when (it) {
                        is MainViewState.Loading -> {
                            resetView()
                            binding.tvTip.text = "等待中"
                        }
                        is MainViewState.RequestOneSuccess<*> -> {
                            when (it.successType) {
                                1 -> {
                                    if (it.news is String) {
                                        resetView()
                                        binding.tvShowResult.text = it.news
                                    }
                                }
                                else -> {}
                            }
                        }
                        is MainViewState.NoNetWork -> {
                            resetView()
                            //提示错误信息
                            binding.tvTip.text = "没有网络"
                            binding.btntry.visibility = View.VISIBLE
                        }
                        is MainViewState.Error -> {
                            when (it.errorType) {
                                1 -> {
                                    resetView()
                                    //提示错误信息
                                    binding.tvError.text = it.error
                                    binding.btntry.visibility = View.VISIBLE
                                }
                                else -> {
                                    resetView()
                                    //提示错误信息
                                    binding.tvError.text = it.error
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    /**
     * 重置视图
     *
     */
    private fun resetView() {
        binding.tvTip.text = ""
        binding.tvError.text = ""
        binding.tvShowResult.text = ""
        binding.btntry.visibility = View.GONE
    }

    /**
     * 请求网络数据
     *
     */
    private fun getNetList() {
        mainViewModel.dispatch(MainIntent.FetchNew)
    }

    /**
     * 模拟错误返回
     *
     */
    private fun getError() {
        mainViewModel.dispatch(MainIntent.FetchNewError)
    }
}