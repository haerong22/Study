package org.example.moviedgs.exceptions

import com.netflix.graphql.types.errors.ErrorType

class CustomNotFoundException(
    override val message: String = "Entity Not Found"
): CustomException(
    message = message,
    errorType = ErrorType.NOT_FOUND,
    errorCode = 1001
) {
}