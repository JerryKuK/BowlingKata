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
        if(score1 == null) {
            score1 = score
            if(score1 == 10) {
                bowlingType = BowlingType.STRIKE
                scoreCallBack(bowlingType, 1, score, true)

                isFinishSet = true
                callback(true)
            } else {
                scoreCallBack(bowlingType, 1, score, false)
            }
            return
        }

        if(score2 == null) {
            score2 = score
        }

        isFinishSet = true
        val score1 = score1 ?: return
        val score2 = score2 ?: return
        if(score1 + score2 == 10) {
            bowlingType = BowlingType.SPARE
            scoreCallBack(bowlingType, 2, score, true)

            callback(true)
        } else {
            bowlingType = BowlingType.GENERAL
            scoreCallBack(bowlingType, 2, score, true)

            callback(true)
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