package com.revxrsal.betterspender.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.revxrsal.betterspender.R.drawable.minus
import com.revxrsal.betterspender.R.drawable.plus

@Composable
fun FloatCounter(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    min: Float = 0.01f,
    max: Float = Float.MAX_VALUE,
    width: Dp? = 100.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {
            onValueChange((value - 1).coerceIn(min, max))
        }) {
            Icon(
                painter = painterResource(minus),
                contentDescription = "Minus 1",
            )
        }
        OutlinedTextField(
            value = "%.2f".format(value),
            onValueChange = {
                val double = it.toFloatOrNull()
                if (double != null)
                    onValueChange(double.coerceIn(min, max))
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier.padding(5.dp).run {
                return@run if (width != null)
                    this.width(width)
                else
                    this
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Default
            ),
            singleLine = true
        )
        IconButton(onClick = {
            onValueChange((value + 1).coerceIn(min, max))
        }) {
            Icon(
                painter = painterResource(plus),
                contentDescription = "Plus 1",
            )
        }
    }
}
