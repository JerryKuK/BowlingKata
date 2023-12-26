package com.example.bowlingkata

interface BaseSet {
    val set: Int
    var bowlingType: BowlingType?
    var beforeTotalScore: Int
    var score1: Int?
    var score2: Int?
    var extraScore1: Int?
    var extraScore2: Int?
    var isFinishSet: Boolean
    fun setScore(
        score: Int,
        scoreCallBack:(BowlingType?, Int, Int, Boolean) -> Unit,
        callback: (isFinish: Boolean) -> Unit
    )
    fun getTotalScore(): Int?
}