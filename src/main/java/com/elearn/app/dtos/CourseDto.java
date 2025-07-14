package com.elearn.app.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private String courseId;

    private String courseTitle;

    private String shortDesc;

    private String longDesc;

    private Double coursePrice;

    private boolean live =false;

    private double discount;

    private Date createdDate;

    private String courseBannerFilePath;

    private List<VideoDto> videos =new ArrayList<>();
//
//    @JsonIgnore
//    private List<CategoryDto> categoryList=new ArrayList<>();


    public String getBannerUrl(){

        return "http://localhost:8082/api/v1/courses/"+courseId+"/banners";

    }

}
