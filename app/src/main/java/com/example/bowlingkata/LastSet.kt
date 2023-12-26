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
        if(score1 == null) {
            score1 = score
            if(score == 10) {
                bowlingType = BowlingType.STRIKE
            }
            scoreCallBack(bowlingType, 1, score, true)

            return
        }

        if(score2 == null) {
            score2 = score
        }

        val score1 = score1 ?: return
        val score2 = score2 ?: return
        if(score1 + score2 < 10) {
            isFinishSet = true
            bowlingType = BowlingType.GENERAL
            scoreCallBack(bowlingType, 2, score, true)

            callback(true)
            return
        } else if(isLastChance) {
            if(extraScore1 == null) {
                extraScore1 = score
            }
            bowlingType = if(score == 10) {
                BowlingType.STRIKE
            } else if(score2 > 1 && score2 + score == 10) {
                BowlingType.SPARE
            } else {
                BowlingType.GENERAL
            }
            scoreCallBack(bowlingType, 3, score, bowlingType == BowlingType.STRIKE || bowlingType == BowlingType.SPARE)

            isFinishSet = true
            callback(true)
        } else {
            isLastChance = true
            bowlingType = if(score2 == 10) {
                BowlingType.STRIKE
            } else if(score1 > 1 && score1 + score2 == 10) {
                BowlingType.SPARE
            } else {
                BowlingType.GENERAL
            }
            scoreCallBack(bowlingType, 2, score, bowlingType == BowlingType.STRIKE || bowlingType == BowlingType.SPARE)
        }
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