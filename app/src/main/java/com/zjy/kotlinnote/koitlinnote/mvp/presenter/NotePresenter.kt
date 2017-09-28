package com.zjy.kotlinnote.koitlinnote.mvp.presenter

import android.app.Activity
import android.app.AlertDialog
import com.zjy.kotlinnote.koitlinnote.R
import com.zjy.kotlinnote.koitlinnote.callback.DBCallback
import com.zjy.kotlinnote.koitlinnote.mvp.model.NoteModel
import com.zjy.kotlinnote.koitlinnote.mvp.view.NoteView
import com.zjy.kotlinnote.koitlinnote.utils.UIUtils

/**
 * Created by zjy on QE.
 */
class NotePresenter constructor(baseView: NoteView) : DBCallback,IPresenter {
    var mNoteView = baseView
    var mNoteModel = NoteModel()
    var dialog: AlertDialog? = null
    override fun saveSuccess() {
        mNoteView.saveSuccess()
    }

    override fun saveFail() {
        mNoteView.saveFail()
    }

    override fun deleteSuccess() {
        mNoteView.deleteSuccess()
    }

    override fun deleteFail() {
        mNoteView.deleteFail()
    }

    fun save(noteName: String, noteType: String, noteContent: String) {
        mNoteModel.save(noteName, noteType, noteContent, this)
    }

    fun delete(noteName: String, noteType: String) {
        showDialog(noteName, noteType)
    }

    fun showDialog(str: String, noteType: String) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(mNoteView as? Activity).setTitle("提示").setMessage(UIUtils.getResources().getString(R.string.tip_delete, str)).setNegativeButton("删除", { dialog, which ->
                mNoteModel.delete(str, noteType, this)
                dialog.dismiss()
            }).setPositiveButton("取消", { dialog, _ -> dialog.dismiss() }).create()
        }
        dialog?.let { it.show() }
    }
    override fun onDestroy() {
        mNoteModel?.let { it = null!! }
        mNoteView?.let { it = null!! }
    }
}