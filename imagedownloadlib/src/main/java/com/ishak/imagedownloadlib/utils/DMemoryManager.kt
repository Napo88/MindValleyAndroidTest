package com.ishak.imagesdownloadlibtest.utils

import android.util.LruCache
import com.ishak.imagesdownloadlibtest.models.MediaDownload

class DMemoryManager() {
    private var maxCacheSize: Int
    companion object{
        var dMenory: DMemoryManager? = null

        fun getInstance(): DMemoryManager? {
            if (dMenory == null)
                dMenory = DMemoryManager()

            return dMenory
        }
    }

    var mediaDownloadTypeCache: LruCache<String, MediaDownload>

    init {
        var maxMemory: Int = Runtime.getRuntime().maxMemory().toInt()
        maxCacheSize = maxMemory / 4
        mediaDownloadTypeCache = LruCache(maxCacheSize)
    }

    fun getMediadownload(key: String): MediaDownload? {
        return mediaDownloadTypeCache.get(key)
    }

    fun putMediaDownloadType(key: String, mediaDownload: MediaDownload): Boolean{
        return mediaDownloadTypeCache.put(key, mediaDownload) != null
    }

    fun clearCache(){
        mediaDownloadTypeCache.evictAll()
    }
}