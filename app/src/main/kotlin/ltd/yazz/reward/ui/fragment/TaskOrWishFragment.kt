package ltd.yazz.reward.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import kotlinx.android.synthetic.main.list_task.view.*

import ltd.yazz.reward.App
import ltd.yazz.reward.Constants
import ltd.yazz.reward.R
import ltd.yazz.reward.model.TaskOrWish
import ltd.yazz.reward.ui.activity.EditActivity
import ltd.yazz.reward.ui.adapter.TaskListAdapter
import ltd.yazz.reward.util.Utils


class TaskOrWishFragment : PageFragment() {

    var adapter: TaskListAdapter = TaskListAdapter(mutableListOf())
    var listener: OnCreditsChangeListener? = null

    private var taskListView: RecyclerView? = null
    private var emptyTextHint: TextView? = null

    private var title: String = Constants.TITLE_TASK
    private var type: Int = Constants.TYPE_TASK

    override fun layout(): Int = R.layout.list_task
    override fun getTitle(): String = this.title
    override fun getType(): Int = this.type

    override fun initValue() {
        val tasks = when (type) {
            Constants.TYPE_WISH -> App.taskOrWishService().findAllWish().toMutableList()
            else -> App.taskOrWishService().findAllTask().toMutableList()
        }
        adapter.registerAdapterDataObserver(AdapterDataObserver())
        adapter.fill(tasks)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnCreditsChangeListener) {
            listener = context
        } else {
            throw RuntimeException("need a listener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun initView(view: View) {
        taskListView = view.task_list
        emptyTextHint = view.empty_hint
        emptyTextHint?.setText(if (type == Constants.TYPE_WISH) R.string.empty_wish_text else R.string.empty_task_text)

        view.task_list.layoutManager = LinearLayoutManager(context)
        view.task_list.adapter = adapter
        if (adapter.itemCount == 0) {
            taskListView?.visibility = View.GONE
            emptyTextHint?.visibility = View.VISIBLE
        } else {
            taskListView?.visibility = View.VISIBLE
            emptyTextHint?.visibility = View.GONE
        }
        adapter.setOnItemClickListener(this)
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        title = args?.getString(Constants.TITLE_KEY).toString()
        type = args?.getInt(Constants.TYPE_KEY) ?: Constants.TYPE_TASK
    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, view.javaClass.canonicalName)
        when (view) {
            is CheckBox -> confirmFinishTask(view, position)
            else -> {
                val i = Intent()
                i.putExtra(Constants.TYPE_KEY, type)
                i.putExtra(Constants.TASK_OR_WISH_INTENT_KEY, adapter.getItem(position)!!)
                i.putExtra(Constants.EDIT_TASK_OR_WISH_POSITION, position)
                i.setClass(activity, EditActivity::class.java)
                startActivityForResult(i, Constants.EDIT_TASK_OR_WISH_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.EDIT_TASK_OR_WISH_CODE) {
            val task = data!!.getParcelableExtra<TaskOrWish>(Constants.NEW_TASK_OR_WISH_VALUE_KEY)
            val pos = data.getIntExtra(Constants.EDIT_TASK_OR_WISH_POSITION, -1)
            if (task != null && pos >= 0) {
                val oldTask = adapter.getItem(pos)!!
                adapter.update(pos, task)
            }
        }
    }

    private fun confirmFinishTask(view: CheckBox, position: Int) {
        val task = adapter.getItem(position)!!
        val title = resources.getString(if (type == Constants.TYPE_WISH) R.string.finish_wish else R.string.finish_task)
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
                .setIcon(R.drawable.ic_confirmation_number_black_24dp)
                .setMessage("确定$title:${task.title}?")
                .setNegativeButton(R.string.cancel, { v, _ -> view.isChecked = false;v.dismiss() })
                .setPositiveButton(R.string.ok, { _, _ ->
                    if (App.taskOrWishService().editTaskOrWish(task._id, task.copy(state = Constants.STATE_DONE)) > 0) {
                        adapter.remove(position)
                        if (task.type == Constants.TYPE_WISH) {
                            listener?.onChange(-task.amount)
                        } else {
                            listener?.onChange(task.amount)
                        }
                        Utils.makeShortToast(activity, "成功$title")
                    }
                })
        builder.show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        if (type == Constants.TYPE_WISH) return //暂时不允许删除心愿
        val title = adapter.getItem(position)!!.title
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.cancel_task)
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setMessage("取消任务:$title?")
                .setNegativeButton(R.string.cancel, { v, _ -> v.dismiss() })
                .setPositiveButton(R.string.ok, { _, _ ->
                    val task = adapter.getItem(position)!!
                    if (App.taskOrWishService().editTaskOrWish(task._id, task.copy(state = Constants.STATE_CANCEL)) > 0) {
                        adapter.remove(position)
                        Utils.makeShortToast(activity, "取消成功")
                    }
                })
        builder.show()
    }

    inner class AdapterDataObserver : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            if (adapter.itemCount == 0) {
                taskListView?.visibility = View.GONE
                emptyTextHint?.visibility = View.VISIBLE
            } else {
                taskListView?.visibility = View.VISIBLE
                emptyTextHint?.visibility = View.GONE
            }
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            onChanged()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            onChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            onChanged()

        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            onChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            onChanged()
        }
    }

    interface OnCreditsChangeListener {
        fun onChange(credits: Int)
    }


    companion object {
        const val TAG = "TaskOrWishFragment"

        fun apply(title: String, type: Int): TaskOrWishFragment {
            val fragment = TaskOrWishFragment()
            val bundle = Bundle()
            bundle.putString(Constants.TITLE_KEY, title)
            bundle.putInt(Constants.TYPE_KEY, type)
            fragment.arguments = bundle
            return fragment
        }
    }
}