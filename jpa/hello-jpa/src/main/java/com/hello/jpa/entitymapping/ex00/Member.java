package com.hello.jpa.entitymapping.ex00;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//@Entity
@Table(name = "Member2")
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false, length = 10)
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    private LocalDate localDate;
    private LocalDateTime localDateTime;

    @Lob
    private String description;

    @Transient
    private int temp;

    public Member() {
    }

    public Member(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
