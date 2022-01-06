package el.ka.waterlife.utils

import androidx.fragment.app.Fragment
import el.ka.waterlife.MainActivity
import el.ka.waterlife.R

lateinit var APP_ACTIVITY: MainActivity

fun changeFragment(fragment: Fragment, addToBackStack: Boolean) {
    if (addToBackStack) {
        APP_ACTIVITY.supportFragmentManager
            .beginTransaction()
            .replace(R.id.dataContainer, fragment)
            .addToBackStack(null)
            .commit()
    } else {
        APP_ACTIVITY.supportFragmentManager
            .beginTransaction()
            .add(R.id.dataContainer, fragment)
            .commit()
    }
}
