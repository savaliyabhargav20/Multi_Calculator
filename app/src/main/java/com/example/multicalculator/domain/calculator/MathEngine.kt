package com.example.multicalculator.domain.calculator

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

object MathEngine {

    sealed class EvaluationResult {
        data class Success(val value: Double, val formattedResult: String) : EvaluationResult()
        data class Error(val message: String) : EvaluationResult()
    }

    private sealed class Token {
        data class NumberToken(val value: Double) : Token()
        data class SymbolToken(val symbol: Char) : Token()
        data class IdentifierToken(val name: String) : Token()
    }

    fun evaluate(expression: String, angleUnit: AngleUnit = AngleUnit.DEGREE): EvaluationResult {
        val sanitized = sanitizeExpression(expression)
        if (sanitized.isBlank()) {
            return EvaluationResult.Error("Empty expression")
        }

        return try {
            val rawTokens = tokenize(sanitized)
            val tokens = insertImplicitMultiplication(rawTokens)
            val parser = Parser(tokens, angleUnit)
            val resultValue = parser.parse()
            if (resultValue.isNaN()) {
                EvaluationResult.Error("Invalid calculation")
            } else if (resultValue.isInfinite()) {
                EvaluationResult.Error("Result too large")
            } else {
                EvaluationResult.Success(resultValue, formatResult(resultValue))
            }
        } catch (e: ArithmeticException) {
            EvaluationResult.Error(e.message ?: "Arithmetic error")
        } catch (e: IllegalArgumentException) {
            EvaluationResult.Error(e.message ?: "Invalid expression")
        } catch (e: Exception) {
            EvaluationResult.Error("Syntax error")
        }
    }

    fun formatResult(value: Double): String {
        if (value.isNaN()) return "Error"
        if (value.isInfinite()) return "Infinity"

        // Handle negative zero
        val valNormalized = if (value == -0.0) 0.0 else value

        // Check if value is integer or very close to integer
        val rounded = round(valNormalized)
        if (abs(valNormalized - rounded) < 1e-11) {
            return rounded.toLong().toString()
        }

        val absVal = abs(valNormalized)
        if (absVal >= 1e12 || (absVal > 0 && absVal < 1e-6)) {
            // Scientific notation
            return String.format("%.6e", valNormalized).replace("e+", "e")
        }

        // Standard decimal formatting up to 8 decimal places
        val formatted = String.format("%.8f", valNormalized)
            .trimEnd('0')
            .trimEnd('.')
        return formatted
    }

    private fun sanitizeExpression(expr: String): String {
        return expr
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
            .replace("π", "pi")
            .replace("√", "sqrt")
    }

    private fun tokenize(expr: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var i = 0
        val len = expr.length

        while (i < len) {
            val c = expr[i]
            when {
                c.isWhitespace() -> i++

                c.isDigit() || c == '.' -> {
                    val sb = StringBuilder()
                    var hasDecimal = false
                    while (i < len && (expr[i].isDigit() || expr[i] == '.')) {
                        if (expr[i] == '.') {
                            if (hasDecimal) break
                            hasDecimal = true
                        }
                        sb.append(expr[i])
                        i++
                    }
                    val numStr = sb.toString()
                    val num = numStr.toDoubleOrNull()
                        ?: throw IllegalArgumentException("Invalid number format: $numStr")
                    tokens.add(Token.NumberToken(num))
                }

                c.isLetter() -> {
                    val sb = StringBuilder()
                    while (i < len && expr[i].isLetter()) {
                        sb.append(expr[i])
                        i++
                    }
                    val name = sb.toString().lowercase()
                    if (name == "pi" || name == "e") {
                        tokens.add(Token.IdentifierToken(name))
                    } else if (name == "mod") {
                        tokens.add(Token.SymbolToken('%'))
                    } else {
                        tokens.add(Token.IdentifierToken(name))
                    }
                }

                c in "+-*/%^!()" -> {
                    tokens.add(Token.SymbolToken(c))
                    i++
                }

                else -> {
                    throw IllegalArgumentException("Unexpected character: $c")
                }
            }
        }
        return tokens
    }

