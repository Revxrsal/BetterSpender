package com.revxrsal.betterspender.screen.stats

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.revxrsal.betterspender.data.entity.ItemPurchase
import com.revxrsal.betterspender.viewmodel.PreferencesViewModel
import java.time.LocalDate

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MonthlySpending(month: LocalDate, items: List<ItemPurchase>) {
    val pref: PreferencesViewModel = hiltViewModel()
    val currency by pref.currency.collectAsState()
    Text(
        text = "Monthly Spending (${currency.symbol})",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
    )
    val data = items.sortByMonth(month = month)
    ItemsChart(data = data)

    SpendingGoalIndicator(
        data.sumOf { it.value.toDouble() }.toFloat()
    )

}

@Composable
private fun ItemsChart(data: List<BarChartData.Bar>) {
    BarChart(
        barChartData = BarChartData(bars = data),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .heightIn(max = 300.dp),
        xAxisDrawer = SimpleXAxisDrawer(
            axisLineColor = MaterialTheme.colorScheme.onSurface
        ),
        yAxisDrawer = SimpleYAxisDrawer(
            axisLineColor = MaterialTheme.colorScheme.onSurface,
            labelTextColor = MaterialTheme.colorScheme.onSurface,
            labelRatio = 3
        ),
        labelDrawer = SimpleValueDrawer(
            labelTextColor = MaterialTheme.colorScheme.onSurface,
            drawLocation = SimpleValueDrawer.DrawLocation.XAxis
        )
    )
}
