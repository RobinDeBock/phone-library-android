package org.hogent.phonelibrary.viewModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.hogent.phonelibrary.domain.models.Device
import java.io.Serializable

/**
 * A search result.
 *
 * @param searchTerm The term used to search with.
 * @param searchType If there was searched by device name or brand name.
 */
abstract class SearchResult(open var searchTerm: String, open var searchType: SearchType): Parcelable

/**
 * A successful result.
 *
 * @property devices The devices.
 * @constructor Success result is a search result.
 *
 * @param searchTerm The name of the device or brand name used to search with.
 * @param searchType If there was searched by device name or brand name.
 */
@Parcelize
class SuccessResult(var devices: List<Device>, override var searchTerm: String, override var searchType: SearchType) :
    SearchResult(searchTerm, searchType)

/**
 * An error result.
 *
 * @property error The error.
 * @constructor Error result is a search result.
 *
 * @param searchTerm The name of the device or brand name used to search with.
 * @param searchType If there was searched by device name or brand name.
 */
@Parcelize
class ErrorResult(var error: Exception, override var searchTerm: String, override var searchType: SearchType) :
    SearchResult(searchTerm, searchType), Parcelable

enum class SearchType {
    ByDEVICE,
    ByBRAND
}