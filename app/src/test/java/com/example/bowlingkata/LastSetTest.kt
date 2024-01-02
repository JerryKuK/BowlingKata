package com.example.bowlingkata

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LastSetTest {
    private lateinit var lastSet: LastSet
    @Before
    fun setUp() {
        lastSet = LastSet(1)
    }

    @Test
    fun isStrike_success() {
        Assert.assertTrue(lastSet.isStrike(10))
    }

    @Test
    fun isStrike_fail() {
        Assert.assertFalse(lastSet.isStrike(9))
    }

    @Test
    fun isSpare_success() {
        lastSet.score1 = 1
        Assert.assertTrue(lastSet.isSpare(9))
        lastSet.score1 = 10
        lastSet.score2 = 1
        Assert.assertTrue(lastSet.isSpare(9))
    }

    @Test
    fun isSpare_fail() {
        lastSet.score1 = 10
        Assert.assertFalse(lastSet.isSpare(0))
        lastSet.score1 = 10
        lastSet.score2 = 10
        Assert.assertFalse(lastSet.isSpare(0))
    }

    @Test
    fun setScoreAndGetCount1() {
        val count1 = lastSet.setScoreAndGetCount(5) {}
        val count2 = lastSet.setScoreAndGetCount(4) {}
        val count3 = lastSet.setScoreAndGetCount(4) {}
        Assert.assertEquals(1, count1)
        Assert.assertEquals(2, count2)
        Assert.assertEquals(-1, count3)
        Assert.assertEquals(5, lastSet.score1)
        Assert.assertEquals(4, lastSet.score2)
        Assert.assertEquals(null, lastSet.extraScore1)
    }

    @Test
    fun setScoreAndGetCount2() {
        val count1 = lastSet.setScoreAndGetCount(5) {}
        val count2 = lastSet.setScoreAndGetCount(5) {}
        val count3 = lastSet.setScoreAndGetCount(4) {}
        Assert.assertEquals(1, count1)
        Assert.assertEquals(2, count2)
        Assert.assertEquals(3, count3)
        Assert.assertEquals(5, lastSet.score1)
        Assert.assertEquals(5, lastSet.score2)
        Assert.assertEquals(4, lastSet.extraScore1)
    }

    @Test
    fun setScore5_4_4() {
        lastSet.setScore(5, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(1, time)
            Assert.assertEquals(5, score)
            Assert.assertFalse(b)
        },{
            Assert.fail("還沒結束")
        })

        lastSet.setScore(4, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(2, time)
            Assert.assertEquals(4, score)
            Assert.assertTrue(b)
        },{
            Assert.assertTrue(it)
        })

        lastSet.setScore(4, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(-1, time)
            Assert.assertEquals(4, score)
            Assert.assertTrue(b)
        },{
            Assert.assertTrue(it)
        })
    }

    @Test
    fun setScore5_5_4() {
        lastSet.setScore(5, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(1, time)
            Assert.assertEquals(5, score)
            Assert.assertFalse(b)
        },{
            Assert.fail("還沒結束")
        })

        lastSet.setScore(5, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.SPARE, bowlingType)
            Assert.assertEquals(2, time)
            Assert.assertEquals(5, score)
            Assert.assertTrue(b)
        },{
            Assert.fail("還沒結束")
        })

        lastSet.setScore(4, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(3, time)
            Assert.assertEquals(4, score)
            Assert.assertTrue(b)
        },{
            Assert.assertTrue(it)
        })
    }

    @Test
    fun setScore0_10_10() {
        lastSet.setScore(0, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(1, time)
            Assert.assertEquals(0, score)
            Assert.assertFalse(b)
        },{
            Assert.fail("還沒結束")
        })

        lastSet.setScore(10, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.SPARE, bowlingType)
            Assert.assertEquals(2, time)
            Assert.assertEquals(10, score)
            Assert.assertTrue(b)
        },{
            Assert.fail("還沒結束")
        })

        lastSet.setScore(10, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.STRIKE, bowlingType)
            Assert.assertEquals(3, time)
            Assert.assertEquals(10, score)
            Assert.assertTrue(b)
        },{
            Assert.assertTrue(it)
        })
    }

    @Test
    fun setScore10_0_10() {
        lastSet.setScore(10, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.STRIKE, bowlingType)
            Assert.assertEquals(1, time)
            Assert.assertEquals(10, score)
            Assert.assertTrue(b)
        },{
            Assert.fail("還沒結束")
        })

        lastSet.setScore(0, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(2, time)
            Assert.assertEquals(0, score)
            Assert.assertFalse(b)
        },{
            Assert.fail("還沒結束")
        })

        lastSet.setScore(10, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.SPARE, bowlingType)
            Assert.assertEquals(3, time)
            Assert.assertEquals(10, score)
            Assert.assertTrue(b)
        },{
            Assert.assertTrue(it)
        })
    }

    @Test
    fun setScore5_5_10() {
        lastSet.setScore(5, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.GENERAL, bowlingType)
            Assert.assertEquals(1, time)
            Assert.assertEquals(5, score)
            Assert.assertFalse(b)
        },{
            Assert.fail("還沒結束")
        })

        lastSet.setScore(5, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.SPARE, bowlingType)
            Assert.assertEquals(2, time)
            Assert.assertEquals(5, score)
            Assert.assertTrue(b)
        },{
            Assert.fail("還沒結束")
        })

        lastSet.setScore(10, { bowlingType, time, score, b ->
            Assert.assertEquals(BowlingType.STRIKE, bowlingType)
            Assert.assertEquals(3, time)
            Assert.assertEquals(10, score)
            Assert.assertTrue(b)
        },{
            Assert.assertTrue(it)
        })
    }
}