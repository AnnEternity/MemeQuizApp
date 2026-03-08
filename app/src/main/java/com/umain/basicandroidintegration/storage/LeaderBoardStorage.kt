package com.umain.basicandroidintegration.storage

import kotlinx.coroutines.flow.Flow

interface LeaderBoardStorage {
    val scores: Flow<List<Score>>

    suspend fun save(score: Score)
}
