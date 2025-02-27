package com.sonali.openlibrary.model

data class MyBooks(
    val entries: List<com.sonali.openlibrary.local.dao.entity.Entry>,
    val links: Links,
    val size: Int

)