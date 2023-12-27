package com.example.bowlingkata

class Player(private val setCount: Int) {
    private val totalSetList: List<BaseSet> by lazy {
        val list = mutableListOf<BaseSet>()
        for (item in 1..setCount) {
            if(item == setCount) {
                list.add(LastSet(item))
            } else {
                list.add(NormalSet(item))
            }
        }
        list
    }

    private val currentSetList: MutableList<BaseSet> = mutableListOf(totalSetList[0])
    var scoreListener: ((set: Int, bowlingType: BowlingType?, time: Int, score: Int?, isResetLimit: Boolean) -> Unit)? = null
    var totalScoreListener: ((set: Int, totalScore: Int?) -> Unit)? = null

    fun sendData(score: Int) {
        setScoreForStrikeOrSpare(score)
        setScoreForNewData(score)
    }

    private fun setScoreForStrikeOrSpare(score: Int) {
        currentSetList.toList().forEach {
            if(!it.isFinishSet) {
                return@forEach
            }

            if(it.bowlingType != BowlingType.GENERAL) {
                if(it.extraScore1 == null) {
                    it.extraScore1 = score
                } else if(it.extraScore2 == null) {
                    it.extraScore2 = score
                }
            }


            val totalScore = it.getTotalScore()
            if(totalScore != null) {
                val index = currentSetList.indexOf(it)
                if(index != -1 && index + 1 < currentSetList.size) {
                    currentSetList[index + 1].beforeTotalScore = totalScore
                }
                totalScoreListener?.invoke(it.set, totalScore)

                currentSetList.remove(it)
            }
        }
    }

    private fun setScoreForNewData(score: Int) {
        val baseSet = currentSetList.lastOrNull()
        baseSet?.setScore(
            score, { bowlingType, time, score, isResetLimit ->
                scoreListener?.invoke(baseSet.set, bowlingType, time, score, isResetLimit)
            }) {
            if (it) {
                if(baseSet.set < setCount) {
                    val newBaseSet = totalSetList[baseSet.set]
                    currentSetList.add(newBaseSet)
                }

                val totalScore = baseSet.getTotalScore()
                if(totalScore != null) {
                    totalScoreListener?.invoke(baseSet.set, totalScore)
                }
            }
        }
    }

    fun getSetScore(set: Int, whatBall: Int): Int? {
        val setIndex = set - 1
        if(setIndex < 0 || setIndex > setCount - 1) {
            return null
        }
        val baseSet = totalSetList[setIndex]
        return if(whatBall == 1) baseSet.score1 else baseSet.score2
    }

    fun getSetTotalScore(set: Int): Int? {
        val setIndex = set - 1
        if(setIndex < 0 || setIndex > setCount - 1) {
            return null
        }
        val baseSet = totalSetList[setIndex]
        return baseSet.getTotalScore()
    }

    fun isLastSetFinish(): Boolean {
        return totalSetList.lastOrNull()?.isFinishSet ?: true
    }
}