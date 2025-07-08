package com.elearn.app.controllers;


import com.elearn.app.config.AppConstant;
import com.elearn.app.dtos.*;
import com.elearn.app.services.CourseService;
import com.elearn.app.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //create course
    @PostMapping
    public ResponseEntity<CourseDto> insert(
            @RequestBody CourseDto courseDto){

        CourseDto savedCourseDto = courseService.create(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourseDto);


    }

    // get:all course
    @GetMapping
    public CustomPageResponse<CourseDto> gerAllCourses(
            @RequestParam(value = "pageNo",required = false,defaultValue = AppConstant.DEFAULT_COURSE_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize",required = false,defaultValue = AppConstant.DEFAULT_COURSE_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy",required = false,defaultValue = AppConstant.DEFAULT_COURSE_SORT_BY) String sortBy
    ){

        CustomPageResponse<CourseDto> customPageRespose = courseService.getAllCourse(pageNo, pageSize, sortBy);

        return customPageRespose;
    }

    //get single course
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(
            @PathVariable("id") String courseId
    ){

        CourseDto course = courseService.getCourse(courseId);

        return ResponseEntity.status(HttpStatus.OK).body(course);

    }

    //delete course

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomeMessage> remove(
           @PathVariable("id") String courseId
    ){

        courseService.delete(courseId);
        CustomeMessage message=new CustomeMessage();
        message.setMessage("deleted successfully!!");
        message.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(message);

    }

    // update course
    @PutMapping("/{id}")
    public CourseDto updateCourse(
            @PathVariable("id") String courseId,
            @RequestBody CourseDto courseDto
    ){
        return courseService.update(courseDto, courseId);
    }

    //search course by title or by shortdesc

    @GetMapping("/search")
    public List<CourseDto> searchCourse(
          @RequestParam(value = "keyword",required = true,defaultValue = "")  String keyword
    ){

        List<CourseDto> courseDtos = courseService.searchByTitleOrShortDescription(keyword);
        return courseDtos;

    }


    //get All the categories in which a specific course is present

    @GetMapping("/{courseId}/categories")
    public ResponseEntity<List<CategoryDto>> getCategoriesOfACourse(
           @PathVariable("courseId") String courseId
    ){

        return ResponseEntity.ok(courseService.getCategoriesOfCourse(courseId));

    }

    //add video to a course

    @PostMapping("/{courseId}/videos/{videoId}")
    public ResponseEntity<CustomeMessage> addVideoToCourse(
         @PathVariable(value = "courseId")   String courseId,
         @PathVariable(value = "videoId")    String videoId
    ){
        courseService.addVideoToACourse(courseId,videoId);
        CustomeMessage customeMessage=new CustomeMessage();
        customeMessage.setMessage("video add successfully in "+courseService.getCourse(courseId).getCourseTitle()+" course!!");
        customeMessage.setSuccess(true);

        return  ResponseEntity.ok(customeMessage);
    }

    //get all videos of a course

    @GetMapping("/{courseId}/videos")
    public List<VideoDto> getVideosOfCourse(
           @PathVariable("courseId") String courseId
    ){

        List<VideoDto> videosOfACourse = courseService.getVideosOfACourse(courseId);
        return videosOfACourse;

    }

    //uploading banner to a course
    @Autowired
    private FileService fileService;

    @PostMapping("/{courseId}/banners")
    public ResponseEntity<CourseDto> uploadBanner(
        @PathVariable("courseId")   String courseId,
        @RequestParam("banner")  MultipartFile file
    ){


        CourseDto courseDto = courseService.saveBanner(file, courseId);
        return ResponseEntity.ok(courseDto);

    }

    //serve saved banner/file to client

    @GetMapping("/{courseId}/banners")
    public ResponseEntity<Resource> serveBanner(
            @PathVariable("courseId") String courseId
    ){

        CustomResource resource =courseService.getCourseBannerById(courseId);

        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(resource.getResourceContentType())).body(resource.getResource());


    }


}
