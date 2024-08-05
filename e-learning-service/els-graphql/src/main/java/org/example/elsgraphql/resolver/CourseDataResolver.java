package org.example.elsgraphql.resolver;

import lombok.RequiredArgsConstructor;
import org.example.elsgraphql.model.Course;
import org.example.elsgraphql.model.CourseSession;
import org.example.elsgraphql.model.User;
import org.example.elsgraphql.service.CourseService;
import org.example.elsgraphql.service.UserService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseDataResolver {

    private final CourseService courseService;
    private final UserService userService;

    @SchemaMapping(typeName = "Course", field = "courseSessions")
    public List<CourseSession> getSessions(Course course) {
        return courseService.findAllSessionsByCourseId(course.getId());
    }

    @SchemaMapping(typeName = "Course", field = "instructor")
    public User getInstructor(Course course) {
        return userService.findById(course.getInstructorId()).orElseThrow();
    }
}