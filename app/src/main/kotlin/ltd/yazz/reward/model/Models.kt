package ltd.yazz.reward.model


/**
 * Project:Reward
 * Create Time: 17-8-30.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */

object Models {

    val models = mutableMapOf<String, Class<out Persistent>>("task_or_wish" to TaskOrWish::class.java)

    fun new(table: String): Persistent? {
        return models[table]?.newInstance()
    }
}