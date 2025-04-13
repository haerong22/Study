package org.example.moviedgs.monitoring

import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.InstrumentationState
import graphql.execution.instrumentation.SimplePerformantInstrumentation
import graphql.execution.instrumentation.parameters.InstrumentationCreateStateParameters
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class LoggingInstrumentation : SimplePerformantInstrumentation() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun createState(parameters: InstrumentationCreateStateParameters?): InstrumentationState? {
        return LoggingState()
    }

    override fun beginExecution(
        parameters: InstrumentationExecutionParameters,
        state: InstrumentationState,
    ): InstrumentationContext<ExecutionResult>? {
        require(state is LoggingState)
        state.operationName = parameters.operation
        state.requestQuery = parameters.query
        return super.beginExecution(parameters, state)
    }

    override fun instrumentExecutionResult(
        executionResult: ExecutionResult,
        parameters: InstrumentationExecutionParameters,
        state: InstrumentationState,
    ): CompletableFuture<ExecutionResult> {
        require(state is LoggingState)
        state.responseData = executionResult.getData<Any>().toString()
        state.errorMessage = executionResult.errors.joinToString { it.toString() }
        state.log(logger)
        return super.instrumentExecutionResult(executionResult, parameters, state)
    }

    data class LoggingState(
        var startTime: Long = System.currentTimeMillis(),
        var operationName: String? = null,
        var requestQuery: String? = null,
        var responseData: String? = null,
        var errorMessage: String? = null,
    ) : InstrumentationState {

        fun log(logger: Logger) {
            logger.info(
                "\n[Logging]\n" +
                        "Operation Name: $operationName\n" +
                        "Request Query: $requestQuery\n" +
                        "Response Data: $responseData\n" +
                        "Error Message: $errorMessage\n" +
                        "Total Time: ${System.currentTimeMillis() - startTime}ms\n"
            )
        }
    }
}