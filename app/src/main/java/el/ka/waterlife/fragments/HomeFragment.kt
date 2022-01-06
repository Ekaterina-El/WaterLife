package el.ka.waterlife.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import el.ka.waterlife.R
import el.ka.waterlife.utils.round
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var days: ArrayList<String>
    private lateinit var calendar: Calendar

    private var lastWater: Double = 0.0
    private var percent: Double = 0.0
    private var percentFull: Double = 0.0
    private var needWater = 2000
    private var currentWater = 0

    companion object {
        const val INDICATOR = "indicator"
        const val BOTTOM_BAR = "bottomBar"
    }

    private fun getCurrentNormaPercent(): Double {
        return currentWater.toDouble() / needWater.toDouble()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCalendar()

        updateIndicatorSize()
        updateIndicators()

        btn_add_water.setOnClickListener {
            addWater()
        }
    }

    override fun onResume() {
        super.onResume()

        getDate()
    }

    private fun addWater() {
        currentWater += 250
        calculateLastWater()
        updateIndicators()
    }

    private fun calculateLastWater() {
        lastWater = (needWater - currentWater).toDouble()
        if (lastWater <= 0) lastWater = 0.0
    }

    private fun updateIndicators() {
        calculateLastWater()
        percentFull = if (lastWater == 0.0) 1.0 else getCurrentNormaPercent()
        percent = getCurrentNormaPercent()
        updateIndicator()
        updateBottomBar()
    }

    private fun getLayoutParams(type: String, weight: Double): LinearLayout.LayoutParams {
        return when (type) {
            INDICATOR -> LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                weight.toFloat()
            )

            BOTTOM_BAR -> LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                weight.toFloat()
            )
            else -> LinearLayout.LayoutParams(
                0,
                0,
                0f
            )
        }
    }

    private fun updateIndicator() {
        val waterParams = getLayoutParams(INDICATOR, percentFull)
        indicator_value_water.layoutParams = waterParams

        val emptyParams = getLayoutParams(INDICATOR, 1 - percentFull)
        indicator_value_empty.layoutParams = emptyParams
    }

    @SuppressLint("SetTextI18n")
    private fun updateBottomBar() {
        val waterBarParams = getLayoutParams(BOTTOM_BAR, percentFull)
        bottomBarWater.layoutParams = waterBarParams

        val emptyBarParams = getLayoutParams(BOTTOM_BAR, 1 - percentFull)
        bottomBarEmpty.layoutParams = emptyBarParams

        val currentWaterString = (currentWater.toDouble() / 1000.0).round(2).toString()

        val lastWaterString = (lastWater / 1000.0).round(2).toString()
        val percentString = (percent * 100).roundToInt().toString()

        bottom_bar_current_water.text = getString(R.string.currentWater, currentWaterString)
        bottom_bar_current_percentage.text = "$percentString%"
        bottom_bar_last_water.text = getString(R.string.last_water, lastWaterString)
    }

    private fun updateIndicatorSize() {
        indicator_person.post {
            val width = indicator_person.measuredWidth
            val height = indicator_person.measuredHeight - 2

            val p = indicator_value.layoutParams
            p.width = width
            p.height = height
            indicator_value.layoutParams = p
        }

    }

    fun initCalendar() {
        calendar = Calendar.getInstance()

        days = arrayListOf<String>(
            getString(R.string.sunday),
            getString(R.string.monday),
            getString(R.string.tuesday),
            getString(R.string.wednesday),
            getString(R.string.thursday),
            getString(R.string.friday),
            getString(R.string.saturday),
        )
    }

    fun getDate() {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        val sdf = SimpleDateFormat("hh:mm a")
        val time = sdf.format(calendar.time)

        clock_day.text = day.toString()
        clock_day_of_week.text = days[dayOfWeek - 1]
        clock_time.text = time
    }
}