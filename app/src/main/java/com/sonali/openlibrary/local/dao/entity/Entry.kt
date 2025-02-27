package com.sonali.openlibrary.local.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "library")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val full_url: String,

    @ColumnInfo
    val url: String,

    @ColumnInfo
    val seed_count: Int,

    @ColumnInfo
    val last_update: String

    //"url": "/people/george08/lists/OL7L",
    //"full_url": "/people/george08/lists/OL7L/Women_1850-1920",
    //"name": "Women 1850-1920",
    //"seed_count": 7,
    //"last_update": "2022-12-21T15:54:45.391690"
)