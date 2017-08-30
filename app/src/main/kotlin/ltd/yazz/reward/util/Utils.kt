package ltd.yazz.reward.util

import android.content.Context
import android.widget.Toast

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
}

fun Any?.isZero(): Boolean {
    return when (this) {
        String -> this == ""
        Int, Long -> this == 0
        Float, Double -> this == 0.0
        else -> this == null
    }
}