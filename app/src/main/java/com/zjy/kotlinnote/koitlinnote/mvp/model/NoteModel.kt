package com.zjy.kotlinnote.koitlinnote.mvp.model

import com.zjy.kotlinnote.koitlinnote.bean.NoteBean
import com.zjy.kotlinnote.koitlinnote.callback.DBCallback
import com.zjy.kotlinnote.koitlinnote.utils.DateUtil
import com.zjy.kotlinnote.koitlinnote.utils.ThreadTask
import org.litepal.crud.DataSupport

/**
 * Created by zjy on QE.
 */
class NoteModel  {
     fun save(noteName: String, noteType: String, noteContent: String, callback: DBCallback) {
        ThreadTask.getInstance().executorDBThread({
            var bean = NoteBean()
            bean.NoteName = noteName
            bean.NoteContent = noteContent
            bean.NoteType = noteType
            if (!DataSupport.isExist(NoteBean::class.java, "NoteName=? and NoteType=?", noteName, noteType)) {
                bean.NoteCreateTime = DateUtil.getShowTime(System.currentTimeMillis())
                bean.NoteLastChangeTime = DateUtil.getShowTime(System.currentTimeMillis())
            } else {
                bean.NoteLastChangeTime = DateUtil.getShowTime(System.currentTimeMillis())
            }
            var saveState = bean.saveOrUpdate("NoteName=? and NoteType=?", noteName, noteType)
            if (saveState) callback.saveSuccess() else callback.saveFail()

        }, ThreadTask.ThreadPeriod.PERIOD_HIGHT)
    }

     fun delete(noteName: String, noteType: String, callback: DBCallback) {
        if (DataSupport.isExist(NoteBean::class.java, "NoteName=? and NoteType=?", noteName, noteType)) {
            DataSupport.deleteAll(NoteBean::class.java, "NoteName=? and NoteType=?", noteName, noteType)
            callback.deleteSuccess()
        } else {
            callback.deleteFail()
        }
    }

}