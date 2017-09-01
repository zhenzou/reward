package ltd.yazz.reward.ui.adapter

import android.support.v7.widget.RecyclerView
import ltd.yazz.reward.util.orElse


abstract class BaseRecyclerAdapter<M, VH : BaseViewHolder<M>>() : RecyclerView.Adapter<VH>(), RefreshableAdapter<M> {

    protected var data: MutableList<M> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(list: MutableList<M>) : this() {
        this.data = list
    }

    protected var listener: BaseViewHolder.OnItemClickListener? = null

    override fun getItemCount(): Int {
        return data.size
    }

    open fun setOnItemClickListener(listener: BaseViewHolder.OnItemClickListener?) {
        this.listener = listener
    }

    override fun refresh() {
        notifyDataSetChanged()
    }

    override fun fill(data: List<M>) {
        this.data = data.toMutableList()
    }

    override fun add(t: M) {
        data.add(t)
        notifyItemInserted(itemCount)
    }

    override fun add(pos: Int, t: M) {
        data.add(pos, t)
        notifyItemChanged(pos)
    }

    override fun remove(pos: Int): M? {
        return if (data.size > pos) {
            val item = data.removeAt(pos)
            notifyItemRemoved(pos)
            item
        } else {
            null
        }
    }

    override fun update(pos: Int, new: M) {
        data[pos] = new
        notifyItemChanged(pos)
    }


    fun getItem(position: Int): M? {
        return data.get(position)
    }

    fun removeItem(item: M) {
        val pos = data.indexOf(item).orElse(-1)
        if (pos >= 0) remove(pos)
    }

}
