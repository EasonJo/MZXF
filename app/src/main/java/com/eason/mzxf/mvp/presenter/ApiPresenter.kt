package com.eason.mzxf.mvp.presenter

import com.eason.mzxf.fetcher.Fetcher
import com.eason.mzxf.fetcher.RequestType
import com.eason.mzxf.fetcher.ResultListener
import com.eason.mzxf.fetcher.Status
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * APIPresenter
 * Created by Eason on 2018/1/17.
 */
abstract class ApiPresenter<M : IModel, V : IView> constructor(m: M, v: V) : BasePresenter<M, V>(m, v), ResultListener {

    var fetcher: Fetcher = Fetcher.INSTANCE

    val TYPE_NONE = RequestType.TYPE_NONE

    //请求状态
    protected val SUCCESS = Status.SUCCESS
    protected val LOADING = Status.LOADING
    protected val ERROR = Status.ERROR
    protected val EMPTY_SUCCESS = Status.EMPTY_SUCCESS

    protected infix fun RequestType.statusIs(status: Status) = fetcher.getRequestStatus(this) == status

    //扩展属性,获取当前请求的类型的状态
    protected val RequestType.status
        get() = fetcher.getRequestStatus(this)

    /**
     * Flowable<T>类型请求封装
     */
    fun <TYPE> fetch(flowable: Flowable<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(flowable, requestType, this, success)
    }

    /**
     * Observable<T>类型封装.
     */
    fun <TYPE> fetch(observable: Observable<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(observable, requestType, this, success)
    }

    /**
     * Single<T>类型封装, Single 类型只发射一个数据
     * @param success(TYPE) 回调函数封装
     */
    fun <TYPE> fetch(single: Single<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(single, requestType, this, success)
    }

    /**
     * Completable<T>类型封装
     */
    fun complete(completable: Completable,
                 requestType: RequestType = TYPE_NONE, success: () -> Unit = {}) {
        fetcher.complete(completable, requestType, this, success)
    }

    override fun onDestroy() {
        super.onDestroy()
        fetcher.clear()
    }

    override fun onRequestStart(requestType: RequestType) {
        onRequestStart()
    }

    override fun onRequestError(requestType: RequestType, errorMessage: String?) {
        onRequestError(errorMessage)
    }
}