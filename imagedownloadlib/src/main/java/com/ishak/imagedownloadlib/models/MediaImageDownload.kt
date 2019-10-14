package com.ishak.imagesdownloadlibtest.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ishak.imagesdownloadlibtest.observables.IDownloadObservable

@ExperimentalStdlibApi
class MediaImageDownload(url: String, downloadObservable: IDownloadObservable) :
    MediaDownload(url, DataType.IMAGE, downloadObservable) {


    override fun copyOfMe(downloadObservable: IDownloadObservable): MediaDownload {
        var mediaDownload: MediaDownload = MediaImageDownload(this.url, downloadObservable)

        return mediaDownload
    }

    public fun getImageBitmap(): Bitmap{
        var bitmap: Bitmap = BitmapFactory.decodeByteArray(content, 0, content!!.size)

        return bitmap
    }
}