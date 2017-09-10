package ltd.yazz.reward.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_task.view.*
import ltd.yazz.reward.R
import ltd.yazz.reward.model.TaskOrWish
import ltd.yazz.reward.util.Utils
import ltd.yazz.reward.util.between

/**
 * Project:Reward
 * Create Time: 17-8-29.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
class TaskListAdapter(data: MutableList<TaskOrWish>) : BaseRecyclerAdapter<TaskOrWish, TaskListAdapter.TaskViewHolder>(data) {
    private var ctx: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        ctx = parent.context
        val view = LayoutInflater.from(ctx).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(ctx!!, view, listener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) = holder.bind(getItem(position)!!, position)

    class TaskViewHolder(private val ctx: Context, itemView: View, listener: BaseViewHolder.OnItemClickListener?) : BaseViewHolder<TaskOrWish>(itemView, listener) {

        override fun bind(m: TaskOrWish, position: Int) {
            itemView.item_check.isChecked = false
            itemView.item_check.setOnClickListener(this)
            itemView.empty_hint.text = m.title
            itemView.item_desc.text = m.desc
            itemView.item_amount.setTextColor(when {
                m.amount < 100 -> Utils.getColor(ctx, R.color.PURE_BLACK_700)
                m.amount.between(100, 500) -> Utils.getColor(ctx, R.color.PURE_GREEN_700)
                m.amount.between(500, 2000) -> Utils.getColor(ctx, R.color.PURE_BLUE_700)
                else -> Utils.getColor(ctx, R.color.PURE_RED_700)
            })
            itemView.item_amount.text = m.amount.toString()
        }
    }
}