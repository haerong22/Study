package org.example.moviedgs.monitoring

import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationState
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import graphql.execution.instrumentation.tracing.TracingInstrumentation
import graphql.execution.instrumentation.tracing.TracingSupport
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class TracingInstrumentation : TracingInstrumentation() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun instrumentExecutionResult(
        executionResult: ExecutionResult,
        parameters: InstrumentationExecutionParameters,
        state: InstrumentationState
    ): CompletableFuture<ExecutionResult> {
        require(state is TracingSupport)

        log.info("\n[Tracing]\n" + formatMap(state.snapshotTracingData()))

        return CompletableFuture.completedFuture(executionResult)
    }

    private fun formatMap(map: Map<String, Any?>, indent: Int = 0): String {
        val indentSpace = "  ".repeat(indent) // 들여쓰기 공백
        val builder = StringBuilder()

        for ((key, value) in map) {
            builder.append("$indentSpace$key: ")

            when (value) {
                is Map<*, *> -> {
                    builder.append("\n")
                    @Suppress("UNCHECKED_CAST")
                    builder.append(formatMap(value as Map<String, Any?>, indent + 1))
                }

                is List<*> -> {
                    builder.append("\n")
                    builder.append(formatList(value, indent + 1))
                }

                else -> builder.append("$value\n")
            }
        }
        return builder.toString()
    }


    private fun formatList(list: List<*>, indent: Int): String {
        val indentSpace = "  ".repeat(indent)
        val builder = StringBuilder()

        for (item in list) {
            builder.append("$indentSpace- ")
            when (item) {
                is Map<*, *> -> {
                    builder.append("\n")
                    @Suppress("UNCHECKED_CAST")
                    builder.append(formatMap(item as Map<String, Any?>, indent + 1))
                }

                is List<*> -> {
                    builder.append("\n")
                    builder.append(formatList(item, indent + 1))
                }

                else -> builder.append("$item\n")
            }
        }
        return builder.toString()
    }
}