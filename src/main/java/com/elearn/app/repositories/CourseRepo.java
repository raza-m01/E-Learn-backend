package com.elearn.app.repositories;

import com.elearn.app.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course,String> {


    //custom method
    Optional<Course> findByCourseTitle(String courseTitle);

    List<Course> findByLive(boolean live);

    //search course By title keyword

    List<Course> findByCourseTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(String keyword,String keyword1);
}
