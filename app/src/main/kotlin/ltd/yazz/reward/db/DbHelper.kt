package ltd.yazz.reward.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import ltd.yazz.reward.Constants
import ltd.yazz.reward.model.Models
import ltd.yazz.reward.model.Persistent
import ltd.yazz.reward.model.TaskOrWish
import ltd.yazz.reward.util.andDo


/**
 * Project:Reward
 * Create Time: 17-8-29.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
class DbHelper(ctx: Context) : SQLiteOpenHelper(ctx, Constants.DB_NAME, null, Constants.VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = TaskOrWish.create()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //DO NOTHING
    }

    fun insert(t: Persistent): Long {
        val db = writableDatabase
        return db.use { it.insert(t.table(), null, t.toContentValues()) }
    }

    fun query(table: String): List<Persistent> {
        val db = readableDatabase
        db.use {
            val cursor = it.query(table, null, null, null, null, null, null, null)
            return cursor.map { Models.new(table)?.from(it) }
        }
    }

    fun query(table: String, where: String, vararg args: Any): List<Persistent> {
        val db = readableDatabase
        db.use {
            val cursor = it.query(table, null, where, args.map { it.toString() }.toTypedArray(), null, null, null, null)
            return cursor.map { Models.new(table)?.from(it) }
        }
    }

    fun update(table: String, value: Persistent, where: String, vararg args: Any): Int {
        val db = writableDatabase
        return db.use { it.update(table, value.toContentValues(), where, args.map { it.toString() }.toTypedArray()) }
    }

    fun delete(table: String, where: String, vararg args: Any): Int {
        val db = writableDatabase
        return db.use { it.delete(table, where, args.map { it.toString() }.toTypedArray()) }
    }

    private inline fun <T : Persistent> Cursor.map(f: (Cursor) -> T?): List<T> {
        val count = this.count
        val result = ArrayList<T>(count)
        Log.d("DbHelper", count.toString())
        if (count > 0) {
            while (this.moveToNext()) {
                val m = f(this)
                m?.andDo { result.add(m) }
            }
        }
        this.close()
        return result.toList()
    }
}