package com.vog.ccapitest.model

data class ContentListResponse(
    /**
     * Resource URLs (Max. 100 items)
     */
    val url : List<String>? = null,

    /**
     * Number of contents
     */
    val contentsnumber : Int? = null,

    /**
     * Number of pages
     */
    val pagenumber: Int? = null,
)
