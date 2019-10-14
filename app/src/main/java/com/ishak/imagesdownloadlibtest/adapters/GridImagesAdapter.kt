package com.ishak.imagesdownloadlibtest.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import com.ishak.imagesdownloadlibtest.R
import com.ishak.imagesdownloadlibtest.models.MediaDownload
import com.ishak.imagesdownloadlibtest.models.MediaImageDownload
import com.ishak.imagesdownloadlibtest.observables.IDownloadObservable
import com.ishak.imagesdownloadlibtest.utils.MediaDownloadType

class GridImagesAdapter: BaseAdapter {
    private var context: Context
    private var mediaDownloadType: MediaDownloadType
    var imageUrls: List<String> = ArrayList()

    constructor(context: Context?, imageUrls: List<String>) : super() {
        this.context = context!!
        this.imageUrls = imageUrls
        mediaDownloadType = MediaDownloadType.getInstance()!!
    }


    @ExperimentalStdlibApi
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var imageView = ImageView(context)

        var mediaImageDownload = MediaImageDownload(imageUrls.get(position), object: IDownloadObservable{
            override fun onStart(mMediaDownload: MediaDownload) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSuccess(mMediaDownload: MediaDownload) {
                with(imageView)
                {
                    setImageBitmap((mMediaDownload as MediaImageDownload).getImageBitmap())
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    imageView.layoutParams = LinearLayout.LayoutParams(250, 250)
                }
            }

            override fun onFailure(
                mMediaDownload: MediaDownload,
                errorResponse: ByteArray?,
                errorCode: Int,
                error: Throwable?
            ) {
                imageView.setImageResource(R.drawable.ic_image_black_24dp)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.layoutParams = LinearLayout.LayoutParams(250, 250)
            }

            override fun onRetry(mMediaDownload: MediaDownload, retryCode: Int) {

            }
        })

        mediaDownloadType.getRequest(mediaImageDownload)

        return imageView
    }

    override fun getItem(position: Int): Any {
        return imageUrls.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return imageUrls.size
    }
}