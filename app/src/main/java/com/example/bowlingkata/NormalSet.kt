package com.example.bowlingkata

class NormalSet(override val set: Int) : BaseSet {
    override var bowlingType: BowlingType? = null
    override var beforeTotalScore: Int = 0
    override var score1: Int? = null
    override var score2: Int? = null
    override var extraScore1: Int? = null
    override var extraScore2: Int?  = null
    override var isFinishSet: Boolean = false

    override fun setScore(
        score: Int,
        scoreCallBack:(BowlingType?, Int, Int, Boolean) -> Unit,
        callback: (isFinish: Boolean) -> Unit
    ) {
        when {
            isStrike(score) -> {
                isFinishSet = true
                bowlingType = BowlingType.STRIKE
            }
            isSpare(score) -> {
                isFinishSet = true
                bowlingType = BowlingType.SPARE
            }
            else -> {
                bowlingType = BowlingType.GENERAL
            }
        }
        val count = setScoreAndGetCount(score) {
            isFinishSet = it
        }
        scoreCallBack(bowlingType, count, score, isFinishSet)
        if(isFinishSet) {
            callback(true)
        }
    }

    override fun isStrike(score: Int): Boolean {
        return score1 == null && score == 10
    }

    override fun isSpare(score: Int): Boolean {
        val score1 = score1 ?: return false
        return score1 != 10 && score1 + score == 10
    }

    override fun setScoreAndGetCount(score: Int, isFinish: (Boolean) -> Unit): Int {
        return if(score1 == null) {
            score1 = score
            1
        } else if(score2 == null) {
            score2 = score
            isFinish(true)
            2
        } else {
            -1
        }
    }

    override fun getTotalScore(): Int? {
        val score1 = score1 ?: return null
        when(bowlingType) {
            BowlingType.STRIKE -> {
                val extraScore1 = extraScore1 ?: return null
                val extraScore2 = extraScore2 ?: return null
                return beforeTotalScore + score1 + extraScore1 + extraScore2
            }
            BowlingType.SPARE -> {
                val score2 = score2 ?: return null
                val extraScore1 = extraScore1 ?: return null
                return beforeTotalScore + score1 + score2 + extraScore1
            }
            else -> {
                val score2 = score2 ?: return null
                return beforeTotalScore + score1 + score2
            }
        }
    }
}