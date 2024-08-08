package org.example.elsgraphql.directive;

import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import org.example.elsgraphql.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        GraphQLFieldDefinition fieldDefinition = environment.getFieldDefinition();
        GraphQLObjectType parentType = (GraphQLObjectType) environment.getFieldsContainer();

        DataFetcher<?> originDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, fieldDefinition);

        DataFetcher<?> authDataFetcher = (dataFetchingEnvironment) -> {
            String userId = dataFetchingEnvironment.getGraphQlContext().get("X-USER-ID");
            if (userId == null || userId.equals("-1") || userId.trim().isEmpty()) {
                throw new UnauthorizedException("Unauthorized: Missing X-USER-ID header");
            }
            return originDataFetcher.get(dataFetchingEnvironment);
        };

        environment.getCodeRegistry().dataFetcher(parentType, fieldDefinition, authDataFetcher);

        return fieldDefinition;
    }
}
