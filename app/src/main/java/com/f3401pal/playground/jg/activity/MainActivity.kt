package com.f3401pal.playground.jg.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.f3401pal.playground.jg.R
import com.f3401pal.playground.jg.databinding.ActivityMainBinding
import com.f3401pal.playground.jg.fragment.AddTransactionBottomSheetFragment
import com.f3401pal.playground.jg.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val addTransactionBottomSheet = AddTransactionBottomSheetFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            this.viewModel = mainViewModel

            actionAdd.setOnClickListener {
                if(!addTransactionBottomSheet.isVisible)
                    addTransactionBottomSheet.showNow(supportFragmentManager, AddTransactionBottomSheetFragment.TAG)
            }
        }
    }
}