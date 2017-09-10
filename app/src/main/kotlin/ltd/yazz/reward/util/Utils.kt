package ltd.yazz.reward.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import java.io.File

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

    fun getColor(ctx: Context, resId: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) ctx.getColor(resId) else ctx.resources.getColor(resId)
    }

    fun getString(ctx: Context, resId: Int): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) ctx.getString(resId) else ctx.resources.getString(resId)
    }

    fun getDrawable(ctx: Context, resId: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) ctx.getDrawable(resId) else ctx.resources.getDrawable(resId)
    }


    /**
     *Returns path end with /
     */
    fun externalStorageDirectory(): String? {
        val state = Environment.getExternalStorageState()
        return if (state == Environment.MEDIA_MOUNTED) {
            Environment.getExternalStorageDirectory().absolutePath + "/"
        } else {
            null
        }
    }

    fun externalStorageDirectoryFile(): File? {
        val state = Environment.getExternalStorageState()
        return if (state == Environment.MEDIA_MOUNTED) {
            Environment.getExternalStorageDirectory()
        } else null
    }

    fun externalStorageBackupDir(): File? {
        val path = externalStorageDirectory()
        return if (path == null) {
            null
        } else {
            File(path + "backups")
        }
    }
}