package ltd.yazz.reward.model

import java.util.*
import android.content.ContentValues
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable

import ltd.yazz.reward.Constants
import ltd.yazz.reward.util.toTableName

/**
 * Project:Reward
 * Create Time: 17-8-29.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */

data class TaskOrWish(
        val _id: Long = -1,
        val title: String = "",
        val desc: String = "",
        val amount: Int = 0,
        val state: Int = 0, //任务状态
        val type: Int = 0, //类型，当前是 11 Task，21 Wish
        val lastView: Long = 0, //最后点击的时间
        val viewTimes: Int = 0, //点击次数
        val startTime: Long = 0,
        val endTime: Long = 0,
        val createTime: Long = 0,
        val updateTime: Long = 0
) : Parcelable, Persistent {

    override fun toContentValues(): ContentValues {
        val value = ContentValues()
        value.put("title", title)
        value.put("desc", desc)
        value.put("amount", amount)
        value.put("state", state)
        value.put("type", type)
        value.put("last_view", lastView)
        value.put("view_times", viewTimes)
        value.put("start_time", startTime)
        value.put("end_time", endTime)
        value.put("create_time", createTime)
        value.put("update_time", updateTime)
        return value
    }

    //TODO 反射
    override fun from(data: Cursor): TaskOrWish {
        val id = data.getLong(0)
        val title = data.getString(1)
        val desc = data.getString(2)
        val amount = data.getInt(3)
        val state = data.getInt(4)
        val type = data.getInt(5)
        val lastView = data.getLong(6)
        val viewTimes = data.getInt(7)
        val startTime = data.getLong(8)
        val endTime = data.getLong(9)
        val createTime = data.getLong(10)
        val updateTime = data.getLong(11)
        return TaskOrWish(id, title, desc, amount, state, type, lastView, viewTimes, startTime, endTime, createTime, updateTime)
    }

//    override fun diff(that: TaskOrWish): TaskOrWish {
//        val value = ContentValues()
//        val id = if (title != that.title) that.title else
//            if (desc != that.desc) value.put("desc", that.desc)
//        if (amount != that.amount) value.put("amount", that.amount)
//        if (state != that.state) value.put("state", that.state)
//        if (type != that.type) value.put("type", that.type)
//        if (lastView != that.lastView) value.put("lastView", that.lastView)
//        if (viewTimes != that.viewTimes) value.put("viewTimes", that.viewTimes)
//        if (startTime != that.startTime) value.put("startTime", that.startTime)
//        if (endTime != that.endTime) value.put("endTime", that.endTime)
//        if (createTime != that.createTime) value.put("createTime", that.createTime)
//        if (updateTime != that.updateTime) value.put("updateTime", that.updateTime)
//        return that
//    }

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readLong())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeInt(amount)
        parcel.writeInt(state)
        parcel.writeInt(type)
        parcel.writeLong(lastView)
        parcel.writeInt(viewTimes)
        parcel.writeLong(startTime)
        parcel.writeLong(endTime)
        parcel.writeLong(createTime)
        parcel.writeLong(updateTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskOrWish> {
        //        init {
        //            Models.register(TaskOrWish::class.java.simpleName.toTableName(), TaskOrWish::class.java)
//        }
        fun newTask(title: String, amount: Int, desc: String? = null): TaskOrWish {

            return TaskOrWish(
                    _id = 0,
                    title = title,
                    type = Constants.TYPE_TASK,
                    desc = desc.orEmpty(),
                    amount = amount,
                    state = Constants.STATE_NEW,
                    lastView = Date().time,
                    viewTimes = 0,
                    startTime = Date().time,
                    endTime = Date().time,
                    updateTime = Date().time,
                    createTime = Date().time
            )
        }

        fun newWish(title: String, amount: Int, desc: String? = null): TaskOrWish {

            return TaskOrWish(
                    _id = 0,
                    title = title,
                    type = Constants.TYPE_WISH,
                    desc = desc.orEmpty(),
                    amount = amount,
                    state = Constants.STATE_NEW,
                    lastView = Date().time,
                    viewTimes = 0,
                    startTime = Date().time,
                    endTime = Date().time,
                    updateTime = Date().time,
                    createTime = Date().time
            )
        }

//

        fun create(): String {
            val sql = "create table if not exists task_or_wish " +
                    "(" +
                    "_id integer primary key autoincrement, " +
                    "title textId, desc textId," +
                    "amount integer," +
                    "state integer," +
                    "type integer," +
                    "last_view integer," +
                    "view_times integer," +
                    "start_time integer," +
                    "end_time," +
                    "create_time," +
                    "update_time" +
                    ")"

            return sql
        }

        override fun createFromParcel(parcel: Parcel): TaskOrWish {
            return TaskOrWish(parcel)
        }

        override fun newArray(size: Int): Array<TaskOrWish?> {
            return arrayOfNulls(size)
        }
    }
}



