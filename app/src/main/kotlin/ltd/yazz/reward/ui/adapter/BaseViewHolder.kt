package ltd.yazz.reward.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<M> constructor(itemView: View, listener: OnItemClickListener? = null) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    private var listener: OnItemClickListener? = null
        set(value) {
            field = value
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

    init {
        if (listener != null) {
            this.listener = listener
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    abstract fun bind(m: M)

    override fun onClick(view: View) {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION) listener!!.onItemClick(view, position)
    }

    override fun onLongClick(view: View): Boolean {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION) listener!!.onItemLongClick(view, position)
        return true
    }
}
