package org.example.moviedgs.cache

import com.github.benmanes.caffeine.cache.Caffeine
import graphql.ExecutionInput
import graphql.execution.preparsed.PreparsedDocumentEntry
import graphql.execution.preparsed.PreparsedDocumentProvider
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.function.Function

@Component
class CachingPreparsedDocumentProvider : PreparsedDocumentProvider {

    private val cache = Caffeine
        .newBuilder()
        .maximumSize(10) // 캐시의 최대 항목 수를 10개로 제한, 최근에 사용되지 않은 항목을 우선적으로 제거
//        .maximumWeight(1000) // 캐시가 수용할 수 있는 항목들의 최대 가중치를 지정
//        .weigher { key: String, value: PreparsedDocumentEntry ->
//            key.length // 가중치를 계산
//        }
        .expireAfterAccess(Duration.ofHours(1)) // 캐시된 항목이 마지막으로 접근된 후 1시간이 지나면 자동으로 만료
//        .expireAfterWrite(Duration.ofHours(1)) // 항목이 캐시에 기록된 후 지정된 시간이 지나면 만료
        .softValues() // JVM이 메모리가 부족하면, GC(Garbage Collector)가 Soft Reference 값을 우선적으로 제거합니다.
        .recordStats() // 캐시의 통계 수집을 활성화합니다.
        .build<String, PreparsedDocumentEntry>()

    override fun getDocumentAsync(
        executionInput: ExecutionInput,
        parseAndValidateFunction: Function<ExecutionInput, PreparsedDocumentEntry>
    ): CompletableFuture<PreparsedDocumentEntry> {
        return CompletableFuture.supplyAsync {
            cache[
                executionInput.query,
                { parseAndValidateFunction.apply(executionInput) }
            ]
        }
    }
}