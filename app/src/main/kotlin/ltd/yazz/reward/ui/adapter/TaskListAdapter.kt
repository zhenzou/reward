package ltd.yazz.reward.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_task_item.view.*

import ltd.yazz.reward.R
import ltd.yazz.reward.model.TaskOrWish

class TaskListAdapter() : BaseRecyclerAdapter<TaskOrWish, TaskListAdapter.TaskViewHolder>() {

    constructor(data: MutableList<TaskOrWish>) : this() {
        super.data = data
    }

    override fun onCreateView(group: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(group.context).inflate(R.layout.list_task_item, group, false)
        return TaskViewHolder(view, listener)
    }

    override fun onBindView(holder: TaskViewHolder, position: Int) = holder.bind(getItem(position)!!)

    class TaskViewHolder(itemView: View, listener: BaseViewHolder.OnItemClickListener?) : BaseViewHolder<TaskOrWish>(itemView, listener) {

        override fun bind(m: TaskOrWish) {
            itemView.dialog_title.text = m.title
            itemView.task_desc.text = m.desc
            itemView.task_amount.text = m.amount.toString()
        }
    }

}