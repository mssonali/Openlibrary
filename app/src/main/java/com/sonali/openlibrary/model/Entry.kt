package com.sonali.openlibrary.model

data class Entry(
    val full_url: String,
    val last_update: String,
    val name: String,
    val seed_count: Int,
    val url: String
)