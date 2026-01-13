package com.umain.basicandroidintegration.quiz.cat

import com.umain.basicandroidintegration.R

enum class CatQuizResults {
    NotACat,
    LittleBit,
    MediumCat,
    BetterThanHalf,
    BigCat,
    Best,
}

val CatQuizResults.text: String
    get() = when (this) {
        CatQuizResults.NotACat -> "Lost..."
        CatQuizResults.LittleBit -> "Confused kitten"
        CatQuizResults.MediumCat -> "Casual Cat scroller"
        CatQuizResults.BetterThanHalf -> "Meme pawprentice"
        CatQuizResults.BigCat -> "Certified Cat Meme Connoisseur!"
        CatQuizResults.Best -> "The chosen Meow!"
    }

val CatQuizResults.image: Int
    get() = when (this) {
        CatQuizResults.NotACat -> R.drawable.not_a_cat
        CatQuizResults.LittleBit -> R.drawable.little_bit
        CatQuizResults.MediumCat -> R.drawable.medium_cat
        CatQuizResults.BetterThanHalf -> R.drawable.better_than_half
        CatQuizResults.BigCat -> R.drawable.big_cat
        CatQuizResults.Best -> R.drawable.best_cat
    }