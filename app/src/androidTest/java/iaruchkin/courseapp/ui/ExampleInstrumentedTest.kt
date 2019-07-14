package iaruchkin.courseapp.ui

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import iaruchkin.courseapp.R
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    //rule is initialized
    @Rule
    @JvmField
    public val rule = getRule()

    private val username_tobe_typed = "Ajesh"
    private val correct_password = "password"
    private val wrong_password = "passme123"

    private fun getRule(): ActivityTestRule<MainActivity> {
        Log.e("Initalising rule", "getting Mainactivity")
        return ActivityTestRule(MainActivity::class.java)
    }

//    @Before
//    fun init() {
//        val moduleFragment: ModuleFragment? = ModuleFragment()
//
//        rule.activity.supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.frame_list, moduleFragment!!)
//                .addToBackStack(null)
//                .commit()
//    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("iaruchkin.courseapp", appContext.packageName)
    }

    @Test
    fun login_success() {
        Log.e("@Test", "Performing login success test")
        Espresso.onView((withId(R.id.item_text)))
                .check(matches(withText("Employee Details:Id: 11111")))

//        Espresso.onView(withId(R.id.password))
//                .perform(ViewActions.typeText(correct_password))

//        Espresso.onView()
//                .perform(ViewActions.click())
//
//        Espresso.onView(withId(R.id.login_result))
//                .check(matches(withText(R.string.login_success)))
    }
}
