package com.ishak.imagesdownloadlibtest.utils

import com.ishak.imagesdownloadlibtest.models.MediaDownload
import com.ishak.imagesdownloadlibtest.observables.IDownloadObservableStatus
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header

class MediaDownloadAsync {

    fun get(mediaDownload: MediaDownload, iDownloadObservableStatus: IDownloadObservableStatus): AsyncHttpClient{
        var client: AsyncHttpClient = AsyncHttpClient(true, 80, 403)

        client.get(mediaDownload.url, object : AsyncHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                if (responseBody != null) {
                    mediaDownload.content = responseBody
                    mediaDownload.downloadObservable.onSuccess(mediaDownload)
                    iDownloadObservableStatus.done(mediaDownload)
                }
            }

            override fun onStart() {
                //super.onStart()
                mediaDownload.downloadObservable.onStart(mediaDownload)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                if (error != null && responseBody != null) {
                    mediaDownload.downloadObservable.onFailure(mediaDownload, responseBody, statusCode, error)

                    iDownloadObservableStatus.failed(mediaDownload)
                }
            }

            override fun onCancel() {
                super.onCancel()

                iDownloadObservableStatus.cancel(mediaDownload)
            }

            override fun onRetry(retryNo: Int) {
                //super.onRetry(retryNo)
                mediaDownload.downloadObservable.onRetry(mediaDownload, retryNo)
            }

        })

        return client
    }
}