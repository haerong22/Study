package org.example.elsgraphql.resolver;

import lombok.RequiredArgsConstructor;
import org.example.elsgraphql.model.Course;
import org.example.elsgraphql.model.CourseRating;
import org.example.elsgraphql.model.User;
import org.example.elsgraphql.service.CourseService;
import org.example.elsgraphql.service.UserService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CourseRatingDataResolver {

    private final CourseService courseService;
    private final UserService userService;

    @SchemaMapping(typeName = "CourseRating", field = "course")
    public Course getCourse(CourseRating courseRating) {
        return courseService.findCourseById(courseRating.getCourseId()).orElseThrow();
    }

    @SchemaMapping(typeName = "CourseRating", field = "user")
    public User getUser(CourseRating courseRating) {
        return userService.findById(courseRating.getUserId()).orElseThrow();
    }
}