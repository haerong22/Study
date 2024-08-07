package org.example.elsgraphql.directive;

import graphql.schema.DataFetchingEnvironment;
import org.example.elsgraphql.exception.UnauthorizedException;
import org.example.elsgraphql.model.CourseSession;
import org.example.elsgraphql.service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class RolePermissionService {
    private static final Logger log = LoggerFactory.getLogger(RolePermissionService.class);
    private final Map<String, Set<PermissionAction>> rolePermissions = new HashMap<>();
    private final EnrollmentService enrollmentService;

    public RolePermissionService(EnrollmentService enrollmentService) {
        initializeRoles();
        this.enrollmentService = enrollmentService;
    }

    public boolean checkPermission(String role, String permission, DataFetchingEnvironment env) {
        Set<PermissionAction> actions = rolePermissions.get(role);
        if (actions != null) {
            for (PermissionAction action : actions) {
                if (action.getPermission().equals(permission)) {
                    action.executeAction(env);
                    return true;
                }
            }
        }
        return false;
    }

    private void initializeRoles() {
        Set<PermissionAction> userPermissions = new HashSet<>();
        userPermissions.add(new PermissionAction("read_user", env -> {
            long headerUserId = Long.parseLong(env.getGraphQlContext().get("X-USER-ID"));
            long argumentUserId = Long.valueOf(env.getArgument("userId"));

            if (headerUserId != argumentUserId) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));
        userPermissions.add(new PermissionAction("update_user", env -> {
            long headerUserId = Long.parseLong(env.getGraphQlContext().get("X-USER-ID"));
            long argumentUserId = Long.parseLong(env.getArgument("userId"));

            if (headerUserId != argumentUserId) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));
        userPermissions.add(new PermissionAction("read_purchase", env -> {

            long headerUserId = Long.parseLong(env.getGraphQlContext().get("X-USER-ID"));
            long argumentUserId = Long.parseLong(env.getArgument("userId"));
            long argumentCourseId = Long.parseLong(env.getArgument("courseId"));

            if (headerUserId != argumentUserId) {
                throw new UnauthorizedException("Unauthorized");
            }

            if (!enrollmentService.checkCourseAccess(argumentCourseId, argumentUserId)) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));
        userPermissions.add(new PermissionAction("read_files", env -> {
            long userId = Long.parseLong(env.getGraphQlContext().get("X-USER-ID"));
            CourseSession session = env.getSource();
            long courseId = session.getCourseId();

            if (!enrollmentService.checkCourseAccess(courseId, userId)) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));
        userPermissions.add(new PermissionAction("read_enrollment", env -> {
            long headerUserId = Long.parseLong(env.getGraphQlContext().get("X-USER-ID"));
            long argumentUserId = Long.parseLong(env.getArgument("userId"));

            if (headerUserId != argumentUserId) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));
        userPermissions.add(new PermissionAction("update_course", env -> {
            String headerUserRole = env.getGraphQlContext().get("X-USER-ROLE");

            if (!headerUserRole.equals("ADMIN")) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));
        userPermissions.add(new PermissionAction("update_record", env -> {
            String headerUserRole = env.getGraphQlContext().get("X-USER-ROLE");

            if (!headerUserRole.equals("ADMIN")) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));
        userPermissions.add(new PermissionAction("update_purchase", env -> {
            long headerUserId = Long.parseLong(env.getGraphQlContext().get("X-USER-ID"));
            long argumentUserId = Long.parseLong(env.getArgument("userId"));

            if (headerUserId != argumentUserId) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));

        Set<PermissionAction> adminPermissions = new HashSet<>();
        adminPermissions.add(new PermissionAction("create_course", env -> {
            String headerUserRole = env.getGraphQlContext().get("X-USER-ROLE");

            if (!headerUserRole.equals("ADMIN")) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));
        adminPermissions.add(new PermissionAction("update_course", env -> {
            String headerUserRole = env.getGraphQlContext().get("X-USER-ROLE");

            if (!headerUserRole.equals("ADMIN")) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));
        adminPermissions.add(new PermissionAction("update_user", env -> {
            String headerUserRole = env.getGraphQlContext().get("X-USER-ROLE");

            if (!headerUserRole.equals("ADMIN")) {
                throw new UnauthorizedException("Unauthorized");
            }
        }));

        rolePermissions.put("user", userPermissions);
        rolePermissions.put("admin", adminPermissions);
    }
}
