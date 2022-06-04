package com.f3401pal.playground.jg.mock

import io.mockk.MockKVerificationScope
import io.mockk.mockk
import io.mockk.verify

inline fun <reified T: Any> mockkRelaxed() = mockk<T>(relaxed = true)

fun verifyNoInteraction(
    verifyBlock: MockKVerificationScope.() -> Unit
) = verify(exactly = 0, verifyBlock = verifyBlock)