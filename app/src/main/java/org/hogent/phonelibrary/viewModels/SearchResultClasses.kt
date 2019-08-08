package org.hogent.phonelibrary.viewModels

import org.hogent.phonelibrary.domain.models.Device
import java.io.Serializable

/**
 * A search result.
 *
 * @param searchTerm The term used to search with.
 * @param searchType If there was searched by device name or brand name.
 */
abstract class SearchResult(var searchTerm: String, var searchType: SearchType) :  Serializable

/**
 * A successful result.
 *
 * @property devices The devices.
 * @constructor Success result is a search result.
 *
 * @param searchTerm The name of the device or brand name used to search with.
 * @param searchType If there was searched by device name or brand name.
 */
class SuccessResult(var devices: Collection<Device>, searchTerm: String, searchType: SearchType) :
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
class ErrorResult(var error: Exception, searchTerm: String, searchType: SearchType) :
    SearchResult(searchTerm, searchType)

enum class SearchType {
    ByDEVICE,
    ByBRAND
}