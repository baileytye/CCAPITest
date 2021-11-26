package com.vog.ccapitest.model

data class CameraInfoResponse(
    val manufacturer : String,
    val productname: String,
    val guid: String,
    val serialnumber: String,
    val macaddress: String,
    val firmwareversion: String,
)
