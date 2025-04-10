package org.example.moviedgs.customscalars

import com.netflix.graphql.dgs.DgsScalar
import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.StringValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingSerializeException
import java.util.Locale

@DgsScalar(name = "Email")
class EmailScalar: Coercing<String, String> {

    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    override fun serialize(dataFetcherResult: Any, graphQLContext: GraphQLContext, locale: Locale): String? {
        val email = dataFetcherResult.toString()

        if (emailRegex.matches(email)) {
            return email
        }

        throw CoercingSerializeException("Invalid email format.")
    }

    override fun parseValue(input: Any, graphQLContext: GraphQLContext, locale: Locale): String? {
        val email = input.toString()

        if (emailRegex.matches(email)) {
            return email
        }

        throw CoercingSerializeException("Invalid email format.")
    }

    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): String? {
        val email = (input as StringValue).value

        if (emailRegex.matches(email)) {
            return email
        }

        throw CoercingSerializeException("Invalid email format.")
    }
}