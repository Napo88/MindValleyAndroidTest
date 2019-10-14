package com.ishak.imagesdownloadlibtest.observables

import com.ishak.imagesdownloadlibtest.models.MediaDownload

interface IDownloadObservable {
    fun onStart(mMediaDownload: MediaDownload)
    fun onSuccess(mMediaDownload: MediaDownload)
    fun onFailure(mMediaDownload: MediaDownload, errorResponse: ByteArray?, errorCode: Int, error: Throwable?)
    fun onRetry(mMediaDownload: MediaDownload, retryCode: Int)
}