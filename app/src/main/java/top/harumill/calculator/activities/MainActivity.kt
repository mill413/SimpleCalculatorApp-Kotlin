package top.harumill.calculator.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.app1.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var exp = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_plus.setOnClickListener { plusExp("+") }
        btn_minus.setOnClickListener { plusExp("-") }
        btn_multipy.setOnClickListener { plusExp("*") }
        btn_divide.setOnClickListener { plusExp("/") }
        btn_bracket_left.setOnClickListener { plusExp("(") }
        btn_bracket_right.setOnClickListener { plusExp(")") }
        btn_CE.setOnClickListener {
            expression.text = ""
            result.text = "0"

            exp = ""
        }
        btn_delete.setOnClickListener {
            exp = exp.dropLast(1)
            result.text = if(exp.isEmpty()) "0" else exp
        }
        btn_negative.setOnClickListener { plusExp("-") }
        btn_dot.setOnClickListener { plusExp(".") }

        val btndigs = arrayOf(btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

        btndigs.forEach { button ->
            button.setOnClickListener {
                plusExp(
                    when(it){
                        btn_0 -> "0"
                        btn_1 -> "1"
                        btn_2 -> "2"
                        btn_3 -> "3"
                        btn_4 -> "4"
                        btn_5 -> "5"
                        btn_6 -> "6"
                        btn_7 -> "7"
                        btn_8 -> "8"
                        btn_9 -> "9"
                        else -> ""
                    }
                )
            }
        }

        btn_equal.setOnClickListener {
            val res = eval(exp)
            plusExp("=")
            expression.text = exp
            result.text = res.toString()

            exp = ""
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bmi -> {
                startActivity(Intent(applicationContext, BMIActivity::class.java))
            }
            R.id.date -> {
                startActivity(Intent(applicationContext, DateActivity::class.java))
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun plusExp(element:String){
        exp = exp.plus(element)
        result.text = exp
    }

    @SuppressLint("SetTextI18n")
    private fun eval(expr: String): Float {
        var index = 0 // current index
        val skipWhile = { cond: (Char) -> Boolean -> while (index < expr.length && cond(expr[index])) index++ }
        val tryRead = { c: Char -> (index < expr.length && expr[index] == c).also { if (it) index++ } }
        val skipWhitespaces = { skipWhile { it.isWhitespace() } }
        val tryReadOp = { op: Char -> skipWhitespaces().run { tryRead(op) }.also { if (it) skipWhitespaces() } }
        var rootOp: () -> Float = { 0.0f }

        val num = {
            if (tryReadOp('(')) {
                rootOp().also {
                    tryReadOp(')').also {
                        if (!it){
                            result.text = "Missing at: $index"
                            exp = ""
//                            throw IllegalExpressionException(index, "Missing )")
                        }
                    }
                }
            } else {
                val start = index
                tryRead('-') or tryRead('+')
                skipWhile { it.isDigit() || it == '.' }
                try {
                    expr.substring(start, index).toFloat()
                } catch (e: NumberFormatException) {
                    result.text = "Invalid number at:${start}"
                    exp = ""
                    "1.0".toFloat()
//                    throw IllegalExpressionException(start, "Invalid number", cause = e)
                }
            }
        }

        fun binary(left: () -> Float, op: Char): List<Float> = mutableListOf(left()).apply {
            while (tryReadOp(op)) addAll(binary(left, op))
        }

        val div = { binary(num, '/').reduce { a, b -> a / b } }
        val mul = { binary(div, '*').reduce { a, b -> a * b } }
        val sub = { binary(mul, '-').reduce { a, b -> a - b } }
        val add = { binary(sub, '+').reduce { a, b -> a + b } }

        rootOp = add
        return rootOp().also {
            if (index < expr.length) {
                result.text = "Invalid expression at:${index}"
                exp = ""
//                throw IllegalExpressionException(index, "Invalid expression")
            }
        }
    }
}

