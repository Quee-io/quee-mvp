package io.quee.mvp_demo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.bytedance.scene.Scene
import com.bytedance.scene.ui.SceneActivity
import com.bytedance.scene.ui.template.AppCompatScene

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 24, **Thu June, 2021**
 * Project *mvp-architecture* [https://quee.io]
 */
class SampleActivity : SceneActivity() {
    override fun getHomeSceneClass() = MainScene::class.java

    override fun supportRestore() = false
}

class MainScene : AppCompatScene() {
    private lateinit var mButton: Button
    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?,
    ): View? {
        val frameLayout = FrameLayout(requireSceneContext())
        mButton = Button(requireSceneContext())
        mButton.text = "Click"
        frameLayout.addView(mButton,
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT))
        return frameLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTitle("Main")
        toolbar?.navigationIcon = null
        mButton.setOnClickListener {
            navigationScene?.push(SecondScene())
        }
    }
}

class SecondScene : AppCompatScene() {
    private val mId: Int by lazy { View.generateViewId() }

    init {
        setSwipeEnabled(true)
    }

    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?,
    ): View? {
        val frameLayout = FrameLayout(requireSceneContext())
        frameLayout.id = mId
        return frameLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTitle("Second")
        add(mId, ChildScene(), "TAG")
    }
}

class ChildScene : Scene() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?,
    ): View {
        val view = View(requireSceneContext())
        view.setBackgroundColor(Color.GREEN)
        return view
    }
}
