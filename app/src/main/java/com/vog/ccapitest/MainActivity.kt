package com.vog.ccapitest

import android.content.*
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.exifinterface.media.ExifInterface
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import timber.log.Timber
import java.io.File

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    val connectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    val networkRequest by lazy {
        NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityManager.registerNetworkCallback(networkRequest, CustomNetworkCallback({}, {}))

        setContent {

            Glide.with(this)
                .asFile()
                .load("http://192.168.1.2:8080/ccapi/ver110/contents/sd1/100CANON/GFA59672.JPG")
                .into(object : CustomTarget<File>() {
                    override fun onResourceReady(
                        resource: File,
                        transition: Transition<in File>?
                    ) {
                        val exif = ExifInterface(resource)
                        Timber.d("Exif : ${exif.getAttribute(ExifInterface.TAG_DATETIME)}")
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // this is called when imageView is cleared on lifecycle call or for
                        // some other reason.
                        // if you are referencing the bitmap somewhere else too other than this imageView
                        // clear it here as you can no longer have the bitmap
                    }

                })

            val painter =
                rememberImagePainter("http://192.168.1.2:8080/ccapi/ver110/contents/sd1/100CANON/GFA59671.JPG?kind=thumbnail") {
                    this.listener(onSuccess = { imageRequest: ImageRequest, metadata: ImageResult.Metadata ->
                        Timber.d("request: $imageRequest, metadata : $metadata")
                    })
                }

            Scaffold {
                Column(modifier = Modifier.padding(16.dp)) {
                    Button(onClick = { viewModel.checkCameraApis() }) {
                        Text(text = "APIs")
                    }
                    Button(onClick = { viewModel.getDeviceInformation() }) {
                        Text(text = "Device Info")
                    }
                    Button(onClick = { viewModel.getContentURLs() }) {
                        Text(text = "Content URLs")
                    }
                    Button(onClick = { viewModel.getContents() }) {
                        Text(text = "Content List")
                    }
                    Button(onClick = { viewModel.updateCopyright() }) {
                        Text(text = "Update copyright")
                    }
                    Button(onClick = { viewModel.updateDateTime() }) {
                        Text(text = "Update Date time")
                    }
                    Image(painter = painter, contentDescription = null)
                }

            }
        }
    }
}


