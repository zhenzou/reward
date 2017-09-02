package ltd.yazz.reward.model

import android.util.Log
import ltd.yazz.reward.util.toTableName


/**
 * Project:Reward
 * Create Time: 17-8-30.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */

object Models {

    private val models = mutableMapOf<String, Class<out Persistent>>()

    init {
        register(TaskOrWish::class.java)
    }

    fun <T : Persistent> register(clazz: Class<T>) {
        register(clazz.simpleName.toTableName(), clazz)
    }

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