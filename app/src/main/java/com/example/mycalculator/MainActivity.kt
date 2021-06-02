package com.example.mycalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.mycalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var lastOperator = ' '
        var equalPressed = false
        changeMode()

        binding.button1.setOnClickListener {printOnScreen("1"); equalPressed = false}
        binding.button2.setOnClickListener {printOnScreen("2"); equalPressed = false}
        binding.button3.setOnClickListener {printOnScreen("3"); equalPressed = false}
        binding.button4.setOnClickListener {printOnScreen("4"); equalPressed = false}
        binding.button5.setOnClickListener {printOnScreen("5"); equalPressed = false}
        binding.button6.setOnClickListener {printOnScreen("6"); equalPressed = false}
        binding.button7.setOnClickListener {printOnScreen("7"); equalPressed = false}
        binding.button8.setOnClickListener {printOnScreen("8"); equalPressed = false}
        binding.button9.setOnClickListener {printOnScreen("9"); equalPressed = false}
        binding.button0.setOnClickListener {printOnScreen("0"); equalPressed = false}
        binding.buttonDot.setOnClickListener {
            var lastChar = '$'

            if(binding.calculatorTypeArea.text.isNotEmpty()) {
                lastChar = binding.calculatorTypeArea.text.toString()[binding.calculatorTypeArea.text.toString().length-1]
            }

            if(lastOperator != '.' && lastChar != '+' && lastChar != '-' && lastChar != '*' && lastChar != '/' && lastChar != '.' && lastChar != '$') {
                printOnScreen(".")
                lastOperator = '.'
                equalPressed = false
            }
        }
        binding.buttonPlus.setOnClickListener {
            var lastChar = '$'

            if(binding.calculatorTypeArea.text.isNotEmpty()) {
                lastChar = binding.calculatorTypeArea.text.toString()[binding.calculatorTypeArea.text.toString().length-1]
            }

            if(lastChar != '.' && lastChar!= '+' && lastChar != '-' && lastChar != '*' && lastChar != '/' && lastChar != '.' && lastChar != '$') {
                printOnScreen("+")
                lastOperator = '+'
                equalPressed = false
            }
        }
        binding.buttonMinus.setOnClickListener {
            var lastChar = '$'

            if(binding.calculatorTypeArea.text.isNotEmpty()) {
                lastChar = binding.calculatorTypeArea.text.toString()[binding.calculatorTypeArea.text.toString().length-1]
            }

            if(lastChar != '.' && lastChar!= '+' && lastChar != '-' && lastChar != '*' && lastChar != '/' && lastChar != '.' && lastChar != '$') {
                printOnScreen("-")
                lastOperator = '-'
                equalPressed = false
            }
        }
        binding.buttonMultiply.setOnClickListener {
            var lastChar = '$'

            if(binding.calculatorTypeArea.text.isNotEmpty()) {
                lastChar = binding.calculatorTypeArea.text.toString()[binding.calculatorTypeArea.text.toString().length-1]
            }

            if(lastChar != '.' && lastChar!= '+' && lastChar != '-' && lastChar != '*' && lastChar != '/' && lastChar != '.' && lastChar != '$') {
                printOnScreen("*")
                lastOperator = '*'
                equalPressed = false
            }
        }
        binding.buttonDivide.setOnClickListener {
            var lastChar = '$'

            if(binding.calculatorTypeArea.text.isNotEmpty()) {
                lastChar = binding.calculatorTypeArea.text.toString()[binding.calculatorTypeArea.text.toString().length-1]
            }

            if(lastChar != '.' && lastChar!= '+' && lastChar != '-' && lastChar != '*' && lastChar != '/' && lastChar != '.' && lastChar != '$') {
                printOnScreen("/")
                lastOperator = '/'
                equalPressed = false
            }
        }
        binding.buttonAC.setOnClickListener {
            binding.calculatorResult.text = null
            binding.calculatorTypeArea.text = null
            lastOperator = ' '
            equalPressed = false
        }
        binding.buttonDelete.setOnClickListener {
            val screenText = binding.calculatorTypeArea.text

            if (screenText.isNotEmpty()) {
                if(binding.calculatorTypeArea.text.toString()[binding.calculatorTypeArea.text.toString().length-1] == '.') {
                    lastOperator = ' '
                }

                equalPressed = false
                binding.calculatorTypeArea.text = screenText.delete(screenText.length - 1, screenText.length)
            }

            binding.calculatorResult.text = null
        }
        binding.buttonEqual.setOnClickListener {
            try {
                if(binding.calculatorTypeArea.text.isNotEmpty() && !equalPressed) {
                    val resultExpression = ExpressionBuilder(binding.calculatorTypeArea.text.toString()).build()
                    val result = resultExpression.evaluate()
                    val longResult = result.toLong()

                    equalPressed = true
                    binding.calculatorResult.text.clear()

                    if(result == longResult.toDouble()) {
                        binding.calculatorResult.text.append(longResult.toString())
                    }
                    else {
                        binding.calculatorResult.text.append(result.toString())
                    }
                }
            }
            catch (e: Exception) {
                Log.d("Exception"," message: " + e.message)
            }
        }
    }

    private fun printOnScreen(resultPrint: String) {
        if(binding.calculatorResult.text.isNotEmpty()) {
            binding.calculatorTypeArea.text .append("")
        }

        binding.calculatorResult.text.append("")
        binding.calculatorTypeArea.append(resultPrint)
    }

    private fun changeMode() {
        val exeCurrencyConverter: Button = binding.buttonMode
        exeCurrencyConverter.setOnClickListener {
            val intent = Intent(this, CurrencyConverter::class.java)
            startActivity(intent)
        }
    }
}
