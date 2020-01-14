package com.connected.theapp

import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


@Suppress("unused")
class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(
        position: Int,
        targetViewId: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {

            var resources: Resources? = null
            var childView: View? = null

            override fun describeTo(description: Description) {
                var idDescription = recyclerViewId.toString()
                if (resources != null) {
                    idDescription = try {
                        resources!!.getResourceName(recyclerViewId)
                    } catch (var4: NotFoundException) {
                        "$recyclerViewId (resource name not found)"
                    }
                }
                description.appendText("with id: $idDescription")
            }

            public override fun matchesSafely(view: View): Boolean {
                resources = view.resources
                if (childView == null) {
                    val recyclerView =
                        view.rootView.findViewById<View>(recyclerViewId) as RecyclerView
                    childView =
                        if (position < 0 || recyclerView.size <= position) {
                            return false
                        } else if (recyclerView.id == recyclerViewId) {
                            recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                        } else {
                            return false
                        }
                }
                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView =
                        childView!!.findViewById<View>(targetViewId)
                    view === targetView
                }
            }
        }
    }

}