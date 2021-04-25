package by.bstu.vs.stpms.courier_application.model.util

import android.view.View
import android.view.ViewGroup


fun View.getAllChildren(): List<View> {
    val result = ArrayList<View>()
    if (this !is ViewGroup) {
        result.add(this)
    } else {
        for (index in 0 until this.childCount) {
            val child = this.getChildAt(index)
            result.addAll(child.getAllChildren())
        }
    }
    return result
}