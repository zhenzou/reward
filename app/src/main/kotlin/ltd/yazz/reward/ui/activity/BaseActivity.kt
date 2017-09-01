package ltd.yazz.reward.ui.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

/**
 * Project:Reward
 * Create Time: 17-8-29.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layout() != 0) setContentView(layout())
        initValue(savedInstanceState)
        initView()
    }

    @LayoutRes
    protected abstract fun layout(): Int

    protected abstract fun initValue(savedInstanceState: Bundle?)
    protected abstract fun initView()

}