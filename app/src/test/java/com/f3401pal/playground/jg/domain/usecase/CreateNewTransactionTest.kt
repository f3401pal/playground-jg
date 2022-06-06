package com.f3401pal.playground.jg.domain.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.f3401pal.playground.jg.domain.model.TransactionInputValidationResult
import com.f3401pal.playground.jg.domain.model.TransactionType
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.mock.TestCoroutineDispatcherProvider
import com.f3401pal.playground.jg.mock.mockkRelaxed
import com.f3401pal.playground.jg.mock.verifyNoInteraction
import com.f3401pal.playground.jg.repository.TransactionRepository
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateNewTransactionTest {

    private val transactionRepository: TransactionRepository = mockkRelaxed()
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider = TestCoroutineDispatcherProvider()

    private val subject = CreateNewTransaction(
        transactionRepository, coroutineDispatcherProvider
    )

    @Test
    fun `given empty description, the result flow emit EmptyDescription`() = runBlocking {
        subject.execute(
            TransactionType.Income,
            "",
            "10"
        ).test {
            assertEquals(TransactionInputValidationResult.EmptyDescription, expectMostRecentItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `given null description, the result flow emit EmptyDescription`() = runBlocking {
        subject.execute(
            TransactionType.Income,
            null,
            "10"
        ).test {
            assertEquals(TransactionInputValidationResult.EmptyDescription, expectMostRecentItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `given empty amount, the result flow emit EmptyAmount`() = runBlocking {
        subject.execute(
            TransactionType.Income,
            "test",
            ""
        ).test {
            assertEquals(TransactionInputValidationResult.EmptyAmount, expectMostRecentItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `given null amount, the result flow emit EmptyAmount`() = runBlocking {
        subject.execute(
            TransactionType.Income,
            "test",
            null
        ).test {
            assertEquals(TransactionInputValidationResult.EmptyAmount, expectMostRecentItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `given non-digits amount, the result flow emit InvalidAmount`() = runBlocking {
        subject.execute(
            TransactionType.Income,
            "test",
            "test"
        ).test {
            assertEquals(TransactionInputValidationResult.InvalidAmount, expectMostRecentItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `given zero amount, the result flow emit InvalidAmount`() = runBlocking {
        subject.execute(
            TransactionType.Income,
            "test",
            "0"
        ).test {
            assertEquals(TransactionInputValidationResult.InvalidAmount, expectMostRecentItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `given valid inputs, the result flow emit Clear`() = runBlocking {
        subject.execute(
            TransactionType.Income,
            "test",
            "123"
        ).test {
            assertEquals(TransactionInputValidationResult.Clear, expectMostRecentItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `given invalid inputs, do NOT save transactions`() = runBlocking {
        subject.execute(
            TransactionType.Income,
            "test",
            "test"
        ).collect()

        verifyNoInteraction {
            transactionRepository.addNewTransaction(any())
        }
    }

    @Test
    fun `given all valid inputs, save transactions`() = runBlocking {
        subject.execute(
            TransactionType.Income,
            "test",
            "123"
        ).collect()

        verify {
            transactionRepository.addNewTransaction(any())
        }
    }

    @Test
    fun `given income type, save a transaction with correct amount`() = runBlocking {
        subject.execute(
            TransactionType.Income,
            "test",
            "123"
        ).collect()

        verify {
            transactionRepository.addNewTransaction(withArg {
                assertEquals(123f, it.amount)
            })
        }
    }

    @Test
    fun `given expense type, save a transaction with correct amount`() = runBlocking {
        subject.execute(
            TransactionType.Expense,
            "test",
            "123"
        ).collect()

        verify {
            transactionRepository.addNewTransaction(withArg {
                assertEquals(-123f, it.amount)
            })
        }
    }
}