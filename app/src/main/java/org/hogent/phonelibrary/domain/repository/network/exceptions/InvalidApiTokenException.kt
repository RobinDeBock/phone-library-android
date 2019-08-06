package org.hogent.phonelibrary.domain.repository.network.exceptions

/**
 * Exception for when the token of the API is invalid.
 *
 */
class InvalidApiTokenException : Exception("The API token is invalid.")