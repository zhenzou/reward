package ltd.yazz.reward.ui.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (inflater != null && layout() != 0) {
            return inflater.inflate(layout(), container, false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValue()
        initView(view)
    }

    @LayoutRes
    protected abstract fun layout(): Int

    protected abstract fun initValue()

    protected abstract fun initView(view: View)

    fun update() {}
}