package com.example.bowlingkata

class LastSet(override val set: Int) : BaseSet {
    override var bowlingType: BowlingType? = null
    override var beforeTotalScore: Int = 0
    override var score1: Int? = null
    override var score2: Int? = null
    override var extraScore1: Int? = null
    override var extraScore2: Int? = null
    override var isFinishSet: Boolean = false
    private var isLastChance = false

    override fun setScore(
        score: Int,
        scoreCallBack:(BowlingType?, Int, Int, Boolean) -> Unit,
        callback: (isFinish: Boolean) -> Unit
    ) {
        bowlingType = when {
            isStrike(score) -> BowlingType.STRIKE
            isSpare(score) -> BowlingType.SPARE
            else -> BowlingType.GENERAL
        }
        val count = setScoreAndGetCount(score) {
            isFinishSet = it
        }
        scoreCallBack(bowlingType, count, score, bowlingType == BowlingType.STRIKE || bowlingType == BowlingType.SPARE || isFinishSet)
        if(isFinishSet) {
            callback(true)
        }
    }

    override fun isStrike(score: Int): Boolean {
        val score1 = score1
        val score2 = score2
        return (score1 == null ||
                score1 == 10 && score2 == null ||
                (score2 ?: 0) > 0 && score1 + (score2 ?: 0) == 10 ||
                score2 == 10) && score == 10
    }

    override fun isSpare(score: Int): Boolean {
        val score1 = score1
        val score2 = score2
        return if(score1 != null && score2 == null) {
            score1 != 10 && score1 + score == 10
        } else if(score1 != null && score2 != null) {
            score2 != 10 && score2 + score == 10
        } else {
            false
        }
    }

    override fun setScoreAndGetCount(score: Int, isFinish: (Boolean) -> Unit): Int {
        return if(score1 == null) {
            score1 = score
            1
        } else if(score2 == null) {
            isLastChance = setLastChance(score)
            score2 = score
            if(!isLastChance) {
                isFinish(true)
            }
            2
        } else if(isLastChance && extraScore1 == null) {
            extraScore1 = score
            isFinish(true)
            3
        } else {
            -1
        }
    }

    private fun setLastChance(score: Int): Boolean {
        val score1 = score1 ?: return false
        return score1 + score >= 10
    }

    override fun getTotalScore(): Int? {
        val score1 = score1 ?: return null
        val score2 = score2 ?: return null
        if(score1 == 10) {
            val extraScore1 = extraScore1 ?: return null
            return beforeTotalScore + score1 + score2 + extraScore1
        } else if(score1 + score2 == 10) {
            val extraScore1 = extraScore1 ?: return null
            return beforeTotalScore + score1 + score2 + extraScore1
        } else {
            return beforeTotalScore + score1 + score2
        }
    }
}