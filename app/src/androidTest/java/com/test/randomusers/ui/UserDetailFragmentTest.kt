package com.test.randomusers.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.test.randomusers.R
import com.test.randomusers.ui.userdetails.UserDetailsFragment
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UserDetailFragmentTest {

//    @Rule
//    @JvmField
//    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var fragmentTestRule: FragmentTestRule<*, UserDetailsFragment> = FragmentTestRule.create(UserDetailsFragment::class.java)

    @Test
    fun userDetailFragmentTest() {
        val recyclerView = onView(
            allOf(
                withId(R.id.user_recyclerview),
                childAtPosition(
                    withId(R.id.constraint),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val imageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(withId(R.id.app_bar))
                    )
                ),
                isDisplayed()
            )
        )
        imageButton.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withText("User details"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(withId(R.id.app_bar))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("User details")))

        val viewGroup = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.user_detail_card_view),
                        withParent(withId(R.id.constraint))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.user_avatar),
                withParent(withParent(withId(R.id.user_detail_card_view))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.user_full_name), withText("Mr. Mads Kvaale"),
                withParent(withParent(withId(R.id.user_detail_card_view))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Mr. Mads Kvaale")))

        val textView3 = onView(
            allOf(
                withId(R.id.user_gender_and_age), withText("Male | 61 yrs"),
                withParent(withParent(withId(R.id.user_detail_card_view))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Male | 61 yrs")))

        val textView4 = onView(
            allOf(
                withId(R.id.user_username), withText("crazyladybug872"),
                withParent(withParent(withId(R.id.user_detail_card_view))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("crazyladybug872")))

        val textView5 = onView(
            allOf(
                withId(R.id.user_email), withText("mads.kvaale@example.com"),
                withParent(withParent(withId(R.id.user_detail_card_view))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("mads.kvaale@example.com")))

        val textView6 = onView(
            allOf(
                withId(R.id.user_phone), withText("57504025"),
                withParent(withParent(withId(R.id.user_detail_card_view))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("57504025")))

        val textView7 = onView(
            allOf(
                withId(R.id.other_details), withText("Other details"),
                withParent(withParent(withId(R.id.other_details_card_view))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Other details")))

        val textView8 = onView(
            allOf(
                withId(R.id.location_tv), withText("Location"),
                withParent(withParent(withId(R.id.other_details_card_view))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("Location")))

        val textView9 = onView(
            allOf(
                withId(R.id.user_location), withText("7209, Rugveien, Namnå, Buskerud, Norway"),
                withParent(withParent(withId(R.id.other_details_card_view))),
                isDisplayed()
            )
        )
        textView9.check(matches(withText("7209, Rugveien, Namnå, Buskerud, Norway")))

        val textView10 = onView(
            allOf(
                withId(R.id.user_since_tv), withText("User since"),
                withParent(withParent(withId(R.id.other_details_card_view))),
                isDisplayed()
            )
        )
        textView10.check(matches(withText("User since")))

        val textView11 = onView(
            allOf(
                withId(R.id.user_since), withText("April 21, 2014 (7 yrs)"),
                withParent(withParent(withId(R.id.other_details_card_view))),
                isDisplayed()
            )
        )
        textView11.check(matches(withText("April 21, 2014 (7 yrs)")))

        val viewGroup2 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.other_details_card_view),
                        withParent(withId(R.id.constraint))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup2.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
