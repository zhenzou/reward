package ltd.yazz.reward.model

import com.google.gson.Gson
import java.io.FileWriter

import ltd.yazz.reward.util.IOS


/**
 * Project:Reward
 * Create Time: 17-9-3.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
class BackupInfo(val version: Int, val items: List<TaskOrWish>) {
    companion object {
        fun restore(f: String): BackupInfo {
            val json = IOS.readAll(f)
            return Gson().fromJson<BackupInfo>(json, BackupInfo::class.java)
        }
    }

    fun backup(fp: String): String {
        val fp = if (fp.endsWith("/")) fp else fp + "/"
        val fw = FileWriter(fp + "reward.bak")
        fw.use { fw.write(toJson()) }
        return fp
    }

    fun toJson(): String {
        return "{\"version\":$version,items:${Gson().toJson(items)}}"
    }


}