package ltd.yazz.reward.ui.activity


/**
 * Project:Reward
 * Create Time: 17-8-29.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_edit.*

import ltd.yazz.reward.App
import ltd.yazz.reward.Constants
import ltd.yazz.reward.R
import ltd.yazz.reward.model.TaskOrWish
import ltd.yazz.reward.util.Utils


/**
 * Project:Reward
 * Create Time: 17-8-29.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
class EditActivity : BaseActivity(), View.OnClickListener {


    companion object {
        const val TAG = "EditActivity"
    }

    private var type = Constants.TYPE_TASK
    private var pos = -1
    private var oldTask: TaskOrWish? = null

    private var isNew = true//默认是新建
    private var amountHint = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setup(intent ?: Intent())
    }

    override fun layout(): Int = R.layout.activity_edit

    override fun initValue(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        card_wrapper.setOnClickListener(this)

        fab.setOnClickListener({ _ ->
            val amount = item_amount.text.toString().toInt()
            if (amount < 0) {
                Utils.makeShortToast(this, "${amountHint}不能小于零")
                return@setOnClickListener
            }
            if (item_title.text.isBlank()) {
                Utils.makeShortToast(this, "名称不能为空")
                return@setOnClickListener
            }
            val task = when (type) {
                Constants.TYPE_WISH -> TaskOrWish.newWish(item_title.text.toString(), amount, item_desc.text.toString())
                else -> TaskOrWish.newTask(item_title.text.toString(), amount, item_desc.text.toString())
            }
            val newTask: TaskOrWish = if (!isNew) {
                val oldTask = oldTask!!
                Log.e(TAG, oldTask._id.toString())
                if (App.taskOrWishService().editTaskOrWish(oldTask._id, task) > 0) task.copy(_id = oldTask._id) else oldTask
            } else {
                App.taskOrWishService().addNewTaskOrWish(task)
            }
            Log.d(TAG, newTask.toString())
            val intent = Intent()
            intent.putExtra(Constants.NEW_TASK_OR_WISH_VALUE_KEY, newTask)
                    .putExtra(Constants.TYPE_KEY, type)
                    .putExtra(Constants.EDIT_TASK_OR_WISH_POSITION, pos)
            setResult(if (newTask._id > 0) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent)
            finish()
        })
    }

    override fun onClick(p0: View?) {
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun setup(intent: Intent) {
        oldTask = intent.getParcelableExtra(Constants.TASK_OR_WISH_INTENT_KEY)
        type = intent.getIntExtra(Constants.TYPE_KEY, Constants.TYPE_TASK)
        pos = intent.getIntExtra(Constants.EDIT_TASK_OR_WISH_POSITION, -1)
        if (oldTask != null) {
            isNew = false
            val task = oldTask!!
            item_title.setText(task.title)
            item_desc.setText(task.desc)
            item_amount.setText(task.amount.toString())
        }
        Log.i(TAG, oldTask?.toString().orEmpty())
        when (type) {
            Constants.TYPE_WISH -> {
                item_title_wrapper.hint = Constants.TITLE_WISH
                amountHint = "价值"
            }
            else -> {
                item_title_wrapper.hint = Constants.TITLE_TASK
                amountHint = "奖励"
            }
        }
        item_amount_wrapper.hint = amountHint
    }
}
