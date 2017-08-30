package ltd.yazz.reward

import android.app.Application

import ltd.yazz.reward.db.DbHelper
import ltd.yazz.reward.service.TaskOrWishService

/**
 * Project:Reward
 * Create Time: 17-8-28.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
class App : Application() {

    val helper = DbHelper(this)
    val taskOrWishService = TaskOrWishService(helper)

    companion object {
        var app: App? = null

        fun helper(): DbHelper {
            return app!!.helper
        }

        fun taskOrWishService(): TaskOrWishService {
            return app!!.taskOrWishService
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}