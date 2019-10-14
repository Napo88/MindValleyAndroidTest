package com.ishak.imagesdownloadlibtest.models

import com.ishak.imagesdownloadlibtest.observables.IDownloadObservable
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

abstract class MediaDownload {
    var url: String
    var content: ByteArray? = null
    var contentType: DataType
    var downloadObservable: IDownloadObservable
    var MD5Key: String
    var from: String = "server"


    @ExperimentalStdlibApi
    constructor(url: String, contentType: DataType, downloadObservable: IDownloadObservable) {
        this.url = url
        this.contentType = contentType
        this.downloadObservable = downloadObservable
        this.MD5Key = md5(this.url)
    }

    abstract fun copyOfMe(downloadObservable: IDownloadObservable): MediaDownload

    @ExperimentalStdlibApi
    fun md5(text: String): String{
        try {
            val mDigest: MessageDigest = MessageDigest.getInstance("MD5")
            mDigest.update(text.encodeToByteArray())
            var messageDigest: ByteArray = mDigest.digest()

            var hexStringBuffer = StringBuffer()

            for (msDigest: Byte in messageDigest){
                var hex: String = Integer.toHexString(msDigest.toInt())

                while (hex.length < 2)
                    hex = "0" + hex
                hexStringBuffer.append(hex)

            }

            return hexStringBuffer.toString()
        } catch (ex: NoSuchAlgorithmException){

        }

        return "";
    }
}