    private fun insertImplicitMultiplication(tokens: List<Token>): List<Token> {
        if (tokens.isEmpty()) return tokens
        val result = mutableListOf<Token>()

        fun canEndPrimary(t: Token): Boolean {
            return when (t) {
                is Token.NumberToken -> true
                is Token.IdentifierToken -> t.name == "pi" || t.name == "e"
                is Token.SymbolToken -> t.symbol == ')' || t.symbol == '!' || t.symbol == '%'
            }
        }

        fun canStartPrimary(t: Token): Boolean {
            return when (t) {
                is Token.NumberToken -> true
                is Token.IdentifierToken -> true // pi, e, sin, sqrt, etc.
                is Token.SymbolToken -> t.symbol == '('
            }
        }

        for (i in tokens.indices) {
            val curr = tokens[i]
            if (i > 0) {
                val prev = tokens[i - 1]
                if (canEndPrimary(prev) && canStartPrimary(curr)) {
                    result.add(Token.SymbolToken('*'))
                }
            }
            result.add(curr)
        }

        return result
    }

    private class Parser(
        private val tokens: List<Token>,
        private val angleUnit: AngleUnit
    ) {
        private var pos = 0

        fun parse(): Double {
            val result = parseExpression()
            if (pos < tokens.size) {
                throw IllegalArgumentException("Unexpected token at position $pos")
            }
            return result
        }

        private fun peek(offset: Int = 0): Token? =
            if (pos + offset in tokens.indices) tokens[pos + offset] else null

        private fun consume(): Token {
            val t = peek() ?: throw IllegalArgumentException("Unexpected end of expression")
            pos++
            return t
        }

        private fun canStartExpression(token: Token?): Boolean {
            if (token == null) return false
            return when (token) {
                is Token.NumberToken -> true
                is Token.IdentifierToken -> true
                is Token.SymbolToken -> token.symbol == '(' || token.symbol == '+' || token.symbol == '-'
            }
        }

        // Additive: + and -
        private fun parseExpression(): Double {
            var result = parseMultiplicative()
            while (true) {
                val token = peek()
                if (token is Token.SymbolToken && (token.symbol == '+' || token.symbol == '-')) {
                    consume()
                    val right = parseMultiplicative()
                    if (token.symbol == '+') {
                        result += right
                    } else {
                        result -= right
                    }
                } else {
                    break
                }
            }
            return result
        }

        // Multiplicative: *, /, %
        private fun parseMultiplicative(): Double {
            var result = parsePower()
            while (true) {
                val token = peek()
                if (token is Token.SymbolToken && (token.symbol == '*' || token.symbol == '/' || token.symbol == '%')) {
                    consume()
                    val right = parsePower()
                    when (token.symbol) {
                        '*' -> result *= right
                        '/' -> {
                            if (right == 0.0) throw ArithmeticException("Division by zero")
                            result /= right
                        }
                        '%' -> {
                            if (right == 0.0) throw ArithmeticException("Modulo by zero")
                            result %= right
                        }
                    }
                } else {
                    break
                }
            }
            return result
        }

        // Power: ^ (Right associative)
        private fun parsePower(): Double {
            val base = parseUnary()
            val token = peek()
            if (token is Token.SymbolToken && token.symbol == '^') {
                consume()
                val exponent = parsePower()
                return base.pow(exponent)
            }
            return base
        }

        // Unary: +, -
        private fun parseUnary(): Double {
            val token = peek()
            if (token is Token.SymbolToken && token.symbol == '+') {
                consume()
                return parseUnary()
            }
            if (token is Token.SymbolToken && token.symbol == '-') {
                consume()
                return -parseUnary()
            }
            return parsePostfix()
        }

        // Postfix: ! and % (if percentage e.g. 50%)
        private fun parsePostfix(): Double {
            var result = parsePrimary()
            while (true) {
                val token = peek()
                if (token is Token.SymbolToken && token.symbol == '!') {
                    consume()
                    result = factorial(result)
                } else if (token is Token.SymbolToken && token.symbol == '%') {
                    val nextToken = peek(1)
                    // If next token cannot start an expression, treat % as postfix percentage (Divide by 100)
                    if (!canStartExpression(nextToken)) {
                        consume()
                        result /= 100.0
                    } else {
                        // Leave % for binary operator in parseMultiplicative
                        break
                    }
                } else {
                    break
                }
            }
            return result
        }

        // Primary: Number, Constants, Functions, Parentheses
        private fun parsePrimary(): Double {
            val token = consume()
            return when (token) {
                is Token.NumberToken -> token.value

                is Token.IdentifierToken -> {
                    when (token.name) {
                        "pi" -> Math.PI
                        "e" -> Math.E
                        else -> parseFunction(token.name)
                    }
                }

                is Token.SymbolToken -> {
                    if (token.symbol == '(') {
                        val expr = parseExpression()
                        val next = consume()
                        if (next !is Token.SymbolToken || next.symbol != ')') {
                            throw IllegalArgumentException("Missing closing parenthesis")
                        }
                        expr
                    } else {
                        throw IllegalArgumentException("Unexpected symbol: ${token.symbol}")
                    }
                }
            }
        }

        private fun parseFunction(funcName: String): Double {
            val lParen = consume()
            if (lParen !is Token.SymbolToken || lParen.symbol != '(') {
                throw IllegalArgumentException("Expected '(' after function $funcName")
            }
            val arg = parseExpression()
            val rParen = consume()
            if (rParen !is Token.SymbolToken || rParen.symbol != ')') {
                throw IllegalArgumentException("Missing closing parenthesis for $funcName")
            }

            return when (funcName) {
                "sin" -> if (angleUnit == AngleUnit.DEGREE) sin(Math.toRadians(arg)) else sin(arg)
                "cos" -> if (angleUnit == AngleUnit.DEGREE) cos(Math.toRadians(arg)) else cos(arg)
                "tan" -> if (angleUnit == AngleUnit.DEGREE) tan(Math.toRadians(arg)) else tan(arg)
                "asin" -> {
                    if (arg < -1.0 || arg > 1.0) throw ArithmeticException("Domain error: asin argument must be between -1 and 1")
                    val res = asin(arg)
                    if (angleUnit == AngleUnit.DEGREE) Math.toDegrees(res) else res
                }
                "acos" -> {
                    if (arg < -1.0 || arg > 1.0) throw ArithmeticException("Domain error: acos argument must be between -1 and 1")
                    val res = acos(arg)
                    if (angleUnit == AngleUnit.DEGREE) Math.toDegrees(res) else res
                }
                "atan" -> {
                    val res = atan(arg)
                    if (angleUnit == AngleUnit.DEGREE) Math.toDegrees(res) else res
                }
                "sqrt" -> {
                    if (arg < 0) throw ArithmeticException("Cannot calculate square root of negative number")
                    sqrt(arg)
                }
                "log" -> {
                    if (arg <= 0) throw ArithmeticException("Logarithm argument must be positive")
                    log10(arg)
                }
                "ln" -> {
                    if (arg <= 0) throw ArithmeticException("Logarithm argument must be positive")
                    ln(arg)
                }
                "abs" -> abs(arg)
                "fact" -> factorial(arg)
                else -> throw IllegalArgumentException("Unknown function: $funcName")
            }
        }

        private fun factorial(n: Double): Double {
            if (n < 0) throw ArithmeticException("Factorial of negative number")
            val nLong = n.toLong()
            if (nLong.toDouble() != n) throw ArithmeticException("Factorial requires an integer")
            if (nLong > 170) return Double.POSITIVE_INFINITY
            var result = 1.0
            for (i in 2..nLong) {
                result *= i
            }
            return result
        }
    }
}
