package com.f3401pal.playground.jg.domain.model

/**
 * Add new transaction validation result
 */
enum class TransactionInputValidationResult {
    EmptyDescription, // empty description field
    EmptyAmount, // empty amount field
    InvalidAmount, // invalid characters or zero amount
    Clear // clear all errors
}