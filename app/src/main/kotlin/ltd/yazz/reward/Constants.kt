package ltd.yazz.reward


/**
 * Project:Reward
 * Create Time: 17-8-28.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */

object Constants{
    val STATE_NEW    = 11
    val STATE_DOING  = 21
    val STATE_DONE   = 31
    val STATE_CANCEL = 41

    val VERSION      = 1


    val DB_NAME = "reward.db"
    val PAGE_TASK = 0
    val PAGE_WISH = 1

    val TYPE_WISH    = 21
    val TYPE_TASK    = 11
    val TITLE_KEY    = "task_fragment_title"
    val TYPE_KEY     = "task_fragment_type"
    val TITLE_TASK   = "任务"
    val TITLE_WISH   = "心愿"

    val EMPTY_LIST_TYPE=11


    val TASK_OR_WISH_INTENT_KEY="task_or_wish"
    val NEW_TASK_OR_WISH_CODE  =1001
    val EDIT_TASK_OR_WISH_CODE =2001
    val NEW_TASK_OR_WISH_VALUE_KEY ="new_task_or_wish"
    val EDIT_TASK_OR_WISH_VALUE_KEY="edit_task_or_wish"
    val EDIT_TASK_OR_WISH_POSITION="edit_task_or_wish_pos"

    fun pos(type: Int): Int {
        return when (type) {
            Constants.TYPE_TASK -> Constants.PAGE_TASK
            Constants.TYPE_WISH -> Constants.PAGE_WISH
            else -> Constants.PAGE_TASK
        }
    }
}
