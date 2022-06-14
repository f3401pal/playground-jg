package com.f3401pal.playground.jg.domain

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.f3401pal.playground.jg.domain.usecase.*
import com.f3401pal.playground.jg.fixture.createTestTransaction
import com.f3401pal.playground.jg.mock.TestCoroutineDispatcherProvider
import com.f3401pal.playground.jg.mock.mockkRelaxed
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TransactionListViewModelTest {

    private val allTransactions = mockkRelaxed<TransactionPagedList>()
    private val allTransactionsFlow = MutableStateFlow(allTransactions)
    private val dailyTransactions = mockkRelaxed<DailyTransactionsPagedList>()

    private val getAllTransactions: GetAllTransactions = mockkRelaxed<GetAllTransactions>().apply {
        every {
            execute(any())
        } returns allTransactionsFlow
    }
    private val groupDailyTransactions: GroupDaliyTransactions = mockkRelaxed<GroupDaliyTransactions>().apply {
        every {
            execute(allTransactions)
        } returns dailyTransactions
    }

    private val deleteTransaction: DeleteTransaction = mockkRelaxed()
    private val coroutineDispatcherProvider = TestCoroutineDispatcherProvider()

    private val subject = TransactionListViewModel(
        getAllTransactions,
        groupDailyTransactions,
        deleteTransaction,
        coroutineDispatcherProvider
    )

    @Test
    fun `given a list of transaction, map to daily transactions`() = runBlocking {
        subject.dailyTransactions.test {
            assertEquals(dailyTransactions, expectMostRecentItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `given a transaction, execute deleteTransaction`() {
        val transactions = createTestTransaction()

        subject.deleteTransaction(transactions)

        coVerify { deleteTransaction.execute(transactions) }
    }

    @Test
    fun `given deleteTransaction throws exception, emit error`() {
        val transactions = createTestTransaction()
        val exception = RuntimeException("test")
        coEvery {
            deleteTransaction.execute(transactions)
        } throws exception

        runBlocking {
            subject.error.test {
                subject.deleteTransaction(transactions)
                assertEquals(exception, expectMostRecentItem())
            }
        }
    }
}