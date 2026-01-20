package com.umain.basicandroidintegration.quiz.data

import com.umain.basicandroidintegration.R

sealed interface QuizQuestion
enum class DogQuizQuestions : QuizQuestion {
    DOGE,
    FLASHLIGHT,
    CHEEMS,
    SIDE_EYE,
    SMILE,
    SWOLE,
    FINE,
    CONFUSED,
}

enum class CatQuizQuestions : QuizQuestion {
    HAPPY,
    CHIPICHAPA,
    HUH,
    BBB,
    MAX,
    POP,
}

enum class MixedQuizQuestions : QuizQuestion {
    DOG,
    CAT,
    HAMSTER
}

val QuizQuestion.questionText: String
    get() = when (this) {
        is CatQuizQuestions -> questionText
        is DogQuizQuestions -> questionText
        is MixedQuizQuestions -> questionText
    }

val QuizQuestion.questionImage: Int
    get() = when (this) {
        is CatQuizQuestions -> questionImage
        is DogQuizQuestions -> questionImage
        is MixedQuizQuestions -> questionImage
    }

val QuizQuestion.isAnswerYes: Boolean
    get() = when (this) {
        is CatQuizQuestions -> isAnswerYes
        is DogQuizQuestions -> isAnswerYes
        is MixedQuizQuestions -> isAnswerYes
    }

val DogQuizQuestions.questionText: String
    get() = when (this) {
        DogQuizQuestions.DOGE -> "Is this the legendary DOGE?"
        DogQuizQuestions.FLASHLIGHT -> "Is this dog in stealth mode?"
        DogQuizQuestions.CHEEMS -> "Does he love cheeseburgers?"
        DogQuizQuestions.SIDE_EYE -> "Does this dog know what you did?"
        DogQuizQuestions.SMILE -> "Is this the dog with an unsettling smile?"
        DogQuizQuestions.SWOLE -> "Does this dog skip leg day?"
        DogQuizQuestions.FINE -> "Does this dog think it is safe to release?"
        DogQuizQuestions.CONFUSED -> "Is this the dog who understands nothing?"
    }

val CatQuizQuestions.questionText: String
    get() = when (this) {
        CatQuizQuestions.BBB -> "Is this the meme lord known as ЪYЪ?"
        CatQuizQuestions.CHIPICHAPA -> "Is this our beloved CHIPI CHAPA?"
        CatQuizQuestions.HUH -> "Is this the legendary HUH cat?"
        CatQuizQuestions.MAX -> "Is this Max - the cat who's seen things?"
        CatQuizQuestions.HAPPY -> "Does this cat give off happy vibes?"
        CatQuizQuestions.POP -> "Be honest - is that POP cat?"
    }

val MixedQuizQuestions.questionText: String
    get() = when (this) {
        MixedQuizQuestions.DOG -> "Is it dog?"
        MixedQuizQuestions.CAT -> "Is it cat"
        MixedQuizQuestions.HAMSTER -> "Is it him?"
    }

val CatQuizQuestions.questionImage: Int
    get() = when (this) {
        CatQuizQuestions.BBB -> R.drawable.byb_cat
        CatQuizQuestions.CHIPICHAPA -> R.drawable.chipi_chapa_cat
        CatQuizQuestions.HUH -> R.drawable.huh_cat
        CatQuizQuestions.MAX -> R.drawable.maxwell_cat
        CatQuizQuestions.HAPPY -> R.drawable.happy_cat
        CatQuizQuestions.POP -> R.drawable.pop_cat
    }
val DogQuizQuestions.questionImage: Int
    get() = when (this) {
        DogQuizQuestions.DOGE -> R.drawable.doge_dog
        DogQuizQuestions.FLASHLIGHT -> R.drawable.flash_dog
        DogQuizQuestions.CHEEMS -> R.drawable.cheems_dog
        DogQuizQuestions.SIDE_EYE -> R.drawable.eye_dog
        DogQuizQuestions.SMILE -> R.drawable.smile_dog
        DogQuizQuestions.SWOLE -> R.drawable.swole_doge_dog
        DogQuizQuestions.FINE -> R.drawable.fine_dog
        DogQuizQuestions.CONFUSED -> R.drawable.confused_dog
    }

val MixedQuizQuestions.questionImage: Int
    get() = when (this) {
        MixedQuizQuestions.DOG -> R.drawable.doge_dog
        MixedQuizQuestions.CAT -> R.drawable.big_cat
        MixedQuizQuestions.HAMSTER -> R.drawable.chipi_chapa_cat
    }

val CatQuizQuestions.isAnswerYes: Boolean
    get() = when (this) {
        CatQuizQuestions.POP,
        CatQuizQuestions.MAX,
        CatQuizQuestions.HAPPY,
        CatQuizQuestions.BBB -> true

        else -> false
    }

val DogQuizQuestions.isAnswerYes: Boolean
    get() = when (this) {
        DogQuizQuestions.DOGE,
        DogQuizQuestions.CHEEMS,
        DogQuizQuestions.SIDE_EYE,
        DogQuizQuestions.SMILE
            -> true

        else -> false
    }

val MixedQuizQuestions.isAnswerYes: Boolean
    get() = when (this) {
        MixedQuizQuestions.DOG,
        MixedQuizQuestions.CAT
            -> true

        else -> false
    }
