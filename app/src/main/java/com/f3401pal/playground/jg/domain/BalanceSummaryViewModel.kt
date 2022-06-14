package com.f3401pal.playground.jg.domain

import androidx.lifecycle.ViewModel
import com.f3401pal.playground.jg.domain.usecase.GetBalance
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@HiltViewModel
class BalanceSummaryViewModel @Inject constructor(
    getBalance: GetBalance,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val balanceSummary = getBalance.execute()
        .flowOn(coroutineDispatcherProvider.background)

}