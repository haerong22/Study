## Netflix DGS

### Custom Scala

```kotlin
implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
```

- 수동 등록 시 @DgsScalar 어노테이션 사용 

### Code Generator

`build.gradle.kts`
```kotlin
plugins {
    id ("com.netflix.dgs.codegen") version "7.0.3"
}

tasks.generateJava {
  language = "kotlin"
  packageName = "com.example.moviedgs"
  typeMapping = mutableMapOf(
    "ID" to "Long"
  )
}
```
- CodeGenerator 옵션
    - https://netflix.github.io/dgs/generating-code-from-schema/#configuring-code-generation
