package com.f3401pal.playground.jg.domain.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.fixture.createTestTransaction
import com.f3401pal.playground.jg.mock.TestCoroutineDispatcherProvider
import com.f3401pal.playground.jg.mock.mockkRelaxed
import com.f3401pal.playground.jg.repository.TransactionRepository
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import io.mockk.every
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetAllTransactionsTest {

    private val transactionRepository: TransactionRepository = mockkRelaxed()
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider = TestCoroutineDispatcherProvider()

    private val subject = GetAllTransactions(
        transactionRepository, coroutineDispatcherProvider
    )

    @Test
    fun `given a page number, query correct size of transactions`() = runBlocking {
        val list = listOf(createTestTransaction())
        every {
            transactionRepository.getTransactions(any())
        } returns MutableStateFlow(list)

        subject.execute(5).test {
            val pagedList = expectMostRecentItem()
            assertEquals(list, pagedList.transactions)
            assertEquals(5, pagedList.page)
        }
    }

}

@RunWith(AndroidJUnit4::class)
class TransactionPagedListTest {

    @Test
    fun `given number of transactions equals to the page total, hasNextPage true`() {
        val transactions = mutableListOf<Transaction>().apply {
            repeat(25) { add(createTestTransaction()) }
        }
        val subject = TransactionPagedList(
            transactions,
            1
        )

        assertEquals(true, subject.hasNextPage)
    }

    @Test
    fun `given number of transactions less than the page total, hasNextPage false`() {
        val transactions = mutableListOf<Transaction>().apply {
            repeat(20) { add(createTestTransaction()) }
        }
        val subject = TransactionPagedList(
            transactions,
            1
        )

        assertEquals(false, subject.hasNextPage)
    }

}