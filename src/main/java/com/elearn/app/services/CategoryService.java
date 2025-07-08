package com.elearn.app.services;

import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomPageResponse;

import java.util.List;

public interface CategoryService {

    //create
     CategoryDto insert(CategoryDto categoryDto);

    //get:all
     CustomPageResponse<CategoryDto> getAll(int pageNo, int pageSize,String sortBy,String sortDir);

    //get
    CategoryDto get(String categoryId);


    //delete
    void delete(String categoryId);

    //update category.
    CategoryDto update(CategoryDto categoryDto,String categoryId);


    void addCourseCategory(String categoryId,String courseId);

    List<CourseDto> getCourseOfCat(String categoryId);

}
