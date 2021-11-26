package com.vog.ccapitest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vog.ccapitest.model.CopyrightBody
import com.vog.ccapitest.model.UpdateDateTimeBody
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.sql.Time
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel : ViewModel() {

    //http://[IPAddress]:[Port]/ccapi/[Version]
    val apiV100 = Retrofit.Builder()
        .baseUrl("http://192.168.1.2:8080/ccapi/ver100/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(CCAPIver100::class.java)

    val apiV110 = Retrofit.Builder()
        .baseUrl("http://192.168.1.2:8080/ccapi/ver110/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(CCAPIver110::class.java)

    fun checkCameraApis() {
        viewModelScope.launch {
            try {
                apiV100.getSupportedApis()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getDeviceInformation() {
        viewModelScope.launch {
            try {
                apiV100.getDeviceInformation()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getContentURLs() {
        viewModelScope.launch {
            try {
                apiV110.getStorageURLs()
                apiV110.getDirectoryURLs("sd1")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getContents() {
        viewModelScope.launch {
            try {
                val response =
                    apiV110.getStorageContents(storageName = "sd1", "100CANON", kind = "list")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateCopyright() {
        viewModelScope.launch {
            try {
                apiV100.updateCopyright(CopyrightBody("New copyright"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateDateTime() {
        viewModelScope.launch {
            try {
                apiV100.updateDateTime(
                    UpdateDateTimeBody(
                        datetime = ZonedDateTime.now().format(
                            DateTimeFormatter.RFC_1123_DATE_TIME
                        ),
                        dst = false,
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}