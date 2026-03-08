package com.umain.basicandroidintegration.storage

import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

private const val SCORES_COLLECTION = "scores"

class LeaderBoardStorageImpl : LeaderBoardStorage {
    private val db = Firebase.firestore
    override val scores: Flow<List<Score>>
        get() = db.collection(SCORES_COLLECTION).dataObjects()


    override suspend fun save(score: Score) {
        db.collection(SCORES_COLLECTION).add(score).await()
    }
}