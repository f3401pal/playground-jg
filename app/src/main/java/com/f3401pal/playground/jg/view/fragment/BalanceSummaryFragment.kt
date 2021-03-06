package com.f3401pal.playground.jg.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.f3401pal.playground.jg.R
import com.f3401pal.playground.jg.databinding.FragmentBalanceSummaryBinding
import com.f3401pal.playground.jg.domain.BalanceSummaryViewModel
import com.f3401pal.playground.jg.ext.collectAfterCreated
import com.f3401pal.playground.jg.ext.toDollarFormat
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BalanceSummaryFragment : Fragment() {

    // shared with TransactionListFragment
    private val viewModel: BalanceSummaryViewModel by viewModels()
    private lateinit var viewBinding: FragmentBalanceSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_balance_summary, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.balanceSummary.collectAfterCreated(viewLifecycleOwner) {
            viewBinding.expenseTotal.text = it.totalExpense.toDollarFormat()
            viewBinding.incomeTotal.text = it.totalIncome.toDollarFormat()
            viewBinding.balance.text = it.balance.toDollarFormat()

            viewBinding.balanceProgress.setProgressCompat(it.balancePercentage, true)
        }
    }
}