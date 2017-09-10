package ltd.yazz.reward.ui.adapter

import java.io.File
import java.io.FileFilter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_file.view.*

import ltd.yazz.reward.Constants
import ltd.yazz.reward.R
import ltd.yazz.reward.model.FileInfo
import ltd.yazz.reward.util.Utils
import ltd.yazz.reward.util.toFileInfos

/**
 * Project:Reward
 * Create Time: 17-8-29.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
class FileInfoListAdapter(root: String, val filter: FileFilter? = null) : BaseRecyclerAdapter<FileInfo, FileInfoListAdapter.FileInfoViewHolder>(mutableListOf<FileInfo>()) {


    val cache = mutableMapOf<String, MutableList<FileInfo>>()
    var selected = -1
        set(value) {
            val old = field
            field = value
            if (old >= 0) {
                notifyItemChanged(old)
            }
            notifyItemChanged(value)
        }

    var cur = root

    init {
        enterDir(root)
    }

    private var ctx: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileInfoViewHolder {
        ctx = parent.context
        val view = LayoutInflater.from(ctx).inflate(R.layout.item_file, parent, false)
        return FileInfoViewHolder(ctx!!, view, listener)
    }

    fun enterDir(path: String, cache: Boolean = true) {
        var files = this.cache[path]
        if (files == null) {
            files = File(path).listFiles(filter).toFileInfos()
            if (cache) {
                this.cache[path] = files
            }
        }
        fill(files)
        selected = -1
        cur = path
    }

    /**
     * 不做检查,其他地方保证
     */
    fun back(): Boolean {
        if (cur == "/") return false
        val path = File(cur).parent
        enterDir(path)
        return true
    }

    override fun onBindViewHolder(holder: FileInfoViewHolder, position: Int) = holder.bind(getItem(position)!!, position)

    inner class FileInfoViewHolder(private val ctx: Context, itemView: View, listener: OnItemClickListener?) : BaseViewHolder<FileInfo>(itemView, listener) {

        override fun bind(m: FileInfo, pos: Int) {
            if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition == selected) itemView.setBackgroundColor(Utils.getColor(ctx, R.color.PURE_GRAY_300))
            else itemView.setBackgroundColor(Color.TRANSPARENT)
            itemView.file_name.text = m.name
            itemView.file_icon.setImageDrawable(when (m.type()) {
                Constants.FILE_DIR -> Utils.getDrawable(ctx, R.drawable.ic_folder_black_24dp)
                Constants.FILE_MUSIC -> Utils.getDrawable(ctx, R.drawable.ic_music_black_24dp)
                Constants.FILE_TEXT -> Utils.getDrawable(ctx, R.drawable.ic_text_black_24dp)
                Constants.FILE_VIDEO -> Utils.getDrawable(ctx, R.drawable.ic_movie_black_24dp)
                Constants.FILE_IMG -> Utils.getDrawable(ctx, R.drawable.ic_image_black_24dp)
                Constants.FILE_UNKNOWN -> Utils.getDrawable(ctx, R.drawable.ic_unknown_black_24dp)
                else -> Utils.getDrawable(ctx, R.drawable.ic_unknown_black_24dp)
            })
        }
    }
}