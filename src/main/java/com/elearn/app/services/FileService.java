package com.elearn.app.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveFile(MultipartFile file,String  directoryPath,String fileName);
}
