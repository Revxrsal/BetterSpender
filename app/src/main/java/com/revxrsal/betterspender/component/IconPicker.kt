package com.revxrsal.betterspender.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.R
import com.revxrsal.betterspender.util.icons

@Composable
fun IconPicker(
    modifier: Modifier = Modifier,
    selected: String,
    onIconSelected: (String) -> Unit,
    filter: String? = null
) {
    val displayedIcons = remember(filter) {
        if (filter == null)
            icons
        else
            icons.filter { it.second.contains(filter, true) }
    }
    LazyHorizontalGrid(
        modifier = modifier,
        rows = GridCells.Fixed(6)
    ) {
        items(displayedIcons) {
            val asText = it.first.asText()
            IconButton(onClick = { onIconSelected(asText) }) {
                FaIcon(
                    faIcon = it.first,
                    tint = LocalContentColor.current
                )
            }
        }
    }
}

val Brands = Font(resId = R.font.fa_brands_400).toFontFamily()
val Solid = Font(resId = R.font.fa_solid_900).toFontFamily()
val Regular = Font(resId = R.font.fa_regular_400).toFontFamily()

@Composable
fun FaIcon(
    faIcon: FaIconType,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = LocalContentColor.current
) {
    FaIcon(
        faIcon = faIcon.asText(),
        modifier = modifier,
        size = size,
        tint = tint,
        fontFamily = getFontFamily(faIcon)
    )
}

@Composable
fun FaIcon(
    faIcon: String,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = LocalContentColor.current,
    fontFamily: FontFamily = Solid
) {
    val scaleFactor = LocalConfiguration.current.fontScale

    val scaleIndependentFontSize =
        scaleIndependentFontSize(sizeInDp = size, scaleFactor = scaleFactor)

    val faTextStyle = TextStyle(
        color = tint,
        fontFamily = fontFamily,
        fontSize = scaleIndependentFontSize
    )

    BasicText(
        text = faIcon,
        modifier = modifier,
        style = faTextStyle,
    )
}

private fun getFontFamily(faIconType: FaIconType): FontFamily {
    return when (faIconType) {
        is FaIconType.BrandIcon -> Brands
        is FaIconType.SolidIcon -> Solid
        is FaIconType.RegularIcon -> Regular
    }
}

/** @fun scaleIndependentFontSize: Since FA icons are font that requires Scale pixel
 * to render and will resize on device font settings. We want to keep icon size
 * constant to provided DP value so we calculate scale factor and cancel it if Any
 *
 * @materialIconOffset: FA icons at same dp taking more space then the Material Icon
 * Since we will be using both icons side by side this value offset Fa Icons to stay in line
 * with Material icons sizes.
 */
private fun scaleIndependentFontSize(sizeInDp: Dp, scaleFactor: Float): TextUnit {
    val materialIconOffset = 3.dp
    return ((sizeInDp - materialIconOffset).value / scaleFactor).sp
}

private fun Int.codePointToString() = this.toChar().toString()


fun FaIconType.asText() = src.toChar().toString()