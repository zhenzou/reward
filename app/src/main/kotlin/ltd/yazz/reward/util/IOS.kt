package ltd.yazz.reward.util

import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream


/**
 * Project:Reward
 * Create Time: 17-9-10.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
object IOS {
    fun readAll(fp: String): String {
        val input = BufferedInputStream(FileInputStream(fp))
        val output = ByteArrayOutputStream()
        input.use { input.copyTo(output) }
        return output.toString()
    }
}