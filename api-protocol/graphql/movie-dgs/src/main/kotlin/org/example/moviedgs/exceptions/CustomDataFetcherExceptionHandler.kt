package org.example.moviedgs.exceptions

import com.netflix.graphql.types.errors.ErrorDetail
import com.netflix.graphql.types.errors.ErrorType
import com.netflix.graphql.types.errors.TypedGraphQLError
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class CustomDataFetcherExceptionHandler : DataFetcherExceptionHandler {

    override fun handleException(handlerParameters: DataFetcherExceptionHandlerParameters): CompletableFuture<DataFetcherExceptionHandlerResult> {
        val graphQLError = if (handlerParameters.exception is CustomException) {
            (handlerParameters.exception as CustomException)
                .toGraphQlError(handlerParameters.path, handlerParameters.sourceLocation)
        } else {
            TypedGraphQLError.newBuilder()
                .errorType(ErrorType.BAD_REQUEST)
                .message(handlerParameters.exception.message ?: "UnknownError")
                .location(handlerParameters.sourceLocation)
                .path(handlerParameters.path)

                .errorDetail(ErrorDetail.Common.FIELD_NOT_FOUND)
                .debugInfo(mapOf("stackTrace" to handlerParameters.exception.stackTrace.first()))
                .origin("movie-service")
                .extensions(mapOf("errorCode" to "1001"))

                .build()
        }

        val result = DataFetcherExceptionHandlerResult.newResult()
            .error(graphQLError)
            .build()

        return CompletableFuture.completedFuture(result)
    }
}