package com.zjy.kotlinnote.koitlinnote.callback

import com.zjy.kotlinnote.koitlinnote.bean.NoteBean

/**
 * Created by zjy on QE.
 */
interface selectCallback {
    fun selectSuccess(data:ArrayList<NoteBean>?,isAll:Boolean)
}