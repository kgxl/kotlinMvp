package com.zjy.kotlinnote.koitlinnote.mvp.presenter

import com.zjy.kotlinnote.koitlinnote.bean.NoteBean
import com.zjy.kotlinnote.koitlinnote.callback.selectCallback
import com.zjy.kotlinnote.koitlinnote.mvp.model.MainModel
import com.zjy.kotlinnote.koitlinnote.mvp.view.MainView

/**
 * Created by zjy on QE.
 */
class MainPresenter constructor(baseView: MainView) : IPresenter, selectCallback {

    var mMainView = baseView
    var mainModel = MainModel()
    var data: List<NoteBean>? = null
    fun selectAll() {
        mainModel.findAll(this)
    }

    fun selectLike(str: String?) {
        mainModel.findLike(this, str)
    }

    override fun selectSuccess(data: ArrayList<NoteBean>?, isAll: Boolean) {
        mMainView.find(data,isAll)
    }

    override fun onDestroy() {
        mainModel?.let { it = null!! }
        mMainView?.let { it = null!! }
    }
}