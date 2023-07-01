package com.example.mycalculator
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)

        lastNumeric = true
        lastDot = false

        Toast.makeText(this, "Button clicked", Toast.LENGTH_LONG).show()
    }

    fun onClear(view: View){        // to remove everything from the display when clr clicked
        tvInput?.text = ""
    }

    fun onDecimal(view: View){      //function to ensure decimal only comes once and come only after a number
        if(lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded((it.toString()))){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view : View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try{

                if(tvValue.startsWith("-")){    //this if is because if no. starts with - then below splitting will
                    prefix = "-"                     //get confuse to split using which - and app will crash
                    tvValue = tvValue.substring(1)      //it will convert -99 to 99
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")  // splitting "99-1" int 99 and 1 by using "-" as splitter

                    var one = splitValue[0] //99
                    var two = splitValue[1] //1

                    if(prefix.isNotEmpty()){
                        one = prefix + one      //we removed confusion of starting "-" and now we are adding "-" back for operations
                    }

                    tvInput?.text = removezeroafterdot((one.toDouble() - two.toDouble()).toString())  //tvInput always take string
                }
                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")  // splitting "99-1" int 99 and 1 by using "-" as splitter

                    var one = splitValue[0] //99
                    var two = splitValue[1] //1

                    if(prefix.isNotEmpty()){
                        one = prefix + one      //we removed confusion of starting "-" and now we are adding "-" back for operations
                    }

                    tvInput?.text = removezeroafterdot((one.toDouble() + two.toDouble()).toString())  //tvInput always take string
                }
                else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")  // splitting "99-1" int 99 and 1 by using "-" as splitter

                    var one = splitValue[0] //99
                    var two = splitValue[1] //1

                    if(prefix.isNotEmpty()){
                        one = prefix + one      //we removed confusion of starting "-" and now we are adding "-" back for operations
                    }

                    tvInput?.text = removezeroafterdot((one.toDouble() * two.toDouble()).toString())  //tvInput always take string
                }
                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")  // splitting "99-1" int 99 and 1 by using "-" as splitter

                    var one = splitValue[0] //99
                    var two = splitValue[1] //1

                    if(prefix.isNotEmpty()){
                        one = prefix + one      //we removed confusion of starting "-" and now we are adding "-" back for operations
                    }

                    tvInput?.text = removezeroafterdot((one.toDouble() / two.toDouble()).toString())  //tvInput always take string
                }

            }catch (e: ArithmeticException){        //it used to catch operation that are invalid i.e 1/0
                e.printStackTrace()
            }
        }
    }

    private fun removezeroafterdot(result: String): String{     //function to remove zero after dot i.e 8.0 to 8
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else {
            value.contains("/") || value.contains("*") || value.contains("+")|| value.contains("-")
        }
    }
}