package com.elearn.app.dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotEmpty(message = "title is required!")
    @Size(min=3,max = 50,message = "size must be in between 3 and 50 character")
    private String categoryTitle;

    @NotEmpty(message = "description is required!!")
    private String categoryDesc;

    private Date addedDate;




}
