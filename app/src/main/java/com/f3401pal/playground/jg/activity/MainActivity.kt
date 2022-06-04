package com.f3401pal.playground.jg.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.f3401pal.playground.jg.R
import com.f3401pal.playground.jg.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}