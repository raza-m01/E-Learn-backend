package com.elearn.app.exceptions;


import com.elearn.app.dtos.CustomeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<CustomeMessage> hanldeResourceNotFoundException(ResourseNotFoundException exception){

        CustomeMessage customeMessage = new CustomeMessage();
        customeMessage.setMessage(exception.getMessage());
        customeMessage.setSuccess(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(customeMessage);


    }

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
}
