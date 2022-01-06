package el.ka.waterlife

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import el.ka.waterlife.utils.round
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var percent: Double = 0.0
    private var needWater = 2000
    private var currentWater = 1600

    companion object {
        const val INDICATOR = "indicator"
        const val BOTTOM_BAR = "bottomBar"
    }

    private fun getCurrentNormaPercent(): Double {
        return currentWater.toDouble() / needWater.toDouble()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        percent = getCurrentNormaPercent()
        updateIndicatorSize()
        updateIndicators()
    }

    private fun updateIndicators() {
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
        val waterParams = getLayoutParams(INDICATOR, percent)
        indicator_value_water.layoutParams = waterParams

        val emptyParams = getLayoutParams(INDICATOR, 1 - percent)
        indicator_value_empty.layoutParams = emptyParams
    }

    @SuppressLint("SetTextI18n")
    private fun updateBottomBar() {
        val waterBarParams = getLayoutParams(BOTTOM_BAR, percent)
        bottomBarWater.layoutParams = waterBarParams

        val emptyBarParams = getLayoutParams(BOTTOM_BAR, 1 - percent)
        bottomBarEmpty.layoutParams = emptyBarParams

        val currentWaterString = (currentWater.toDouble() / 1000.0).round(1).toString()
        val lastWaterString = ((needWater - currentWater).toDouble() / 1000.0).toDouble().round(1).toString()
        val percentString = (percent*100).round(1).toString()

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
}