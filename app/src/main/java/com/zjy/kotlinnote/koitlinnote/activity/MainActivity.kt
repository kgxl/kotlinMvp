package com.zjy.kotlinnote.koitlinnote.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.zjy.kotlinnote.koitlinnote.R
import com.zjy.kotlinnote.koitlinnote.adapter.NoteAdapter
import com.zjy.kotlinnote.koitlinnote.bean.NoteBean
import com.zjy.kotlinnote.koitlinnote.mvp.presenter.MainPresenter
import com.zjy.kotlinnote.koitlinnote.mvp.view.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView, MaterialSearchView.OnQueryTextListener, MaterialSearchView.SearchViewListener {

    val REQUEST = 111
    var presenter: MainPresenter = MainPresenter(this)
    var noteAdapter: NoteAdapter? = null
    var mDatas = ArrayList<NoteBean>()
    var mHandle = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg?.what == 1) {
                noteAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        presenter.selectAll()
    }

    private fun initView() {
        setSupportActionBar(tb)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        noteAdapter = NoteAdapter(R.layout.layout_note_list, mDatas)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = noteAdapter
        noteAdapter?.setEmptyView(R.layout.layout_note_empty, recycler)
        noteAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            var intent = Intent(this@MainActivity, NoteActivity::class.java)
            intent.putExtra("select", mDatas[position])
            startActivityForResult(intent, REQUEST)
        }
        search_view.setOnQueryTextListener(this)
        search_view.setOnSearchViewListener(this)
        search_view.setEllipsize(true)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //每次字符改变，进行搜索
        presenter.selectLike(newText)
        return true
    }

    override fun onSearchViewClosed() {
        recover()
    }

    override fun onSearchViewShown() {
    }

    fun notifyChange() {
        mHandle.sendEmptyMessage(1)
    }

    //恢复为全部
    fun recover() {
        presenter.selectAll()
        notifyChange()
    }

    override fun find(data: ArrayList<NoteBean>?, isAll: Boolean) {
        if (mDatas != null) mDatas.clear()
        data?.let {
            mDatas?.addAll(it)
        }
        notifyChange()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        search_view.setMenuItem(menu?.findItem(R.id.action_search))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_add -> startActivityForResult(Intent(this@MainActivity, NoteActivity::class.java), REQUEST)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST && resultCode == Activity.RESULT_OK) {
            presenter.selectAll()
        }
    }

    override fun onBackPressed() {
        if (search_view.isSearchOpen) {
            search_view.closeSearch()
        } else super.onBackPressed()
    }
}
