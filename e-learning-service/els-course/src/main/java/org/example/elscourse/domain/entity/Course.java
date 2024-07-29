package org.example.elscourse.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private Long instructorId;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<CourseSession> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<CourseRating> ratings = new ArrayList<>();

    public Course(Long courseId) {
        this.id = courseId;
    }
}
