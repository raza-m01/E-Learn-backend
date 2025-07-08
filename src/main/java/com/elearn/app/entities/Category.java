package com.elearn.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude ="courses")
@EqualsAndHashCode(exclude ="courses")
public class Category {

    @Id
    private String categoryId;

    @Column(unique = true,nullable = false)
    private String categoryTitle;

    @Column(name="description",length = 2000)
    private String categoryDesc;

    private Date addedDate;

    //course-mapping Relation
    @ManyToMany(mappedBy = "categoryList",cascade = CascadeType.ALL)
    private List<Course> courses=new ArrayList<>();



    public void addCourse(Course course){
        courses.add(course);
        course.getCategoryList().add(this);
    }

    public void removeCourse(Course course){
        courses.remove(course);
        course.getCategoryList().remove(this);
    }




}
