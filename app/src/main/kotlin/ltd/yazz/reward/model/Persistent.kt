package ltd.yazz.reward.model

import android.content.ContentValues
import android.database.Cursor


/**
 * Project:Reward
 * Create Time: 17-8-30.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
interface Persistent {
    fun table(): String
    fun toContentValues(): ContentValues
    fun from(data: Cursor): Persistent
//    fun diff(data: T): T
}