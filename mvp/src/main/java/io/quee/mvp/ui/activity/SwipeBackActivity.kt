package io.quee.mvp.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import io.quee.mvp.R
import io.quee.mvp.swipe.SwipeBackLayout
import io.quee.mvp.swipe.SwipeBackLayout.DragEdge

open class SwipeBackActivity : AppCompatActivity() {

    private var swipeBackLayout: SwipeBackLayout? = null
    private var ivShadow: ImageView? = null

    protected open fun addSwipeLayout() = false

    override fun setContentView(layoutResID: Int) {
        val container: View = initiateRootView()
        super.setContentView(container)
        val view = LayoutInflater.from(this).inflate(layoutResID, null)
        swipeBackLayout?.apply {
            addView(view)
            customize()
        }
    }

    private fun initiateRootView() =
        RelativeLayout(this).apply {
            ivShadow = ImageView(this@SwipeBackActivity).apply {
                setBackgroundColor(resources.getColor(R.color.black_p50))
            }
            swipeBackLayout = SwipeBackLayout(this@SwipeBackActivity).apply {
                setEnablePullToBack(addSwipeLayout())
                setDragEdge(DEFAULT_DRAG_EDGE)
                setOnSwipeBackListener { _, fractionScreen ->
                    ivShadow?.apply {
                        alpha = 1 - fractionScreen
                    }
                }
            }
            RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            ).apply {
                addView(ivShadow, this)
            }
            addView(swipeBackLayout)
        }

    protected open fun SwipeBackLayout.customize() {

    }

    companion object {
        private val DEFAULT_DRAG_EDGE = DragEdge.LEFT
    }
}