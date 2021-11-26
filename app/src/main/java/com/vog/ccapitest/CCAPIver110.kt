package com.vog.ccapitest

import com.vog.ccapitest.model.ContentListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CCAPIver110 {

    /**
     * This gets the list of storage URLs.
     */
    @GET("contents")
    suspend fun getStorageURLs() : ContentListResponse

    /**
     * This gets the URLs of the directories directly below the storage (DCIM).
     */
    @GET("contents/{storage}")
    suspend fun getDirectoryURLs(@Path("storage") storage : String) : ContentListResponse

    /**
     * This gets the URLs of the contents within the directory.
     * @param storageName Storage name (ex. 'sd')
     * @param directory Directory name (ex. '100CANON')
     * @param type Target file format :
     *  - all All (Default when type is not designated)
     *  - jpeg JPEG
     *  - cr2 CR2
     *  - cr3 CR3
     *  - wav WAV
     *  - mp4 MP4
     *  - mov MOV
     * @param kind Response data kind:
     * - list -> Gets the list of contents.(Default when kind is not designated)
     * - chunked -> Gets the list of contents divided in chunk format.
     * - number -> Gets the number of contents and number of pages.
     * @param order Contents acquisition order. Can be designated only when kind = chunked
     * @param page Display page of contents to be acquired
     * ex. When page = 2, the 101st to 200th contents will be displayed.
     * Can be designated only when kind = list (Default when page is not designated = 1)
     */
    @GET("contents/{storage}/{directory}")
    suspend fun getStorageContents(
        @Path("storage") storageName: String,
        @Path("directory") directory: String,
        @Query("type") type : String? = null,
        @Query("kind") kind: String? = null,
        @Query("order") order: String? = null,
        @Query("page") page: Int? = null,
    ) : ContentListResponse

    /**
     * ONLY USE FOR INFO, FOR DOWNLOADING THE IMAGE USE COIL
     * This gets contents.
     * @param storageName Storage name (ex. 'sd')
     * @param directory Directory name (ex. '100CANON')
     * @param file File name
     * @param kind Contents kind :
     * - main -> Main data (Default when kind is not designated)
     * - thumbnail -> Thumbnail image
     * - display -> Display image
     * - embedded -> Embedded image RAW only
     * - info -> File information
     */
    @GET("contents/{storage}/{directory}/{file}")
    suspend fun getContentData(
        @Path("storage") storageName: String,
        @Path("directory") directory: String,
        @Path("file") file : String,
        @Query("kind") kind: String? = "info",
    )
}