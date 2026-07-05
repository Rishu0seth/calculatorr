package com.vcode.calculatorr

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcode.calculatorr.ui.theme.CalculatorrTheme
import com.vcode.calculatorr.ui.theme.NumberButtonColor
import com.vcode.calculatorr.ui.theme.OperatorButtonColor
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorrTheme {
                CalculatorrScreen()
                }
            }
        }
    }

@Composable
fun CalculatorrScreen(){
    var display by remember {
        mutableStateOf("0")
    }
    var firstNumber by remember {
        mutableStateOf("")
    }
    var operator by remember {
        mutableStateOf("")
    }
    var waitingForSecondNumber by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp)
    ) {
        DisplaySection(
            modifier = Modifier
                .weight(1f),
            display = display
        )
        ButtonSection(
            modifier = Modifier.weight(2f),
            onButtonClick = { symbol ->
               if (symbol == "AC") {
                   display = "0"
               }
               else if (symbol == ".") {
                   if (!display.contains(".")) {
                       display += "."
                    }
               }
               else if (symbol == "+"||
                        symbol == "-"||
                        symbol == "X"||
                        symbol == "÷") {

                   firstNumber = display
                   operator = symbol
                   waitingForSecondNumber = true

               }
               else if (symbol == "=") {

                       val result = when (operator) {
                           "+" -> firstNumber.toDouble() + display.toDouble()
                           "-" -> firstNumber.toDouble() - display.toDouble()
                           "X" -> firstNumber.toDouble() * display.toDouble()
                           "÷" -> firstNumber.toDouble() / display.toDouble()
                           else -> display.toDouble()
                       }

                       display = result.toString()

                       firstNumber = ""
                       operator = ""
                       waitingForSecondNumber = false

               }
               else if (waitingForSecondNumber) {
                   display = symbol
                   waitingForSecondNumber = false
               }
               else if (display == "0") {
                   display = symbol
               }
               else {
                   display += symbol
               }
            }
        )
    }
}

@Composable
fun DisplaySection(
    modifier: Modifier = Modifier,
    display: String
) {
    Box(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text (text = display,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    onButtonClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        verticalArrangement = SpaceEvenly
    ) {
        CalculatorrButtonRow(
            first = "AC",
            second = "+/-",
            third = "%",
            fourth = "÷",
            fourthColor = OperatorButtonColor,
            fourthTextColor = Color.White,
            firstOnClick = {
                onButtonClick("AC")
            },
            secondOnClick = {
                onButtonClick("+/-")
            },
            thirdOnClick = {
                onButtonClick("%")
            },
            fourthOnClick = {
                onButtonClick("÷")
            }
        )
        CalculatorrButtonRow(
            first = "7",
            second = "8",
            third = "9",
            fourth = "X",
            fourthColor = OperatorButtonColor,
            fourthTextColor = Color.White,
            firstOnClick = {
                onButtonClick("7")
            },
            secondOnClick = {
                onButtonClick("8")
            },
            thirdOnClick = {
                onButtonClick("9")
            },
            fourthOnClick = {
                onButtonClick("X")
            }
        )
        CalculatorrButtonRow(
            first = "4",
            second = "5",
            third = "6",
            fourth = "-",
            fourthColor = OperatorButtonColor,
            fourthTextColor = Color.White,
            firstOnClick = {
                onButtonClick("4")
            },
            secondOnClick = {
                onButtonClick("5")
            },
            thirdOnClick = {
                onButtonClick("6")
            },
            fourthOnClick = {
                onButtonClick("-")
            }
        )
        CalculatorrButtonRow(
            first = "1",
            second = "2",
            third = "3",
            fourth = "+",
            fourthColor = OperatorButtonColor,
            fourthTextColor = Color.White,
            firstOnClick = {
                onButtonClick("1")
            },
            secondOnClick = {
                onButtonClick("2")
            },
            thirdOnClick = {
                onButtonClick("3")
            },
            fourthOnClick = {
                onButtonClick("+")
            }
        )
        CalculatorrButtonRow(
            first = "0",
            second = ".",
            third  = "=",
            fourth = "",
            fourthColor = OperatorButtonColor,
            fourthTextColor = Color.White,
            firstOnClick = {
                onButtonClick("0")
            },
            secondOnClick = {
                onButtonClick(".")
            },
            thirdOnClick = {
                onButtonClick("=")
            }
        )
    }
}

@Composable
fun CalculatorrButton (
    symbol: String,
    backgroundColor: Color = NumberButtonColor,
    textColor: Color = Color.Black,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(48.dp))
            .clickable {
                onClick()
            },
    contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            color = textColor
        )
    }
}

@Composable 
fun CalculatorrButtonRow(
    first: String,
    second: String,
    third: String,
    fourth: String,
    firstColor: Color = NumberButtonColor,
    secondColor: Color = NumberButtonColor,
    thirdColor: Color = NumberButtonColor,
    fourthColor: Color = NumberButtonColor,

    firstTextColor: Color = Color.Black,
    secondTextColor: Color = Color.Black,
    thirdTextColor: Color = Color.Black,
    fourthTextColor: Color = Color.Black,

    firstOnClick: () -> Unit = {},
    secondOnClick: () -> Unit = {},
    thirdOnClick: () -> Unit = {},
    fourthOnClick: () -> Unit = {}

){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = SpaceEvenly
    ) {
        CalculatorrButton(symbol = first,
            backgroundColor = firstColor,
            textColor = firstTextColor,
            onClick = firstOnClick)
        CalculatorrButton(symbol = second,
            backgroundColor = secondColor,
            textColor = secondTextColor,
            onClick = secondOnClick)
        CalculatorrButton(symbol = third,
            backgroundColor = thirdColor,
            textColor = thirdTextColor,
            onClick = thirdOnClick)
        CalculatorrButton(symbol = fourth,
            backgroundColor = fourthColor,
            textColor = fourthTextColor,
            onClick = fourthOnClick)
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorrPreview() {
    CalculatorrTheme {
        CalculatorrScreen()
    }
}

