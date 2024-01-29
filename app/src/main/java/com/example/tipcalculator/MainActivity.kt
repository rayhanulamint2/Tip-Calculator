package com.example.tipcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.io.StringReader
import java.text.NumberFormat
import java.text.NumberFormat.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}
@Composable
private fun calculateTip(roundUp: Boolean,amount: Double,tipPercent: Double = 15.0):String{
    var tip: Double = tipPercent/100*amount
    if(roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}
//@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(@DrawableRes leadingIcon:Int,
                    label:String,
                    amountInput:String,
                    onvaluechange:(String)->Unit,
                    keyboardoptions:KeyboardOptions,
                    modifier: Modifier = Modifier
){

    TextField(
        keyboardOptions = keyboardoptions,
        singleLine = true,
        value = amountInput,
//        leadingIcon = Icon(painter = painterResource(id = leadingIcon), contentDescription = null),
        label = {
                Text(text = label)
        },
        onValueChange = onvaluechange,
        modifier = modifier
    )
}
@Composable
fun RoundTheTipRow(roundUp:Boolean,onRoundUpChanged:(Boolean)->Unit,modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        print("tanvir")
        Text(text = stringResource(id = R.string.round_up_tip))
        Switch( modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End),
            checked = roundUp, onCheckedChange = onRoundUpChanged
        )
    }
}

@Composable
fun TipTimeLayout(){
    var amountInput by remember{ mutableStateOf("")}
    var percentageInput by remember{ mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }
    val amount: Double = amountInput.toDoubleOrNull() ?: 0.0
    val percentage = percentageInput.toDoubleOrNull()?:0.0
    val tip = calculateTip(roundUp,amount = amount,percentage)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(40.dp)
            .verticalScroll(rememberScrollState())
        ) {
        print("Tanvir")
        Text(
            text = stringResource(id = R.string.calculate_tip),
            fontSize = 20.sp ,
            modifier = Modifier.align(alignment = Alignment.Start)
        )
        EditNumberField(
            R.drawable.payments_fill0_wght400_grad0_opsz24,stringResource(id = R.string.bill_amount),amountInput,{amountInput = it},
            KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth())
        EditNumberField(R.drawable.percent_fill0_wght400_grad0_opsz24,stringResource(id = R.string.how_was_the_service),amountInput = percentageInput , onvaluechange = {percentageInput = it},KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),modifier = Modifier
            .padding(bottom = 32.dp)
            .fillMaxWidth())



        RoundTheTipRow(roundUp = roundUp, onRoundUpChanged = {roundUp = it})
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(id = R.string.tip_amount,tip),
            fontSize = 40.sp,
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }

}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {
        TipTimeLayout()
    }
}