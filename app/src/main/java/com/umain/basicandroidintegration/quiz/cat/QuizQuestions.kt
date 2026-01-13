package com.umain.basicandroidintegration.quiz.cat

import com.umain.basicandroidintegration.R

sealed interface QuizQuestion
enum class DogQuizQuestion : QuizQuestion {
    DOGE,
    CHEEMS,
    SWOLE,
    FINE,
    SIDE_EYE,
    FLASHLIGHT,
    SAD,
    SMILE,
    CONFUSED,
    ANGRY
}

enum class CatQuizQuestions : QuizQuestion {
    HAPPY,
    CHIPICHAPA,
    HUH,
    BBB,
    MAX,
    POP,
}

val QuizQuestion.questionText: String
    get() = when (this) {
        is CatQuizQuestions -> questionText
        is DogQuizQuestion -> questionText
    }

val QuizQuestion.questionImage: Int
    get() = when (this) {
        is CatQuizQuestions -> questionImage
        is DogQuizQuestion -> TODO()
    }

val QuizQuestion.isAnswerYes: Boolean
    get() = when (this) {
        is CatQuizQuestions -> isAnswerYes
        is DogQuizQuestion -> TODO()
    }

val DogQuizQuestion.questionText: String
    get() = when (this) {
        DogQuizQuestion.DOGE -> "Is this the legendary DOGE?"
        DogQuizQuestion.CHEEMS -> "Does he loves cheeseburgers?"
        DogQuizQuestion.SWOLE -> "Does this dog skip leg day?"
        DogQuizQuestion.FINE -> "Is everything definitely fine here?"
        DogQuizQuestion.SIDE_EYE -> "Does this dog know what you did?"
        DogQuizQuestion.FLASHLIGHT -> "Is this dog on a very serious mission?"
        DogQuizQuestion.SAD -> "Is this dog emotionally manipulating you?"
        DogQuizQuestion.SMILE -> "Is this the dog with an unsettling smile?"
        DogQuizQuestion.CONFUSED -> "Is this the confused dog who understands nothing?"
        DogQuizQuestion.ANGRY -> "Is this the tiny but furious dog?"
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

val CatQuizQuestions.questionImage: Int
    get() = when (this) {
        CatQuizQuestions.BBB -> R.drawable.byb_cat
        CatQuizQuestions.CHIPICHAPA -> R.drawable.chipi_chapa_cat
        CatQuizQuestions.HUH -> R.drawable.huh_cat
        CatQuizQuestions.MAX -> R.drawable.maxwell_cat
        CatQuizQuestions.HAPPY -> R.drawable.happy_cat
        CatQuizQuestions.POP -> R.drawable.pop_cat
    }

val CatQuizQuestions.isAnswerYes: Boolean
    get() = when (this) {
        CatQuizQuestions.POP,
        CatQuizQuestions.MAX,
        CatQuizQuestions.HAPPY,
        CatQuizQuestions.BBB -> true

        else -> false
    }