package com.revxrsal.betterspender.screen.stats

import androidx.compose.runtime.Composable
import com.github.tehras.charts.bar.BarChartData.Bar
import com.github.tehras.charts.piechart.PieChartData.Slice
import com.revxrsal.betterspender.data.entity.ItemPurchase
import com.revxrsal.betterspender.util.colorGen
import java.time.LocalDate

val Weeks = listOf(
    1..7,
    8..14,
    15..21,
    22..31
)

@Composable
fun Items.sortByMonth(month: LocalDate): List<Bar> {
    val colorGen = colorGen()
    val byDate = mapByDate()
    val bars = mutableListOf<Bar>()

    for ((index, week) in Weeks.withIndex()) {
        val sum = sumWeek(byDate, week, month)
        bars.add(
            Bar(
                value = sum,
                color = colorGen.next,
                label = "Week ${index + 1}"
            )
        )
    }
    return bars
}

@Composable
fun Items.sortByMonthPie(month: LocalDate): Map<String, Slice> {
    val colorGen = colorGen()
    val slices = mutableMapOf<String, MutableFloat>()

    for (item in this) {
        if (item.purchasedAt.month != month.month || item.purchasedAt.year != month.year)
            continue
        slices.getOrPut(item.name) { MutableFloat() }.f += item.quantity
    }
    return slices.mapValues { Slice(it.value.f, colorGen.next) }
}

class MutableFloat(var f: Float = 0.0f)

private fun sumWeek(
    byDate: Map<LocalDate, List<ItemPurchase>>,
    week: IntRange,
    month: LocalDate
): Float {
    var sum = 0f
    for (i in week) {
        sum += byDate
            .getOrDefault(
                month.withDayOfMonth(i.coerceAtMost(month.lengthOfMonth())),
                emptyList()
            )
            .sumOf { (it.price * it.quantity).toDouble() }.toFloat()
    }
    return sum
}

private fun Items.mapByDate(): MutableMap<LocalDate, List<ItemPurchase>> {
    val byDate = mutableMapOf<LocalDate, List<ItemPurchase>>()
    for (item in this) {
        val list = byDate.getOrPut(item.purchasedAt) { mutableListOf() }
        (list as MutableList).add(item)
    }
    return byDate
}


typealias Items = List<ItemPurchase>