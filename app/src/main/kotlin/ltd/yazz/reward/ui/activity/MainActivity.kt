package ltd.yazz.reward.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

import ltd.yazz.reward.Constants
import ltd.yazz.reward.R
import ltd.yazz.reward.model.TaskOrWish
import ltd.yazz.reward.model.newTask
import ltd.yazz.reward.ui.adapter.MainViewPageAdapter

/**
 * Project:Reward
 * Create Time: 17-8-29.
 * Description:
 * @author zzhen zzzhen1994@gmail.com
 * @version
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener, View.OnClickListener {


    companion object {
        const val TAG = "MainActivity"
    }

    private var type = Constants.TYPE_TASK
    private var title = Constants.TITLE_TASK

    override fun layout(): Int = R.layout.activity_main

    override fun initValue() {
    }

    override fun initView() {
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        view_pager.adapter = MainViewPageAdapter(this.supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
        view_pager.addOnPageChangeListener(this)
        fab.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            fab -> onAdd() //点击新建按钮
        }
    }

    private fun onAdd() {
        val i = Intent()
        i.putExtra(Constants.TYPE_KEY, type)
        i.setClass(this, EditActivity::class.java)
        startActivityForResult(i, Constants.NEW_TASK_OR_WISH_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.NEW_TASK_OR_WISH_CODE) {
            val task = data!!.getParcelableExtra<TaskOrWish>(Constants.NEW_TASK_OR_WISH_VALUE_KEY)
            val type = data.getIntExtra(Constants.TYPE_KEY, Constants.TYPE_TASK)
            val adapter = view_pager.adapter as MainViewPageAdapter
            if (task != null) {
                adapter.add(Constants.pos(type), task)
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> this.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_history -> view_pager.currentItem = Constants.PAGE_TASK
            R.id.nav_statistic -> view_pager.currentItem = Constants.PAGE_WISH
            R.id.nav_setting -> {
            }
            R.id.nav_feedback -> {
            }
            R.id.nav_info -> {
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPageSelected(position: Int) {

        Log.i(TAG, position.toString())
        Log.i(TAG, view_pager.adapter.getPageTitle(position).toString())
        val title = view_pager.adapter.getPageTitle(position).toString()
        when (title) {
            Constants.TITLE_TASK -> {
                setType(Constants.TYPE_TASK)
                setTitle(Constants.TITLE_TASK)
            }
            Constants.TITLE_WISH -> {
                setType(Constants.TYPE_WISH)
                setTitle(Constants.TITLE_WISH)
            }
        }
    }

    private fun setType(type: Int) {
        this.type = type
    }

    private fun setTitle(title: String) {
        this.title = title
    }


    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
}
