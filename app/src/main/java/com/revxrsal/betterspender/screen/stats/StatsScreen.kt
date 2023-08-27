package com.revxrsal.betterspender.screen.stats

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.PieChartData.Slice
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import com.revxrsal.betterspender.R
import com.revxrsal.betterspender.component.Today
import com.revxrsal.betterspender.viewmodel.DatabaseViewModel
import java.time.LocalDate

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StatsScreen(
    database: DatabaseViewModel = hiltViewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        val items by database.items.collectAsState()
        var month by remember { mutableStateOf(Today.withDayOfMonth(1)) }
        MonthPicker(
            month = month,
            onMonthSelected = { month = it }
        )
        Crossfade(
            targetState = items.count {
                it.purchasedAt.monthValue == month.monthValue
                        && it.purchasedAt.year == month.year
            } == 0
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if (it) {
                    Text(
                        text = "No data found for this month",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.padding(vertical = 20.dp))
                    Image(
                        painter = painterResource(R.drawable.money),
                        contentDescription = "Nothing here",
                        modifier = Modifier.size(180.dp)
                    )
                } else {
                    MonthlySpending(month = month, items = items)
                    TopItems(month = month, items = items)
                }
            }
        }
    }
}

@Composable
fun TopItems(month: LocalDate, items: Items) {
    Text(
        text = "Top Items",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 25.dp, bottom = 10.dp)
    )
    val data = items.sortByMonthPie(month = month)
    TopItemsChart(slices = data)
}

@Composable
private fun TopItemsChart(slices: Map<String, Slice>) {
    val sorted = remember(slices) {
        slices.asSequence()
            .sortedByDescending { it.value.value }
            .map { it.key to it.value }
            .toMap()
    }
    val sum = remember(slices) {
        slices.values.sumOf { it.value.toDouble() }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        PieChart(
            pieChartData = PieChartData(slices = sorted.values.toList()),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .heightIn(max = 300.dp),
            sliceDrawer = SimpleSliceDrawer(
                sliceThickness = 30f
            )
        )
        for ((item, slice) in sorted) {
            val (value, color) = slice
            val p = value * 100 / sum
            val percent = if (p % 1 == 0.0)
                p.toInt().toString()
            else
                "%.1f".format(p)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.circle),
                    contentDescription = item,
                    tint = color,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = "$percent% - $item",
                    modifier = Modifier.padding(10.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}