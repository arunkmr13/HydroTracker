package com.arunkmr.hydrotrack.domain.model

import java.time.LocalDate

/**
 * Domain model for a symptom check-in entry.
 */
data class SymptomEntry(
    val id: Long = 0,
    val date: LocalDate,
    val feltDizzy: Boolean = false,
    val drySkin: Boolean = false,
    val notes: String? = null
) {
    val hasSymptoms: Boolean
        get() = feltDizzy || drySkin
}
