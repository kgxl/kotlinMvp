package com.zjy.kotlinnote.koitlinnote.callback

/**
 * Created by zjy on QE.
 */
interface DBCallback {
    fun saveSuccess()
    fun saveFail()
    fun deleteSuccess()
    fun deleteFail()
}