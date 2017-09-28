package com.zjy.kotlinnote.koitlinnote.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.zjy.kotlinnote.koitlinnote.R
import com.zjy.kotlinnote.koitlinnote.bean.NoteBean
import com.zjy.kotlinnote.koitlinnote.mvp.presenter.NotePresenter
import com.zjy.kotlinnote.koitlinnote.mvp.view.NoteView
import com.zjy.kotlinnote.koitlinnote.utils.KeyboardUtils
import com.zjy.kotlinnote.koitlinnote.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity(), NoteView {
    var isDelete = false
    var presenter = NotePresenter(this)
    override fun deleteSuccess() {
        ToastUtil.showShortToastSafe("delete success")
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun deleteFail() {
        ToastUtil.showShortToastSafe("delete fail")
    }

    override fun saveSuccess() {
        ToastUtil.showShortToastSafe("save success")
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun saveFail() {
        ToastUtil.showShortToastSafe("save fail")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        init()
    }

    private fun init() {
        setSupportActionBar(tb)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tb.setNavigationIcon(R.drawable.back)
        tb.setNavigationOnClickListener { finish() }
        rela_parent.setOnClickListener { KeyboardUtils.hideSoftInput(this@NoteActivity) }
        intent.getParcelableExtra<NoteBean>("select")?.let {
            note_title.setText(it.NoteName)
            note_content.setText(it.NoteContent)
            note_type.setText(it.NoteType)
            isDelete = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_delete -> {
                if (!"".equals(note_title.text.trim().toString()) && !"".equals(note_type.text.trim().toString()))
                    presenter.delete(note_title.text.trim().toString(), note_type.text.trim().toString())
            }
            R.id.action_complete -> {
                if (!"".equals(note_title.text.trim().toString())) {
                    if (!"".equals(note_type.text.trim().toString())) {
                        if (!"".equals(note_content.text.trim().toString())) {
                            presenter.save(note_title.text.trim().toString(), note_type.text.trim().toString(), note_content.text.trim().toString())
                        } else {
                            ToastUtil.showShortToastSafe(R.string.tip_content)
                        }
                    } else {
                        ToastUtil.showShortToastSafe(R.string.tip_type)
                    }
                } else {
                    ToastUtil.showShortToastSafe(R.string.tip_title)
                }
            }
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (isDelete) {
            menu?.findItem(R.id.action_delete)?.isVisible = true
            menu?.findItem(R.id.action_complete)?.isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }
}
