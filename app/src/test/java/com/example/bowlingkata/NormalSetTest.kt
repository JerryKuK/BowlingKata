package com.example.bowlingkata

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NormalSetTest {
    private lateinit var normalSet: NormalSet
    @Before
    fun setUp() {
        normalSet = NormalSet(1)
    }

    @Test
    fun isStrike_success() {
        Assert.assertTrue(normalSet.isStrike(10))
    }

    @Test
    fun isStrike_fail() {
        normalSet.score1 = 1
        Assert.assertFalse(normalSet.isStrike(10))
    }

    @Test
    fun isSpare_success() {
        normalSet.score1 = 1
        Assert.assertTrue(normalSet.isSpare(9))
    }

    @Test
    fun isSpare_fail() {
        normalSet.score1 = 0
        Assert.assertFalse(normalSet.isSpare(9))
    }

    @Test
    fun setScoreAndGetCount() {
        val count1 = normalSet.setScoreAndGetCount(5) {}
        val count2 = normalSet.setScoreAndGetCount(6) {}
        Assert.assertEquals(1, count1)
        Assert.assertEquals(2, count2)
        Assert.assertEquals(5, normalSet.score1)
        Assert.assertEquals(6, normalSet.score2)
    }

    @Test
    fun setScore5_4() {
        normalSet.setScore(5, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(1, time)
            Assert.assertEquals(5, score)
            Assert.assertFalse(b)
        }, {
            Assert.fail("還沒結束")
        })

        normalSet.setScore(4, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(2, time)
            Assert.assertEquals(4, score)
            Assert.assertTrue(b)
        }, {
            Assert.assertTrue(it)
        })
    }

    @Test
    fun setScore5_5() {
        normalSet.setScore(5, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(1, time)
            Assert.assertEquals(5, score)
            Assert.assertFalse(b)
        }, {
            Assert.fail("還沒結束")
        })

        normalSet.setScore(5, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.SPARE, bowlingType)
            Assert.assertEquals(2, time)
            Assert.assertEquals(5, score)
            Assert.assertTrue(b)
        }, {
            Assert.assertTrue(it)
        })
    }

    @Test
    fun setScore10() {
        normalSet.setScore(10, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.STRIKE, bowlingType)
            Assert.assertEquals(1, time)
            Assert.assertEquals(10, score)
            Assert.assertTrue(b)
        }, {
            Assert.assertTrue(it)
        })
    }
}