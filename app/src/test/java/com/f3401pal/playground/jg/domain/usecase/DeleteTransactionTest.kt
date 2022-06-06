package com.f3401pal.playground.jg.domain.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.fixture.createTestTransaction
import com.f3401pal.playground.jg.mock.TestCoroutineDispatcherProvider
import com.f3401pal.playground.jg.mock.mockkRelaxed
import com.f3401pal.playground.jg.repository.TransactionRepository
import io.mockk.every
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeleteTransactionTest {

    private val transactionRepository: TransactionRepository = mockkRelaxed()
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider = TestCoroutineDispatcherProvider()

    private val subject = DeleteTransaction(
        transactionRepository, coroutineDispatcherProvider
    )

    @Test(expected = RuntimeException::class)
    fun `given delete transaction not success, throw exception`() {
        every {
            transactionRepository.deleteTransaction(any())
        } returns false

        runBlocking { subject.execute(createTestTransaction()) }
    }

    @Test
    fun `given a transaction, delete it`() {
        every {
            transactionRepository.deleteTransaction(any())
        } returns true

        val transaction = createTestTransaction()
        runBlocking { subject.execute(transaction) }

        verify {
            transactionRepository.deleteTransaction(transaction)
        }
    }
}