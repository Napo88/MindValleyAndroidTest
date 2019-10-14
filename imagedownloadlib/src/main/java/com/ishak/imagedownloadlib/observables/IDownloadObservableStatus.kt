package com.ishak.imagesdownloadlibtest.observables

import com.ishak.imagesdownloadlibtest.models.MediaDownload

interface IDownloadObservableStatus {
    fun done(mMediaDownload: MediaDownload)
    fun cancel(mMediaDownload: MediaDownload)
    fun failed(mMediaDownload: MediaDownload)
}