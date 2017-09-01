package ltd.yazz.reward.model

import android.util.Log


/**
 * Project:Reward
 * Create Time: 17-8-30.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */

object Models {

    //    private val models = mutableMapOf<String, Class<out Persistent>>("task_or_wish" to TaskOrWish::class.java)
    private val models = mutableMapOf<String, Class<out Persistent>>()

    fun <T : Persistent> register(table: String, clazz: Class<T>) {
        if (table.isNotEmpty()) {
            Log.d("Models register", table)
            models[table] = clazz
        }
    }

    fun new(table: String): Persistent? {
        return models[table]?.newInstance()
    }
}