package com.vog.ccapitest.model

data class SupportedApisResponse(val ver100: List<Api>? = null) {
    data class Api(
        val url: String,
        val get: Boolean,
        val post: Boolean,
        val put: Boolean,
        val delete: Boolean
    )
}

