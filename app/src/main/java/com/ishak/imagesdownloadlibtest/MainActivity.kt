package com.ishak.imagesdownloadlibtest

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ishak.imagesdownloadlibtest.adapters.GridImagesAdapter
import com.ishak.imagesdownloadlibtest.model.Board
import com.ishak.imagesdownloadlibtest.models.MediaDownload
import com.ishak.imagesdownloadlibtest.models.MediaJsonDownload
import com.ishak.imagesdownloadlibtest.observables.IDownloadObservable
import com.ishak.imagesdownloadlibtest.utils.MediaDownloadType
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    @BindView(R.id.gridView)
    private lateinit var gridView: GridView
    private lateinit var imgAdapter: GridImagesAdapter
    private lateinit var mediaDownloadType: MediaDownloadType
    private var handler: Handler = Handler()
    var imgUrls: ArrayList<String> = ArrayList()
    private val Base_URL: String = "https://pastebin.com/raw/wgkJgazE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        mediaDownloadType = MediaDownloadType.getInstance()!!
        imgAdapter = GridImagesAdapter(this, imgUrls)
        gridView.adapter = imgAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pingboard_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @ExperimentalStdlibApi
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.nav_refresh){

            handler.postDelayed(object: Runnable{
                override fun run() {
                    getContent()
                }

            }, 1000)

            return true
        } else if (item.itemId == R.id.nav_clear_cache){
            mediaDownloadType.clearCache()
        }

        return super.onOptionsItemSelected(item)
    }

    //inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>(){}.type)

    @ExperimentalStdlibApi
    fun getContent(){
        var contentData: MediaDownload = MediaJsonDownload(Base_URL, object: IDownloadObservable {
            override fun onStart(mMediaDownload: MediaDownload) {

            }

            override fun onSuccess(mMediaDownload: MediaDownload) {

                var type = object: TypeToken<List<Board>>(){}.type
                var listBoard: List<Board> = (mMediaDownload as MediaJsonDownload).getJson(type) as List<Board>

                for(board: Board in listBoard){
                    imgUrls.add(board.urls.small)
                    imgUrls.add(board.urls.thumb)
                    imgUrls.add(board.urls.regular)
                }
                imgAdapter.imageUrls = imgUrls
                gridView.adapter = imgAdapter

            }

            override fun onFailure(
                mMediaDownload: MediaDownload,
                errorResponse: ByteArray?,
                errorCode: Int,
                error: Throwable?
            ) {
                Toast.makeText(this@MainActivity, "Content download error.", Toast.LENGTH_LONG)
            }

            override fun onRetry(mMediaDownload: MediaDownload, retryCode: Int) {

            }

        })
        mediaDownloadType.getRequest(contentData)

    }
}
