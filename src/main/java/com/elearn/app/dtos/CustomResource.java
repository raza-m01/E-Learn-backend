package com.elearn.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

//custom resource type to send resource to the client.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomResource {

    private  Resource resource;
    private  String resourceContentType;
}
