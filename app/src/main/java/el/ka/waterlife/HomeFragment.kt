package el.ka.waterlife

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var needWater = 2000
    private var currentWater = 1600

    private fun getCurrentNormaPercent(): Float {
        return currentWater.toFloat() / needWater.toFloat()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateWidth()
        updateIndicator()
    }

    private fun getLayoutParamsIndicator(weight: Float): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            weight
        )
    }

    private fun updateIndicator() {
        val percent = getCurrentNormaPercent()

        val waterParams = getLayoutParamsIndicator(percent)
        indicator_value_water.layoutParams = waterParams

        val emptyParams = getLayoutParamsIndicator(1 - percent)
        indicator_value_empty.layoutParams = emptyParams
    }

    private fun updateWidth() {
        indicator_person.post {
            val width = indicator_person.measuredWidth
            val p = indicator_value.layoutParams
            p.width = width
            indicator_value.layoutParams = p
        }

    }
}