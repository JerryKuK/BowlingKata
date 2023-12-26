package com.example.bowlingkata

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PlayerTest {
    private lateinit var player: Player
    @Before
    fun setUp() {
        player = Player(10)
    }

    @Test
    fun sendData_8_And_1_Score() {
        player.totalScoreListener = { set, totalScore ->
            Assert.assertEquals(1, set)
            Assert.assertEquals(9, totalScore)
        }
        player.sendData(8)
        player.sendData(1)
        Assert.assertEquals(9, player.getSetTotalScore(1))
    }

    @Test
    fun sendData_8_And_2_Score() {
        player.totalScoreListener = { _, _ ->
            Assert.fail("Spare要在一球才會有分數")
        }
        player.sendData(8)
        player.sendData(2)
        Assert.assertEquals(8, player.getSetScore(1, 1))
        Assert.assertEquals(2, player.getSetScore(1, 2))
        Assert.assertEquals(null, player.getSetTotalScore(1))
    }

    @Test
    fun sendData_8_And_2_And_9_Score() {
        player.totalScoreListener = { set, totalScore ->
            Assert.assertEquals(1, set)
            Assert.assertEquals(19, totalScore)
        }
        player.sendData(8)
        player.sendData(2)
        player.sendData(9)
        Assert.assertEquals(8, player.getSetScore(1, 1))
        Assert.assertEquals(2, player.getSetScore(1, 2))
        Assert.assertEquals(9, player.getSetScore(2, 1))
        Assert.assertEquals(19, player.getSetTotalScore(1))
    }

    @Test
    fun sendData_10_Score() {
        player.totalScoreListener = { set, totalScore ->
            Assert.fail("Strike要在兩球才會有分數")
        }
        player.sendData(10)
        Assert.assertEquals(null, player.getSetTotalScore(1))
    }

    @Test
    fun sendData_10_And_8_And_1_Score() {
        player.totalScoreListener = { set, totalScore ->
            when(set) {
                1 -> Assert.assertEquals(19, totalScore)
                2 -> Assert.assertEquals(28, totalScore)
            }
        }
        player.sendData(10)
        player.sendData(8)
        player.sendData(1)
        Assert.assertEquals(10, player.getSetScore(1, 1))
        Assert.assertEquals(null, player.getSetScore(1, 2))
        Assert.assertEquals(8, player.getSetScore(2, 1))
        Assert.assertEquals(1, player.getSetScore(2, 2))
        Assert.assertEquals(19, player.getSetTotalScore(1))
        Assert.assertEquals(28, player.getSetTotalScore(2))
    }

    @Test
    fun sendData_10_And_9_And_1_Score() {
        player.totalScoreListener = { set, totalScore ->
            Assert.assertEquals(1, set)
            Assert.assertEquals(20, totalScore)
        }
        player.sendData(10)
        player.sendData(9)
        player.sendData(1)
        Assert.assertEquals(10, player.getSetScore(1, 1))
        Assert.assertEquals(null, player.getSetScore(1, 2))
        Assert.assertEquals(9, player.getSetScore(2, 1))
        Assert.assertEquals(1, player.getSetScore(2, 2))
        Assert.assertEquals(20, player.getSetTotalScore(1))
    }

    @Test
    fun sendData_10_And_9_And_1_And_9_Score() {
        player.totalScoreListener = { set, totalScore ->
            when(set) {
                1 -> Assert.assertEquals(20, totalScore)
                2 -> Assert.assertEquals(39, totalScore)
            }
        }
        player.sendData(10)
        player.sendData(9)
        player.sendData(1)
        player.sendData(9)
        Assert.assertEquals(10, player.getSetScore(1, 1))
        Assert.assertEquals(null, player.getSetScore(1, 2))
        Assert.assertEquals(9, player.getSetScore(2, 1))
        Assert.assertEquals(1, player.getSetScore(2, 2))
        Assert.assertEquals(9, player.getSetScore(3, 1))
        Assert.assertEquals(20, player.getSetTotalScore(1))
        Assert.assertEquals(39, player.getSetTotalScore(2))
    }

    @Test
    fun sendData_10_And_10_And_9_Score() {
        player.totalScoreListener = { set, totalScore ->
            Assert.assertEquals(1, set)
            Assert.assertEquals(29, totalScore)
        }
        player.sendData(10)
        player.sendData(10)
        player.sendData(9)
        Assert.assertEquals(10, player.getSetScore(1, 1))
        Assert.assertEquals(null, player.getSetScore(1, 2))
        Assert.assertEquals(10, player.getSetScore(2, 1))
        Assert.assertEquals(null, player.getSetScore(2, 2))
        Assert.assertEquals(9, player.getSetScore(3, 1))
        Assert.assertEquals(29, player.getSetTotalScore(1))
    }

    @Test
    fun sendData_10_And_10_And_10_Score() {
        player.totalScoreListener = { set, totalScore ->
            Assert.assertEquals(1, set)
            Assert.assertEquals(30, totalScore)
        }
        player.sendData(10)
        player.sendData(10)
        player.sendData(10)
        Assert.assertEquals(10, player.getSetScore(1, 1))
        Assert.assertEquals(null, player.getSetScore(1, 2))
        Assert.assertEquals(10, player.getSetScore(2, 1))
        Assert.assertEquals(null, player.getSetScore(2, 2))
        Assert.assertEquals(10, player.getSetScore(3, 1))
        Assert.assertEquals(null, player.getSetScore(3, 2))
        Assert.assertEquals(30, player.getSetTotalScore(1))
    }

    @Test
    fun sendLastData_10_And_10_And_10_Score() {
        val player = Player(1)
        player.totalScoreListener = { set, totalScore ->
            Assert.assertEquals(1, set)
            Assert.assertEquals(30, totalScore)
        }
        player.sendData(10)
        player.sendData(10)
        player.sendData(10)
        Assert.assertEquals(30, player.getSetTotalScore(1))
    }

    @Test
    fun sendData_1_And_1_And_1_And_1_Score() {
        player.totalScoreListener = { set, totalScore ->
            when(set) {
                1 -> Assert.assertEquals(2, totalScore)
                2 -> Assert.assertEquals(4, totalScore)
            }
        }
        player.sendData(1)
        player.sendData(1)
        player.sendData(1)
        player.sendData(1)
        Assert.assertEquals(1, player.getSetScore(1, 1))
        Assert.assertEquals(1, player.getSetScore(1, 2))
        Assert.assertEquals(1, player.getSetScore(2, 1))
        Assert.assertEquals(1, player.getSetScore(2, 2))
        Assert.assertEquals(2, player.getSetTotalScore(1))
        Assert.assertEquals(4, player.getSetTotalScore(2))
    }
}