package ltd.yazz.reward.service

import ltd.yazz.reward.Constants
import ltd.yazz.reward.db.DbHelper
import ltd.yazz.reward.model.Persistent
import ltd.yazz.reward.model.TaskOrWish
import ltd.yazz.reward.util.toPersistent


/**
 * Project:Reward
 * Create Time: 17-8-30.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
class TaskOrWishService(private val helper: DbHelper) {

    private val table = "task_or_wish"

    fun findAllTask(): List<TaskOrWish> {
        return find("type=? and state =?", Constants.TYPE_TASK, Constants.STATE_NEW)
    }

    fun findAllWish(): List<TaskOrWish> {
        return find("type=? and state =?", Constants.TYPE_WISH, Constants.STATE_NEW)
    }

    private fun find(where: String, vararg args: Any): List<TaskOrWish> {
        val tasks = helper.query(table, where, *args)
        return tasks.map { task -> task as TaskOrWish }.toList()
    }

    fun addNewTaskOrWish(t: TaskOrWish): TaskOrWish {
        val id = helper.insert(t)
        return t.copy(_id = id)
    }

    fun editTaskOrWish(id: Long, value: Persistent): Int {
        return helper.update(table, value, "_id =?", id)
    }

    fun statistic(): Int {
        val list = find("state=?", Constants.STATE_DONE)
        return list.map { if (it.type == Constants.TYPE_WISH) -it.amount else it.amount }.sum()
    }

    fun finishTaskOrWish(id: Long): Int {
        return helper.update(table, mapOf("state" to Constants.STATE_DONE).toPersistent(), "_id =?", id)
    }
}