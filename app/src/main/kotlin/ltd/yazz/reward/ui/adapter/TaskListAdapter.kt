package ltd.yazz.reward.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_task_list.view.*

import ltd.yazz.reward.R
import ltd.yazz.reward.model.TaskOrWish

/**
 * Project:Reward
 * Create Time: 17-8-29.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
class TaskListAdapter(data: MutableList<TaskOrWish>) : BaseRecyclerAdapter<TaskOrWish, TaskListAdapter.TaskViewHolder>(data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_list, parent, false)
        return TaskViewHolder(view, listener)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) = holder.bind(getItem(position)!!)

    class TaskViewHolder(itemView: View, listener: BaseViewHolder.OnItemClickListener?) : BaseViewHolder<TaskOrWish>(itemView, listener) {

        override fun bind(m: TaskOrWish) {
            itemView.item_check.isChecked = false
            itemView.item_check.setOnClickListener(this)
            itemView.empty_hint.text = m.title
            itemView.item_desc.text = m.desc
            itemView.item_amount.text = m.amount.toString()
        }
    }
}