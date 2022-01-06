package el.ka.waterlife

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import el.ka.waterlife.fragments.HistoryFragment
import el.ka.waterlife.fragments.HomeFragment
import el.ka.waterlife.fragments.SettingsFragment
import el.ka.waterlife.utils.APP_ACTIVITY
import el.ka.waterlife.utils.changeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP_ACTIVITY = this
        val mHomeFragment = HomeFragment()
        val mHistoryFragment = HistoryFragment()
        val mSettingsFragment = SettingsFragment()

        changeFragment(mHomeFragment, true)

        main_bottom_menu.setOnItemSelectedListener { item ->
            var frg: Fragment? = null
            when (item.itemId) {
                R.id.menu_home_fragment -> {
                    frg = mHomeFragment
                }

                R.id.menu_history_fragment -> {
                    frg = mHistoryFragment
                }

                R.id.menu_settings_fragment -> {
                    frg = mSettingsFragment
                }
            }
            if (frg != null) changeFragment(frg, true)
            return@setOnItemSelectedListener true
        }
    }


}