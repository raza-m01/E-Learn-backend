package com.elearn.app.services;

import com.elearn.app.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {

    CourseDto create(CourseDto courseDto);

    CustomPageResponse<CourseDto> getAllCourse(int pageNo, int pageSize, String sortBy);

    CourseDto getCourse(String courseId);

    CourseDto update(CourseDto courseDto ,String courseId);

    void delete(String courseId);

    List<CourseDto> searchByTitleOrShortDescription(String keyWord);


    List<CategoryDto> getCategoriesOfCourse(String courseId);

    void addVideoToACourse(String courseId,String videoId);


    List<VideoDto> getVideosOfACourse(String courseId);

    CourseDto saveBanner(MultipartFile file, String courseId);

    CustomResource getCourseBannerById(String courseId);
}
