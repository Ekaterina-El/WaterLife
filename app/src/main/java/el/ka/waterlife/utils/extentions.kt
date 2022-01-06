package el.ka.waterlife.utils

import kotlin.math.round

fun Double.round(decimals: Int = 2): Double {
    var mult = 1.0;
    repeat(decimals) { mult *= 10 }
    return round(this * mult) / mult
}