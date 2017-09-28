package com.zjy.kotlinnote.koitlinnote.mvp.view

import com.zjy.kotlinnote.koitlinnote.bean.NoteBean

/**
 * Created by zjy on QE.
 */
interface MainView {
    fun find(data: ArrayList<NoteBean>?,isAll:Boolean)
}