package com.elearn.app.custom_annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageContentType implements ConstraintValidator<ValidImageContentType, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {

        // check for empty and null // enforcing non-null
        if(file.isEmpty() || file==null){
                return false;
        }

        String contentType=file.getContentType();

        return contentType !=null &&
                (contentType.equalsIgnoreCase("image/png")||contentType.equalsIgnoreCase("image/jpeg"));


    }
}
