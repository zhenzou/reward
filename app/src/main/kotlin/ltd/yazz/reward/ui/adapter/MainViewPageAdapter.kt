package ltd.yazz.reward.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import ltd.yazz.reward.Constants
import ltd.yazz.reward.model.TaskOrWish
import ltd.yazz.reward.ui.fragment.TaskOrWishFragment

class MainViewPageAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm), RefreshableAdapter<TaskOrWish> {


    private val list: Array<TaskOrWishFragment> = arrayOf(TaskOrWishFragment(), TaskOrWishFragment.apply(Constants.TITLE_WISH, Constants.TYPE_WISH))

    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence = list[position].getTitle()

    override fun refresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fill(data: List<TaskOrWish>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(t: TaskOrWish) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(pos: Int, t: TaskOrWish) {
        list[pos].add(t)
    }

    override fun remove(pos: Int): TaskOrWish? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(pos: Int, new: TaskOrWish) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}