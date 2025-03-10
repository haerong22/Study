package org.example.elsgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Long instructorId;
    private List<CourseSession> courseSessions;
    private List<CourseRating> ratings;
}