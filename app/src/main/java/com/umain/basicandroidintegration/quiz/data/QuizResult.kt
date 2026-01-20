package com.umain.basicandroidintegration.quiz.data

import com.umain.basicandroidintegration.R

sealed interface QuizResult
enum class CatQuizResult : QuizResult {
    Zero,
    One,
    Two,
    Three,
    Four,
    Five,
    Six,
}

enum class DogQuizResult : QuizResult {
    Zero,
    One,
    Two,
    Three,
    Four,
    Five,
    Six,
    Seven,
    Eight,
}

enum class MixedQuizResult : QuizResult {
    Zero,
    One,
    Two,
    Three,
}

val QuizResult.resultText: String
    get() = when (this) {
        is CatQuizResult -> resultText
        is DogQuizResult -> resultText
        is MixedQuizResult -> resultText
    }

val QuizResult.image: Int
    get() = when (this) {
        is CatQuizResult -> image
        is DogQuizResult -> image
        is MixedQuizResult -> image
    }

val CatQuizResult.resultText: String
    get() = when (this) {
        CatQuizResult.Zero -> "404: Cat not found"
        CatQuizResult.One -> "Meh"
        CatQuizResult.Two -> "Confused kitten"
        CatQuizResult.Three -> "Casual Cat scroller"
        CatQuizResult.Four -> "Meme pawprentice"
        CatQuizResult.Five -> "Certified Cat Meme Connoisseur!"
        CatQuizResult.Six -> "The chosen Meow!"
    }

val DogQuizResult.resultText: String
    get() = when (this) {
        DogQuizResult.Zero -> "Lost puppy..."
        DogQuizResult.One -> "Just sniffing around"
        DogQuizResult.Two -> "Error: treat not found"
        DogQuizResult.Three -> "Casual dog meme walker"
        DogQuizResult.Four -> "Certified Good Boi"
        DogQuizResult.Five -> "Meme-fetching machine!"
        DogQuizResult.Six -> "Elite Dog Meme Connoisseur!"
        DogQuizResult.Seven -> "The Chosen Doggo"
        DogQuizResult.Eight -> "Absolute Top Dog"
    }

val MixedQuizResult.resultText: String
    get() = when (this) {
        MixedQuizResult.Zero -> "Lost..."
        MixedQuizResult.One -> "1 question was right"
        MixedQuizResult.Two -> "2 questions were right"
        MixedQuizResult.Three -> "all questions were right!"
    }

val CatQuizResult.image: Int
    get() = when (this) {
        CatQuizResult.Zero -> R.drawable.not_a_cat
        CatQuizResult.One -> R.drawable.not_a_cat
        CatQuizResult.Two -> R.drawable.little_bit
        CatQuizResult.Three -> R.drawable.medium_cat
        CatQuizResult.Four -> R.drawable.better_than_half
        CatQuizResult.Five -> R.drawable.big_cat
        CatQuizResult.Six -> R.drawable.best_cat
    }

val DogQuizResult.image: Int
    get() = when (this) {
        DogQuizResult.Zero -> R.drawable.zero_dog
        DogQuizResult.One -> R.drawable.one_dog
        DogQuizResult.Two -> R.drawable.two_dog
        DogQuizResult.Three -> R.drawable.three_dog
        DogQuizResult.Four -> R.drawable.four_dog
        DogQuizResult.Five -> R.drawable.five_dog
        DogQuizResult.Six -> R.drawable.six_dog
        DogQuizResult.Seven -> R.drawable.doge_dog
        DogQuizResult.Eight -> R.drawable.eight_dog
    }

val MixedQuizResult.image: Int
    get() = when (this) {
        MixedQuizResult.Zero -> R.drawable.not_a_cat
        MixedQuizResult.One -> R.drawable.not_a_cat
        MixedQuizResult.Two -> R.drawable.not_a_cat
        MixedQuizResult.Three -> R.drawable.not_a_cat
    }
