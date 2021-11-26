package com.vog.ccapitest

import com.vog.ccapitest.model.*
import retrofit2.http.*

interface CCAPIver100 {

    /**
     * This gets a list of the APIs supported by the Canon camera.
     * The supported APIs differ for each Canon camera, so it is recommended to execute this
     * at the start of the generator program.
     */
    @GET("http://192.168.1.2:8080/ccapi")
    suspend fun getSupportedApis(): SupportedApisResponse

    /**
     * This gets the fixed information of the Canon camera.
     * This enables to get fixed information that does not depend on the Canon camera status,
     * such as the product name and serial number.
     */
    @GET("deviceinformation")
    suspend fun getDeviceInformation(): CameraInfoResponse

    /**
     * This changes the date and time set in the Canon camera.
     * To set daylight saving time correctly, enter the time that takes into account daylight
     * saving time in “datetime”, and set “true” in “dst”.
     */
    @PUT("functions/datetime")
    suspend fun updateDateTime(@Body updateDateTimeBody: UpdateDateTimeBody): DateTimeResponse

    /**
     * This updates the copyright holder name set in the Canon camera.
     * The Canon camera cannot be operated while updating is in progress.
     * The set value will be recorded in Exif and other image metadata of shot images.
     */
    @PUT("functions/registeredname/copyright")
    suspend fun updateCopyright(@Body copyrightBody: CopyrightBody): CopyrightResponse

}