package com.example.multicalculator

import com.example.multicalculator.domain.calculator.AngleUnit
import com.example.multicalculator.domain.calculator.MathEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MathEngineTest {

    @Test
    fun testBasicArithmetic() {
        val res1 = MathEngine.evaluate("2 + 3 * 4")
        assertTrue(res1 is MathEngine.EvaluationResult.Success)
        assertEquals("14", (res1 as MathEngine.EvaluationResult.Success).formattedResult)

        val res2 = MathEngine.evaluate("(2 + 3) * 4")
        assertTrue(res2 is MathEngine.EvaluationResult.Success)
        assertEquals("20", (res2 as MathEngine.EvaluationResult.Success).formattedResult)

        val res3 = MathEngine.evaluate("10 / 2 - 3")
        assertTrue(res3 is MathEngine.EvaluationResult.Success)
        assertEquals("2", (res3 as MathEngine.EvaluationResult.Success).formattedResult)
    }

    @Test
    fun testDivisionByZero() {
        val res = MathEngine.evaluate("10 / 0")
        assertTrue(res is MathEngine.EvaluationResult.Error)
        assertEquals("Division by zero", (res as MathEngine.EvaluationResult.Error).message)
    }

    @Test
    fun testPowerAndSqrt() {
        val resPower = MathEngine.evaluate("2 ^ 3")
        assertTrue(resPower is MathEngine.EvaluationResult.Success)
        assertEquals("8", (resPower as MathEngine.EvaluationResult.Success).formattedResult)

        val resSqrt = MathEngine.evaluate("sqrt(16)")
        assertTrue(resSqrt is MathEngine.EvaluationResult.Success)
        assertEquals("4", (resSqrt as MathEngine.EvaluationResult.Success).formattedResult)
    }

    @Test
    fun testScientificFunctionsDegree() {
        val resSin = MathEngine.evaluate("sin(90)", AngleUnit.DEGREE)
        assertTrue(resSin is MathEngine.EvaluationResult.Success)
        assertEquals("1", (resSin as MathEngine.EvaluationResult.Success).formattedResult)

        val resCos = MathEngine.evaluate("cos(60)", AngleUnit.DEGREE)
        assertTrue(resCos is MathEngine.EvaluationResult.Success)
        assertEquals("0.5", (resCos as MathEngine.EvaluationResult.Success).formattedResult)

        val resAsin = MathEngine.evaluate("asin(1)", AngleUnit.DEGREE)
        assertTrue(resAsin is MathEngine.EvaluationResult.Success)
        assertEquals("90", (resAsin as MathEngine.EvaluationResult.Success).formattedResult)
    }

    @Test
    fun testScientificFunctionsRadian() {
        val resSin = MathEngine.evaluate("sin(pi / 2)", AngleUnit.RADIAN)
        assertTrue(resSin is MathEngine.EvaluationResult.Success)
        assertEquals("1", (resSin as MathEngine.EvaluationResult.Success).formattedResult)

        val resCos = MathEngine.evaluate("cos(pi)", AngleUnit.RADIAN)
        assertTrue(resCos is MathEngine.EvaluationResult.Success)
        assertEquals("-1", (resCos as MathEngine.EvaluationResult.Success).formattedResult)
    }

    @Test
    fun testLogAndLn() {
        val resLog = MathEngine.evaluate("log(100)")
        assertTrue(resLog is MathEngine.EvaluationResult.Success)
        assertEquals("2", (resLog as MathEngine.EvaluationResult.Success).formattedResult)

        val resLn = MathEngine.evaluate("ln(e)")
        assertTrue(resLn is MathEngine.EvaluationResult.Success)
        assertEquals("1", (resLn as MathEngine.EvaluationResult.Success).formattedResult)
    }

    @Test
    fun testFactorialAndPercentage() {
        val resFact = MathEngine.evaluate("5!")
        assertTrue(resFact is MathEngine.EvaluationResult.Success)
        assertEquals("120", (resFact as MathEngine.EvaluationResult.Success).formattedResult)

        val resPct = MathEngine.evaluate("50%")
        assertTrue(resPct is MathEngine.EvaluationResult.Success)
        assertEquals("0.5", (resPct as MathEngine.EvaluationResult.Success).formattedResult)

        val resPctMult = MathEngine.evaluate("50% * 200")
        assertTrue(resPctMult is MathEngine.EvaluationResult.Success)
        assertEquals("100", (resPctMult as MathEngine.EvaluationResult.Success).formattedResult)
    }

    @Test
    fun testImplicitMultiplication() {
        val res1 = MathEngine.evaluate("2pi")
        assertTrue(res1 is MathEngine.EvaluationResult.Success)
        assertEquals("6.28318531", (res1 as MathEngine.EvaluationResult.Success).formattedResult)

        val res2 = MathEngine.evaluate("3(4 + 5)")
        assertTrue(res2 is MathEngine.EvaluationResult.Success)
        assertEquals("27", (res2 as MathEngine.EvaluationResult.Success).formattedResult)

        val res3 = MathEngine.evaluate("2sin(30)", AngleUnit.DEGREE)
        assertTrue(res3 is MathEngine.EvaluationResult.Success)
        assertEquals("1", (res3 as MathEngine.EvaluationResult.Success).formattedResult)
    }

    @Test
    fun testUnaryOperators() {
        val res = MathEngine.evaluate("-5 + 3")
        assertTrue(res is MathEngine.EvaluationResult.Success)
        assertEquals("-2", (res as MathEngine.EvaluationResult.Success).formattedResult)
    }
}
