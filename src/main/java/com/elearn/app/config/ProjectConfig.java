package com.elearn.app.config;


import com.elearn.app.dtos.CourseDto;
import com.elearn.app.entities.Course;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper=new ModelMapper();
        // Skip mapping the ID when updating Course entity
        modelMapper.typeMap(CourseDto.class, Course.class)
                .addMappings(mapper -> mapper.skip(Course::setCourseId));
        return  modelMapper;
    }
}
