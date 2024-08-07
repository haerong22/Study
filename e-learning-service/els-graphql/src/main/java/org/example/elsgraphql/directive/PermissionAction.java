package org.example.elsgraphql.directive;

import graphql.schema.DataFetchingEnvironment;
import lombok.Getter;

import java.util.function.Consumer;


public class PermissionAction {
    @Getter
    private final String permission;
    private final Consumer<DataFetchingEnvironment> action;

    public PermissionAction(String permission, Consumer<DataFetchingEnvironment> action) {
        this.permission = permission;
        this.action = action;
    }

    public void executeAction(DataFetchingEnvironment env) {
        if (action != null) {
            action.accept(env);
        }
    }
}