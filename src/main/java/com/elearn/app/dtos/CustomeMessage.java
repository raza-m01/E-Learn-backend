package com.elearn.app.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//generally used when controller/service does not return any thing/void
@Getter
@Setter
@NoArgsConstructor
public class CustomeMessage {

    private String message;

    private boolean success;
}
