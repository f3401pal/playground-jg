package com.f3401pal.playground.jg.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.f3401pal.playground.jg.R
import com.f3401pal.playground.jg.databinding.ActivityMainBinding
import com.f3401pal.playground.jg.fragment.AddTransactionBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {

            actionAdd.setOnClickListener {
                if(supportFragmentManager.findFragmentByTag(AddTransactionBottomSheetFragment.TAG) == null)
                    AddTransactionBottomSheetFragment().showNow(supportFragmentManager, AddTransactionBottomSheetFragment.TAG)
            }
        }
    }
}