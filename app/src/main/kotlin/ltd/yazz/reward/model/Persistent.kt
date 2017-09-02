package ltd.yazz.reward.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Build

import ltd.yazz.reward.util.put
import ltd.yazz.reward.util.toTableName


/**
 * Project:Reward
 * Create Time: 17-8-30.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
interface Persistent {
    fun table(): String = this.javaClass.simpleName.toTableName()
    fun toContentValues(): ContentValues
    fun from(data: Cursor): Persistent
//    fun diff(data: T): T
}


class BasePersistent<K, V>(private val map: Map<K, V>) : Persistent {
    override fun table(): String {
        throw RuntimeException("not implement")
    }

    override fun toContentValues(): ContentValues {
        val value = ContentValues()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            map.forEach { k, v -> value.put(k.toString(), v as Any) }
        } else {
            map.entries.forEach({ (k, v) -> value.put(k.toString(), v as Any) })
        }
        return value
    }

    override fun from(data: Cursor): Persistent {
        throw RuntimeException("not implement")
    }
}