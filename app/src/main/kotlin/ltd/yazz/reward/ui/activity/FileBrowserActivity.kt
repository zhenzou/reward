package ltd.yazz.reward.ui.activity;

import java.io.FileFilter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_file_browser.*

import ltd.yazz.reward.R
import ltd.yazz.reward.ui.adapter.BaseViewHolder
import ltd.yazz.reward.ui.adapter.FileInfoListAdapter
import ltd.yazz.reward.util.Utils
import ltd.yazz.reward.util.orElse

/**
 * Project:Reward
 * Create Time: 17-9-9.
 * Description:
 *
 * @author zzhen zzzhen1994@gmail.com
 */
class FileBrowserActivity : BaseActivity(), BaseViewHolder.OnItemClickListener {

    companion object {
        const val TAG = "FileBrowserActivity"
        val ROOT_FILE_KEY = "root_file_key"
        val ACTION_KEY = "file_browser_action_key"
        val RESULT_FILE_KEY = "root_file_key"
        val BACKUP_RESULT_CODE = 321
        val RESTORE_RESULT_CODE = 421
        val ACTION_BACKUP = 123
        val ACTION_RESTORE = 223

        fun start(from: Activity, path: String?, action: Int = ACTION_BACKUP) {
            val i = Intent()
            i.putExtra(ROOT_FILE_KEY, path)
            i.putExtra(ACTION_KEY, action)
            i.setClass(from, FileBrowserActivity::class.java)
            val code = if (action == ACTION_BACKUP) BACKUP_RESULT_CODE else RESTORE_RESULT_CODE
            from.startActivityForResult(i, code)
        }
    }

    private var adapter: FileInfoListAdapter? = null
    private var action = ACTION_BACKUP
    private var file: String? = null

    override fun layout(): Int = R.layout.activity_file_browser

    override fun initValue(savedInstanceState: Bundle?) {
        val path = intent.getStringExtra(ROOT_FILE_KEY).orElse(Utils.externalStorageBackupDir()?.absolutePath.orElse("/"))
        Log.d(TAG, "root:" + path)
        action = intent.getIntExtra(ACTION_KEY, ACTION_BACKUP)
        val filter = if (action == ACTION_BACKUP) FileFilter { it.isDirectory && !it.isHidden } else FileFilter { !it.isHidden }
        adapter = FileInfoListAdapter(root = path, filter = filter)

        file_list.adapter = adapter
    }

    override fun initView() {
        val adapter = adapter!!
        title = adapter.cur
        file_list.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener { view ->
            val intent = Intent()
            intent.putExtra(RESULT_FILE_KEY, if (isBackup()) adapter.cur else file)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        adapter.setOnItemClickListener(this)
    }

    override fun onItemLongClick(view: View, position: Int) {
    }

    override fun onItemClick(view: View, position: Int) {
        val item = adapter?.getItem(position)!!
        if (item.dir) {
            //暂时业务上保证,在backup中不会出现文件
            //TODO Refactor
            adapter?.enterDir(item.path)
            title = adapter?.cur
        } else {
            adapter?.selected = position
            file = item.path
        }
    }

    private fun isBackup(): Boolean {
        return action == ACTION_BACKUP
    }

    /**
     *TODO 保存位置
     */
    override fun onBackPressed() {
        val back = adapter?.back().orElse(false)
        if (!back) {
            finish()
        } else {
            title = adapter?.cur
        }
    }
}