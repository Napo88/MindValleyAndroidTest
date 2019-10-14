package com.ishak.imagesdownloadlibtest.utils

import com.ishak.imagesdownloadlibtest.models.MediaDownload
import com.ishak.imagesdownloadlibtest.observables.IDownloadObservable
import com.ishak.imagesdownloadlibtest.observables.IDownloadObservableStatus
import com.loopj.android.http.AsyncHttpClient
import java.util.*
import kotlin.collections.HashMap

class MediaDownloadType() {
    companion object{
        private var instance: MediaDownloadType? = null

        fun getInstance(): MediaDownloadType?{
            if (instance == null)
                instance = MediaDownloadType()

            return instance
        }
    }
    private var requestsByKey: HashMap<String, LinkedList<MediaDownload>> = HashMap()
    private var requestClient: HashMap<String, AsyncHttpClient> = HashMap()
    private var dMemoryManager: DMemoryManager

    init {
        dMemoryManager = DMemoryManager.getInstance()!!
    }

    fun getRequest(mediaDownload: MediaDownload){
        val mdKey: String = mediaDownload.MD5Key

        var mDownloadFromCache: MediaDownload? = dMemoryManager.getMediadownload(mdKey)
        if (mDownloadFromCache != null){
            mDownloadFromCache.from = "Cache"

            var iDownloadObservable: IDownloadObservable = mediaDownload.downloadObservable
            iDownloadObservable.onStart(mDownloadFromCache)
            iDownloadObservable.onSuccess(mDownloadFromCache)

            return
        }

        if(requestsByKey.containsKey(mdKey)){
            mediaDownload.from = "Cache"
            requestsByKey.get(mdKey)?.add(mediaDownload)

            return
        }

        if (requestsByKey.containsKey(mdKey)){
            requestsByKey.get(mdKey)?.add(mediaDownload)
        } else {
            var newLstDownload: LinkedList<MediaDownload> = LinkedList()
            newLstDownload.add(mediaDownload)
            requestsByKey.put(mdKey, newLstDownload)
        }

        var newDownload: MediaDownload = mediaDownload.copyOfMe(object :IDownloadObservable{
            override fun onStart(mMediaDownload: MediaDownload) {
                for (media: MediaDownload in requestsByKey.get(mMediaDownload.MD5Key)!!){
                    media.content = mMediaDownload.content
                    media.downloadObservable.onStart(media)
                }
            }

            override fun onSuccess(mMediaDownload: MediaDownload) {
                for (media: MediaDownload in requestsByKey.get(mMediaDownload.MD5Key)!!){
                    media.content = mMediaDownload.content
                    media.downloadObservable.onSuccess(media)
                }
                requestsByKey.remove(mediaDownload.MD5Key)
            }

            override fun onFailure(
                mMediaDownload: MediaDownload,
                errorResponse: ByteArray?,
                errorCode: Int,
                error: Throwable?
            ) {
                for (media: MediaDownload in requestsByKey.get(mMediaDownload.MD5Key)!!){
                    media.content = mMediaDownload.content
                    media.downloadObservable.onFailure(media, errorResponse, errorCode, error)
                }
                requestsByKey.remove(mediaDownload.MD5Key)
            }

            override fun onRetry(mMediaDownload: MediaDownload, retryCode: Int) {
                for (media: MediaDownload in requestsByKey.get(mMediaDownload.MD5Key)!!){
                    media.content = mMediaDownload.content
                    media.downloadObservable.onRetry(media, retryCode)
                }
            }

        })

        var mediaDownloadAsync = MediaDownloadAsync()

        var client: AsyncHttpClient = mediaDownloadAsync.get(newDownload, object: IDownloadObservableStatus{
            override fun done(mMediaDownload: MediaDownload) {
                dMemoryManager.putMediaDownloadType(mMediaDownload.MD5Key, mMediaDownload)
                requestClient.remove(mMediaDownload.MD5Key)
            }

            override fun cancel(mMediaDownload: MediaDownload) {
                requestClient.remove(mMediaDownload.MD5Key)
            }

            override fun failed(mMediaDownload: MediaDownload) {
                requestClient.remove(mMediaDownload.MD5Key)
            }

        })

        requestClient.put(mdKey, client)
    }

    fun cancelRequest(mediaDownload: MediaDownload){
        if(requestsByKey.containsKey(mediaDownload.MD5Key)){
            requestsByKey.get(mediaDownload.MD5Key)?.remove(mediaDownload)
            mediaDownload.from = "Canceled"
            mediaDownload.downloadObservable.onFailure(mediaDownload, null, 0, null)
        }
    }

    fun isRequestDone(): Boolean{
        return requestsByKey.size == 0
    }

    fun clearCache(){
        dMemoryManager.clearCache()
    }
}