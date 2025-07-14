package com.elearn.app.services;


import com.elearn.app.config.AppConstant;
import com.elearn.app.dtos.*;
import com.elearn.app.entities.Category;
import com.elearn.app.entities.Course;
import com.elearn.app.entities.Video;
import com.elearn.app.exceptions.ResourseNotFoundException;
import com.elearn.app.repositories.CourseRepo;
import com.elearn.app.repositories.VideoRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepo courseRepo;

    private ModelMapper modelMapper;

    private VideoRepo videoRepo;

    public CourseServiceImpl(CourseRepo courseRepo, ModelMapper modelMapper, VideoRepo videoRepo) {
        this.courseRepo = courseRepo;
        this.modelMapper = modelMapper;
        this.videoRepo = videoRepo;
    }

    @Autowired
    private FileService fileService;



    @Override
    public CourseDto create(CourseDto courseDto) {

        String courseId= UUID.randomUUID().toString();
        courseDto.setCourseId(courseId);

        Date createdDate = new Date();

        courseDto.setCreatedDate(createdDate);

//        courseRepo.save() // here we need to pass entity as .save() take entity as parameter.so we need to map dtoToEntity.
        Course savedCourse = courseRepo.save(this.dtoToEntity(courseDto));

        return this.entityToDto(savedCourse);
    }

    @Override
    public CustomPageResponse<CourseDto> getAllCourse(int pageNo, int pageSize, String sortBy) {

        Sort sort= Sort.by(sortBy).descending();
        PageRequest pageRequest=PageRequest.of(pageNo,pageSize,sort);
        Page<Course> coursePage = courseRepo.findAll(pageRequest); // provide page content as well as page meta data.
        List<Course> contentList = coursePage.getContent();

        List<CourseDto> courseListDto = contentList.stream().map((course) -> modelMapper.map(course, CourseDto.class)).toList();
        // by returning courseListDto we could send only the content of current page to the client, but it's recommended to send page meta data also to the client.
        CustomPageResponse<CourseDto> customPageResponse=new CustomPageResponse<>();

        customPageResponse.setPageNo(coursePage.getNumber());
        customPageResponse.setPageSize(coursePage.getSize());
        customPageResponse.setTotalElements(coursePage.getTotalElements());
        customPageResponse.setLast(coursePage.isLast());
        customPageResponse.setTotalPages(coursePage.getTotalPages());
        customPageResponse.setContent(courseListDto);

        return customPageResponse;
    }

    @Override
    public CourseDto getCourse(String courseId) {

        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourseNotFoundException("course is not available"));
        return modelMapper.map(course,CourseDto.class);

    }

    @Override
    public CourseDto update(CourseDto courseDto, String courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourseNotFoundException("course not available!!"));

        // this is overloaded method of modelMapper.map()
        //by this we map dto to entity but skipping the courseId filed in the entity which and primary key and tracked by hibernate,if we change it  will throw exception
        modelMapper.map(courseDto,course);

        // passing one by one , means setting dto value in course entity one by one
//        course.setCourseTitle(courseDto.getCourseTitle());
//        course.setShortDesc(courseDto.getShortDesc());
//        course.setLongDesc(courseDto.getLongDesc());
//        course.setCoursePrice(courseDto.getCoursePrice());
//        course.setLive(courseDto.isLive());
//        course.setDiscount(courseDto.getDiscount());
//        course.setCourseBannerFilePath(courseDto.getCourseBannerFilePath());

        Course updatedCourse = courseRepo.save(course);
        return modelMapper.map(updatedCourse,CourseDto.class);
    }

    @Override
    public void delete(String courseId) {

        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourseNotFoundException("Course not found"));
        courseRepo.delete(course); // or we can directly use deleteById


    }

    @Override
    public List<CourseDto> searchByTitleOrShortDescription(String keyWord) {
        List<Course> coursesList = courseRepo.findByCourseTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(keyWord,keyWord);

        return coursesList
                .stream()
                .map((course)->modelMapper.map(course,CourseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public List<CategoryDto> getCategoriesOfCourse(String courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourseNotFoundException("course not found!!"));
        List<Category> categoryList = course.getCategoryList();

        List<CategoryDto> list = categoryList.stream().map((category) -> modelMapper.map(category, CategoryDto.class)).toList();
        return list;
    }

    // Add video to a course.
    @Override
    @Transactional
    public void addVideoToACourse(String courseId,String videoId){
        //session start

        Video video = videoRepo.findById(videoId).orElseThrow(() -> new ResourseNotFoundException("video not found!!"));

        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourseNotFoundException("course not found!!!"));

        //as video is owning the relationship so this should be used
        // Important: set the owning side
        video.setCourse(course);

//        // Optional: update the inverse side
//        course.getVideos().add(video);
//        courseRepo.save(course);

        //must save the owning side
        videoRepo.save(video);

        System.out.println("video added in course!!");

        //session end

    }

    @Override
    public List<VideoDto> getVideosOfACourse(String courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourseNotFoundException("course id not present"));
        List<Video> videos = course.getVideos();
        return videos.stream().map((video)->modelMapper.map(video,VideoDto.class)).toList();
    }

    @Override
    public CourseDto saveBanner(MultipartFile file, String courseId) {

        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourseNotFoundException("Course not found!!"));

        // saving file at the given path, it will return the full file path after saving
        String filePath = fileService.saveFile(file, AppConstant.COURSE_BANNER_UPLOAD_DIR, file.getOriginalFilename());

        // setting the file path in course dto
        course.setCourseBannerFilePath(filePath);
        course.setBannerContentType(file.getContentType());

        // saving the course dto to database
        Course savedCourse = courseRepo.save(course);

        return modelMapper.map(savedCourse,CourseDto.class);
    }

    @Override
    public CustomResource getCourseBannerById(String courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourseNotFoundException("course not found!!"));
        String courseBannerFilePath = course.getCourseBannerFilePath();

        //converting string courseBannerFilePath into Path type
        Path bannerFilePath = Paths.get(courseBannerFilePath);

        //getting resource that is present on the given path
        Resource resource = new FileSystemResource(bannerFilePath);

        //now set this resource in our customResource class
        CustomResource customResource=new CustomResource();
        customResource.setResource(resource);
        customResource.setResourceContentType(course.getBannerContentType());

        return customResource;
    }


    // instead of manual mapping , we use a dependency modal mapper->ModelMapper bean config is done in Project config

    // method to map dtoToEntity
    public Course dtoToEntity(CourseDto dto){

//        Course course=new Course();
//        course.setCourseId(dto.getCourseId());
//        course.setCourseTitle(dto.getCourseTitle());
//        .....more field mapping like above

        Course course = modelMapper.map(dto, Course.class);
        return course;
    }

    // method to map EntityToDto
    public CourseDto entityToDto(Course course){

//        CourseDto dto=new CourseDto();
//        dto.setCourseId(course.getCourseId());
//        dto.setCourseTitle(course.getCourseTitle());
////        ....mapping of field

        CourseDto dto = modelMapper.map(course, CourseDto.class);

        return dto;
    }




}
