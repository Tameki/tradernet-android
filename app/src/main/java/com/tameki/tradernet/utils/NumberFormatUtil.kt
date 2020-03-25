package com.tameki.tradernet.utils

import java.text.DecimalFormat

fun Double.format(): String = FormatUtil.defaultFormat.format(this)

object FormatUtil {
    val defaultFormat = DecimalFormat("###,###,###,##0.00########")
}