package org.example.elsgraphql.resolver;

import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.example.elsgraphql.model.Course;
import org.example.elsgraphql.model.Enrollment;
import org.example.elsgraphql.model.Payment;
import org.example.elsgraphql.model.User;
import org.example.elsgraphql.service.CourseService;
import org.example.elsgraphql.service.EnrollmentService;
import org.example.elsgraphql.service.UserService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class EnrollmentDataResolver {

    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public EnrollmentDataResolver(
            UserService userService,
            CourseService courseService,
            EnrollmentService enrollmentService,
            BatchLoaderRegistry registry
    ) {
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;

        registry.forTypePair(Long.class, Course.class).registerMappedBatchLoader(
                (courseIds, env) -> {
                    List<Long> ids = courseIds.stream().toList();
                    log.info("### -- batch getCourse {} ---", ids);
                    return Mono.justOrEmpty(courseService.findCourseByIds(ids)
                            .stream()
                            .collect(Collectors.toMap(Course::getId, course -> course))
                    );
                }
        );
    }

    @SchemaMapping(typeName = "Enrollment", field = "user")
    public User getUser(Enrollment enrollment) {
        return userService.findById(enrollment.getUserId()).orElse(null);
    }
//    @SchemaMapping(typeName = "Enrollment", field = "course")
//    public Course getCourse(Enrollment enrollment) {
//        log.info("### --- getCourse load {} ---", enrollment.getCourseId());
//        return courseService.findCourseById(enrollment.getCourseId()).orElse(null);
//    }

    @SchemaMapping(typeName = "Enrollment", field = "course")
    public CompletableFuture<Course> getCourse(Enrollment enrollment, DataLoader<Long, Course> loader) {
        log.info("### --- getCourse load {} ---", enrollment.getCourseId());
        return loader.load(enrollment.getCourseId());
    }

    @SchemaMapping(typeName = "Enrollment", field = "payment")
    public Payment getPayment(Enrollment enrollment) {
        return enrollmentService.findPaymentById(enrollment.getPaymentId());
    }
}