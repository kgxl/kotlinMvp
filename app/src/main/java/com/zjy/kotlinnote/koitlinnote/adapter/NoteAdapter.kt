package com.zjy.kotlinnote.koitlinnote.adapter

import android.graphics.Color
import android.support.annotation.LayoutRes

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.czy1121.view.CornerLabelView
import com.zjy.kotlinnote.koitlinnote.R
import com.zjy.kotlinnote.koitlinnote.bean.NoteBean

/**
 * Created by zjy on QE.
 */

class NoteAdapter(@LayoutRes layoutResId: Int, data: List<NoteBean>?) : BaseQuickAdapter<NoteBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: NoteBean) {
        helper.setText(R.id.note_name, item.NoteName)
        helper.setText(R.id.note_time, item.NoteLastChangeTime)
        helper.getView<CornerLabelView>(R.id.cornerLabelView).right().setText1(item.NoteType).setFillColor(Color.parseColor("#FF0030")).setText1Height(10.0f)
    }
}
