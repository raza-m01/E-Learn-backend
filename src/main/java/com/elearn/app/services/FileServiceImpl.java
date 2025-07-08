package com.elearn.app.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileServiceImpl implements FileService{
    @Override
    public String saveFile(MultipartFile file, String directoryPath, String fileName) {

        // create folder in your project structure

        Path folderPath = Paths.get(directoryPath);//  gives path like this which is a object of Path type-> example-> uploads/courses/banners

        //get full file path(path along with file name-> means actual filePath)
        Path filePath = Paths.get(folderPath.toString(), file.getOriginalFilename());// gives like-> ex-> uploads/courses/banners/file_name.jpeg
        System.out.println(filePath);
        try{
            //creating directory on the given path/location
            Files.createDirectories(folderPath);

            //write the file on to that path in the directory.
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        return filePath.toString();
    }
}
