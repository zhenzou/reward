package ltd.yazz.reward.ui.activity;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_file_browser.*
import ltd.yazz.reward.Constants
import java.io.FileFilter

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
        val RESULT_FILE_KEY = "root_file_key"
        val RESULT_CODE = 321

        fun start(from: Activity, path: String) {
            val i = Intent()
            i.putExtra(ROOT_FILE_KEY, path)
            i.setClass(from, FileBrowserActivity::class.java)
            from.startActivityForResult(i, RESULT_CODE)
        }
    }

    private val root = Utils.externalStorageBackupDir()
    private var adapter: FileInfoListAdapter? = null

    override fun layout(): Int = R.layout.activity_file_browser

    override fun initValue(savedInstanceState: Bundle?) {
        Log.d(TAG, root?.absolutePath)

        adapter = FileInfoListAdapter(root = root?.absolutePath.orEmpty(), filter = FileFilter { it.isDirectory && !it.isHidden })
        file_list.adapter = adapter
    }

    override fun initView() {
        val adapter = adapter!!
        title = adapter.cur
        file_list.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener { view ->
            val intent = Intent()
            intent.putExtra(RESULT_FILE_KEY, adapter.cur)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        adapter.setOnItemClickListener(this)
    }

    override fun onItemLongClick(view: View, position: Int) {
    }

    override fun onItemClick(view: View, position: Int) {
        val item = adapter?.getItem(position)!!
        adapter?.enterDir(item.path)
        title = adapter?.cur
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