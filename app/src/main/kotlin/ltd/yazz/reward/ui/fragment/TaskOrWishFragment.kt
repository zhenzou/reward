package ltd.yazz.reward.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.main_task_list.view.*
import ltd.yazz.reward.App

import ltd.yazz.reward.Constants
import ltd.yazz.reward.R
import ltd.yazz.reward.model.TaskOrWish
import ltd.yazz.reward.model.newTask
import ltd.yazz.reward.ui.activity.EditActivity
import ltd.yazz.reward.ui.adapter.MainViewPageAdapter
import ltd.yazz.reward.ui.adapter.RefreshableAdapter
import ltd.yazz.reward.ui.adapter.TaskListAdapter

class TaskOrWishFragment : PageFragment(), RefreshableAdapter<TaskOrWish> {
    //    private val adapter: TaskListAdapter = TaskListAdapter(mutableListOf(newTask("测试", 10, "测试描述")))
//    private var adapter: TaskListAdapter = TaskListAdapter(emptyList<TaskOrWish>().toMutableList())
    private var adapter: TaskListAdapter = TaskListAdapter(App.taskOrWishService().findAllTask().toMutableList())

    private var title: String = Constants.TITLE_TASK
    private var type: Int = Constants.TYPE_TASK

    override fun layout(): Int = R.layout.main_task_list
    override fun getTitle(): String = this.title
    override fun getType(): Int = this.type

    override fun initValue() {
        val tasks = when (type) {
            Constants.TYPE_WISH -> App.taskOrWishService().findAllWish().toMutableList()
            else -> App.taskOrWishService().findAllTask().toMutableList()
        }
        fill(tasks)
    }

    override fun initView(view: View) {
        view.task_list.layoutManager = LinearLayoutManager(context)
        view.task_list.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        title = args?.getString(Constants.TITLE_KEY).toString()
        type = args?.getInt(Constants.TYPE_KEY) ?: 0
    }

    override fun onItemClick(view: View, position: Int) {
        val i = Intent()
        i.putExtra(Constants.TYPE_KEY, type)
        i.putExtra(Constants.TASK_OR_WISH_INTENT_KEY, adapter.getItem(position)!!)
        i.putExtra(Constants.EDIT_TASK_OR_WISH_POSITION, position)
        i.setClass(activity, EditActivity::class.java)
        startActivityForResult(i, Constants.EDIT_TASK_OR_WISH_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.EDIT_TASK_OR_WISH_CODE) {
            val task = data!!.getParcelableExtra<TaskOrWish>(Constants.NEW_TASK_OR_WISH_VALUE_KEY)
            val pos = data.getIntExtra(Constants.EDIT_TASK_OR_WISH_POSITION, -1)
            if (task != null && pos >= 0) {
                update(pos, task)
            }
        }
    }

    override fun onItemLongClick(view: View, position: Int) {
    }

    //TODO BASE
    override fun refresh() {
        adapter.notifyDataSetChanged()
    }

    override fun fill(data: List<TaskOrWish>) {
        adapter.data = data.toMutableList()
        adapter.notifyDataSetChanged()
    }

    override fun add(t: TaskOrWish) {
        adapter.data!!.add(t)
        adapter.notifyItemChanged(adapter.itemCount)
    }

    override fun add(pos: Int, t: TaskOrWish) {
        adapter.data!!.add(pos, t)
        adapter.notifyItemChanged(pos)
    }

    override fun remove(pos: Int): TaskOrWish? {
        val data = adapter.data!!
        return if (data.size > pos) {
            data.removeAt(pos)
        } else {
            null
        }
    }

    override fun update(pos: Int, new: TaskOrWish) {
        adapter.data!![pos] = new
        adapter.notifyItemChanged(pos)
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