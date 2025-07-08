package com.elearn.app.services;

import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.entities.Category;
import com.elearn.app.entities.Course;
import com.elearn.app.exceptions.ResourseNotFoundException;
import com.elearn.app.repositories.CategoryRepo;
import com.elearn.app.repositories.CourseRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService{

    private CategoryRepo categoryRepo;

    private CourseRepo courseRepo;

    private ModelMapper modelMapper;

    public CategoryServiceImp(CategoryRepo categoryRepo, CourseRepo courseRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.courseRepo = courseRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto insert(CategoryDto categoryDto) {

        //create categoryId
        String catId= UUID.randomUUID().toString();

        categoryDto.setCategoryId(catId);
        //addedDate
        categoryDto.setAddedDate(new Date());

        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepo.save(category);

        return modelMapper.map(savedCategory,CategoryDto.class);

    }

//    @Override
//    public List<CategoryDto> getAll() {
//        List<Category> allCategories = categoryRepo.findAll();
//        return allCategories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
//    }
    // using pagination in getAllCategory
    @Override
    public CustomPageResponse<CategoryDto> getAll(int pageNo,int pageSize,String sortBy,String sortDir) {

//        Sort sort= Sort.by(sortBy).descending();

        Sort sort= sortDir.equals("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        //pageable- pageRequest
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize,sort);

        Page<Category> categoryPage = categoryRepo.findAll(pageRequest);    // this categoryPage object contains various info. like pageSize , current pageNo, totalNoOfPage isLastPage,isFirst, pageContent and many more

        List<Category> cotentList = categoryPage.getContent();
        List<CategoryDto> contentListDTO = cotentList.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList(); // converting into DTO

        // so we can return this contentListDTO but it's better to send all the other info to the client/front-end along with content. like pageNo,pageSize , totalNoOfElement,isLast,etc.
        // to send all other info along with page content we make a dto in that dto we will send all these -> i make customPageResponse DTO
        CustomPageResponse<CategoryDto> customPageResponse=new CustomPageResponse<>();

       customPageResponse.setContent(contentListDTO);
       customPageResponse.setTotalElements(categoryPage.getTotalElements());
       customPageResponse.setTotalPages(categoryPage.getTotalPages());
       customPageResponse.setPageNo(categoryPage.getNumber());
       customPageResponse.setPageSize(categoryPage.getSize());
       customPageResponse.setLast(categoryPage.isLast());


        return customPageResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("category " + categoryId + " not available!"));
        return modelMapper.map(category,CategoryDto.class);

    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category you are trying to delete is Not Available!"));
        categoryRepo.delete(category);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourseNotFoundException("category not available!"));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDesc(categoryDto.getCategoryDesc());
        Category updatedCategory = categoryRepo.save(category);
        return modelMapper.map(updatedCategory,CategoryDto.class);
    }



    @Override
    @Transactional
    public void addCourseCategory(String categoryId, String courseId) {

        //get category

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("category not found!!!"));


        // get course

        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourseNotFoundException("course not found!!!"));


        //add course to the category

        //  category me jo course list hai usme course add ho gaya
        // and iss course me jo category list hai usme ye waali category addd ho gaya
        category.addCourse(course);

        categoryRepo.save(category);

        System.out.println("category relation ship updated!!");

    }

    @Override
    @Transactional
    public List<CourseDto> getCourseOfCat(String categoryId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("category not found !"));
        List<Course> courses = category.getCourses();

        return courses.stream().map((course) -> modelMapper.map(course, CourseDto.class)).toList();
    }
}
