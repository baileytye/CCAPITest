package com.vog.ccapitest.model

data class DateTimeResponse(
    /**
     * Date and time (RFC1123 compliant)
     * "Fri, 01 Feb 2019 23:34:50 -0100"
     */
    val datetime : String,

    /**
     * Daylight saving time
     */
    val dst: Boolean
)
