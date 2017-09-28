package com.zjy.kotlinnote.koitlinnote.mvp.model

import com.zjy.kotlinnote.koitlinnote.bean.NoteBean
import com.zjy.kotlinnote.koitlinnote.callback.selectCallback
import com.zjy.kotlinnote.koitlinnote.utils.ThreadTask
import org.litepal.crud.DataSupport

/**
 * Created by zjy on QE.
 */
class MainModel {
    var data: List<NoteBean>? = null
    fun findAll(callback: selectCallback) {
        ThreadTask.getInstance().executorDBThread({
            if (DataSupport.isExist(NoteBean::class.java)) {
                data = DataSupport.findAll(NoteBean::class.java)
            }else{
                data=null
            }
            callback.selectSuccess(data as? ArrayList<NoteBean>,true)
        }, ThreadTask.ThreadPeriod.PERIOD_HIGHT)
    }

    fun findLike(callback: selectCallback, str: String?) {
        ThreadTask.getInstance().executorDBThread({
            if (!"".equals(str)) {
                if (DataSupport.isExist(NoteBean::class.java)) {
                    data = DataSupport.where("NoteName like ?", "$str%").find(NoteBean::class.java)
                }else{
                    data=null
                }
                callback.selectSuccess(data as? ArrayList<NoteBean>,false)
            }
        }, ThreadTask.ThreadPeriod.PERIOD_HIGHT)
    }
}