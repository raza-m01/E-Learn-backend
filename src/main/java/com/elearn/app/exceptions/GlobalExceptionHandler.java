package com.elearn.app.exceptions;


import com.elearn.app.dtos.CustomeMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // catching my own custom Created Exception which is thrown when Resource not found
    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<CustomeMessage> hanldeResourceNotFoundException(ResourseNotFoundException exception){

        CustomeMessage customeMessage = new CustomeMessage();
        customeMessage.setMessage(exception.getMessage());
        customeMessage.setSuccess(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(customeMessage);


    }


    //catching exception thrown at dto, i have applied pre-defined validation at dto fields, this will catch the exception occur when dto fields has failed the validation.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationError(MethodArgumentNotValidException exception){
        Map<String,String> errors =new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName= ((FieldError)error).getField();
            String errorMessage=error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

    }


    // catching and handling exception thrown by our own custom Validation annotation when our own custom validation annotation is throwing exception when validation fails.
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Set<String>> handleConstraintViolations(ConstraintViolationException ex){


        Set<String> validationErrors = ex.getConstraintViolations().stream().map((error) -> error.getMessage()).collect(Collectors.toSet());

/*
        Set<String> validationErrors=new HashSet<>();

        String error = ex.getConstraintViolations().stream().map((cv) -> cv.getMessage()).findFirst().orElse("Validation error!!!");

        CustomeMessage message=new CustomeMessage();

        message.setMessage(error);
        message.setSuccess(false);*/

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);

    }
}
