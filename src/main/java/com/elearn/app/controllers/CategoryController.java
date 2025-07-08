package com.elearn.app.controllers;


import com.elearn.app.config.AppConstant;
import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.dtos.CustomeMessage;
import com.elearn.app.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //create category
    @PostMapping
    public ResponseEntity<?> create(
           @Valid @RequestBody CategoryDto categoryDto
//           BindingResult bindingResult

    ){

//          this not the best to handle exception thrown by the dto bean on validation fails. best way to handle this in Global exception handler
//        if(bindingResult.hasErrors()){
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
//        }

        CategoryDto createdCategory = categoryService.insert(categoryDto);

        //send custom response/ here we can send created status 201[created]
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdCategory);

    }

    // get all category
    @GetMapping
    public CustomPageResponse<CategoryDto> getAllCategories(
            @RequestParam(value = "pageNo",required = false,defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize",required = false,defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy",required = false,defaultValue = AppConstant.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "sortDir",required = false,defaultValue = AppConstant.DEFAULT_SORT_DIR) String sortDir

    ){

        CustomPageResponse<CategoryDto> customPageResponse = categoryService.getAll(pageNo, pageSize,sortBy,sortDir);

        return customPageResponse;
    }

    //get category by id
    @GetMapping("/{id}")
    public CategoryDto getCategory(
           @PathVariable("id") String categoryId){
        return categoryService.get(categoryId);
    }

    // delete category

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomeMessage> deleteCategory(
            @PathVariable("id") String categoryId
    ){

        categoryService.delete(categoryId);

        CustomeMessage message=new CustomeMessage();
        message.setMessage("Category "+categoryId+" Deleted Successfully!");
        message.setSuccess(true);

        return  ResponseEntity.status(HttpStatus.OK).body(message);

    }

    // update category
    @PutMapping("/{id}")
    public CategoryDto updateCategory(
           @PathVariable("id") String categoryId,
           @RequestBody CategoryDto categoryDto){

        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);

        return updatedCategory;
    }


    //nested end points
    // adding course to a category.
    @PostMapping("/{categoryId}/courses/{courseId}")
    public ResponseEntity<CustomeMessage> addCourseToCategory(
           @PathVariable String categoryId,
           @PathVariable String courseId
    ){

        categoryService.addCourseCategory(categoryId,courseId);

        CustomeMessage customeMessage=new CustomeMessage();

        customeMessage.setMessage("Course successfully added in the "+categoryService.get(categoryId).getCategoryTitle()+" category");
        customeMessage.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(customeMessage);

    }

    //get All courses of a particular categories

    @GetMapping("/{categoryId}/courses")
    public ResponseEntity<List<CourseDto>> getCoursesOfACategory(
            @PathVariable(value = "categoryId") String categoryId
    ){

        return ResponseEntity.ok(categoryService.getCourseOfCat(categoryId));

    }


}
