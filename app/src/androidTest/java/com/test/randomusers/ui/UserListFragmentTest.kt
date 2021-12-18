package com.test.randomusers.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.test.randomusers.R
import com.test.randomusers.ui.users.UserListFragment
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UserListFragmentTest {

    @Rule
    @JvmField
    var fragmentTestRule: FragmentTestRule<*, UserListFragment> = FragmentTestRule.create(UserListFragment::class.java)

    @Test
    fun userListFragmentTest() {
        val textView = onView(
            allOf(
                withText("Random Users"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(withId(R.id.app_bar))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Random Users")))

        val recyclerView = onView(
            allOf(
                withId(R.id.user_recyclerview),
                withParent(
                    allOf(
                        withId(R.id.constraint),
                        withParent(withId(R.id.nav_host_fragment))
                    )
                ),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.user_avatar),
                withParent(withParent(withId(R.id.card_view))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.user_full_name), withText("Devon Hansen"),
                withParent(withParent(withId(R.id.card_view))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Devon Hansen")))

        val textView3 = onView(
            allOf(
                withId(R.id.user_location), withText("The Colony, United States"),
                withParent(withParent(withId(R.id.card_view))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("The Colony, United States")))
    }
}
