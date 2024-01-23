package com.ncms.module.models.databaseModel

data class Result(
    val changes: List<Change>?,
    val latest_version: Int
)