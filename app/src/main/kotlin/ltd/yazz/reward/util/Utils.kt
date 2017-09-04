package ltd.yazz.reward.util

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

import ltd.yazz.reward.model.BasePersistent
import ltd.yazz.reward.model.Persistent

/**
 * Project:Reward
 * Create Time: 17-8-28.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
object Utils {
    fun makeShortToast(ctx: Context, text: String) {
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show()
    }

    fun makeLongToast(ctx: Context, text: String) {
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show()
    }

    fun makeShortSnack(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
    }

    fun makeLongSnack(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
    }

    /**
     *Returns path with /
     */
    fun externalStorageDirectory(): String? {
        val state = Environment.getExternalStorageState()
        if (state == Environment.MEDIA_MOUNTED) {
            return Environment.getExternalStorageDirectory().absolutePath + "/"
        }
        return null
    }
}

fun Any?.isZero(): Boolean {
    return when (this) {
        String -> this == ""
        Int, Long -> this == 0
        Float, Double -> this == 0.0
        else -> this == null
    }
}

fun String?.toTableName(): String {
    return when (this) {
        null -> ""
        else -> {
            val builder = StringBuilder(this.orEmpty().length)
            this.forEachIndexed { i, it ->
                if (it.isUpperCase()) {
                    builder.append(if (i == 0) it.toLowerCase() else "_${it.toLowerCase()}")
                } else {
                    builder.append(it)
                }
            }
//            this.foldIndexed("", { i, acc, c -> acc + (if (c.isUpperCase() && i > 0) "_${c.toLowerCase()}" else c.toLowerCase()) })
            builder.toString()
        }
    }
}

fun <K, V> Map<K, V>.toPersistent(): Persistent {
    return BasePersistent(this)
}

fun ContentValues.put(key: String, any: Any) {
    when (any) {
        is String -> put(key, any)
        is Int -> put(key, any)
        is Long -> put(key, any)
        is Byte -> put(key, any)
        is Float -> put(key, any)
        is Double -> put(key, any)
        is Boolean -> put(key, any)
        is ByteArray -> put(key, any)
    }
}

fun <T> T?.orElse(defaultValue: T): T {
    return this ?: defaultValue
}

fun <T> T?.orDo(f: () -> Unit) {
    if (this == null) f()
}

fun <T> T?.andDo(f: () -> Unit) {
    if (this != null) f()
}