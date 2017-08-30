package ltd.yazz.reward.ui.adapter


/**
 * Project:Reward
 * Create Time: 17-8-29.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */

interface RefreshableAdapter<T> {
    fun refresh()
    fun fill(data: List<T>)
    fun add(t: T)
    fun add(pos: Int, t: T)
    fun update(pos: Int, t: T)
    fun remove(pos: Int): T?
}