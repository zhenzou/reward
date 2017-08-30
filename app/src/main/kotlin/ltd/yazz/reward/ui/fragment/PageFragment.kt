package ltd.yazz.reward.ui.fragment

import ltd.yazz.reward.ui.adapter.BaseViewHolder

abstract class PageFragment : BaseFragment(), BaseViewHolder.OnItemClickListener {

    abstract fun getTitle(): String
    abstract fun getType(): Int
}