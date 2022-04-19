package com.lonbon.mvidemo.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lonbon.mvidemo.R
import com.lonbon.mvidemo.event.MainIntent
import com.lonbon.mvidemo.status.MainViewState
import com.lonbon.mvidemo.viewmodel.ChanelActivityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

/**
 * 用于验证userIntent的线程安全
 *
 */
class ChannelActivity : AppCompatActivity() {

    companion object {
        fun startChannelActivity(context: Context) {
            context.startActivity(Intent(context, ChannelActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channal)
        val viewModel by viewModels<ChanelActivityViewModel>()

        findViewById<Button>(R.id.butThreadSafe).setOnClickListener {
            viewModel.dispatch(MainIntent.FetchNewError)
            for (i in 0..100) {
                viewModel.dispatch(MainIntent.FetchNew)
            }

            viewModel.dispatch(MainIntent.FetchNewError)
            for (i in 0..100) {
                viewModel.dispatch(MainIntent.FetchNew)
            }

            viewModel.dispatch(MainIntent.FetchNewError)
            for (i in 0..100) {
                viewModel.dispatch(MainIntent.FetchNew)
            }
        }

        findViewById<Button>(R.id.butThreadUnSafe).setOnClickListener {
            Thread {
                viewModel.fetchNewsReset()
                for (i in 0..100) {
                    viewModel.fetchAdd()
                }
            }.start()

            Thread {
                viewModel.fetchNewsReset()
                for (i in 0..100) {
                    viewModel.fetchAdd()
                }
            }.start()

            Thread {
                viewModel.fetchNewsReset()
                for (i in 0..100) {
                    viewModel.fetchAdd()
                }
            }.start()
        }


        val text = findViewById<TextView>(R.id.butResult)
        lifecycleScope.launch {
            viewModel.state.collect {
                text.text = "结果是${it}"
            }
        }


    }
}