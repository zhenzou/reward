package ltd.yazz.reward.util;

import android.content.ContentValues
import ltd.yazz.reward.model.BasePersistent
import ltd.yazz.reward.model.FileInfo
import ltd.yazz.reward.model.Persistent
import java.io.File

/**
 * Project:Reward
 * Create Time: 17-9-9.
 * Description: All ext fun
 *
 * @author zzhen zzzhen1994@gmail.com
 */

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

fun String?.extName(): String {
    return when (this) {
        null -> ""
        else -> {
            val i = this.lastIndexOf('.')
            if (i < 0) {
                this
            } else {
                this.substring(i + 1)
            }
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

fun Int.between(left: Int, right: Int): Boolean {
    return this in (left)..(right - 1)
}

fun String.zeroOrInt(): Int {
    return try {
        toInt()
    } catch (e: NumberFormatException) {
        return 0
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

fun Array<File>?.toFileInfos(): MutableList<FileInfo> {
    return when (this) {
        null -> mutableListOf()
        else -> {
            this.map { FileInfo(it) }.toMutableList()
        }
    }
}