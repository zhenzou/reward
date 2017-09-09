package ltd.yazz.reward.model

import ltd.yazz.reward.Constants
import ltd.yazz.reward.util.extName
import java.io.File


/**
 * Project:Reward
 * Create Time: 17-9-9.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
class FileInfo(file: File) {

    companion object {
        val imgs = arrayOf("jpeg", "png", "gif", "bmp", "ico")
        val videos = arrayOf("mp4", "mkv", "rmvb", "avi")
        val texts = arrayOf("json", "ini", "log", "txt")
        val musics = arrayOf("mp3", "flac", "ape", "m4a")
    }

    val name: String = file.name
    val path: String = file.absolutePath
    val dir: Boolean = file.isDirectory

    private val ext: String by lazy { name.extName().toLowerCase() }
    fun type(): Int {
        return if (dir) {
            Constants.FILE_DIR
        } else {
            when (ext) {
                in imgs -> Constants.FILE_IMG
                in videos -> Constants.FILE_VIDEO
                in texts -> Constants.FILE_TEXT
                in musics -> Constants.FILE_MUSIC
                else -> Constants.FILE_UNKNOWN
            }
        }
    }
}