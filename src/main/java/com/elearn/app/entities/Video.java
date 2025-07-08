package com.elearn.app.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.BatchSize;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "course")
@EqualsAndHashCode(exclude = "course")
@Entity
public class Video {

    @Id
    private String videoId;

    @Column(nullable = false)
    private String videoTitle;

    @Column(length = 2000)
    private String videoDesc;

    private String videoFilePath;

    private String contentType;

    //course-mapping Relation
    @ManyToOne
    private Course course;

}
