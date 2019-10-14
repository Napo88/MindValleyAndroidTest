package com.ishak.imagesdownloadlibtest.models

import com.google.gson.Gson
import com.ishak.imagesdownloadlibtest.observables.IDownloadObservable
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.util.*

@ExperimentalStdlibApi
class MediaJsonDownload(url: String, downloadObservable: IDownloadObservable) :
    MediaDownload(url, DataType.JSON, downloadObservable) {

    override fun copyOfMe(downloadObservable: IDownloadObservable): MediaDownload {
        var mediaDownload: MediaDownload = MediaJsonDownload(this.url, downloadObservable)

        return mediaDownload
    }

    public fun getJsonText(): String{
        try {
            var str: String = String(content!!, Charset.forName("UTF-8"))

            return str
        } catch (ex: Exception){
            ex.printStackTrace()
        }

        return "";
    }

    public fun getJson(type: Type): Objects{
        var gson: Gson = Gson()

        return gson.fromJson(getJsonText(), type);
    }
}