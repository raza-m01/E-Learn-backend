package com.elearn.app.controllers;


import com.elearn.app.dtos.CustomeMessage;
import com.elearn.app.dtos.VideoDto;
import com.elearn.app.services.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    private VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    //create/insert
    @PostMapping
    public ResponseEntity<VideoDto> create(
           @RequestBody VideoDto videoDto
    ){

        VideoDto savedVideoDto = videoService.insert(videoDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedVideoDto);

    }

    //get : All video

    @GetMapping
    public List<VideoDto> getAllVideo(){
        return videoService.getAllVideo();
    }

    //get single

    @GetMapping("{id}")
    public VideoDto getSingleVideo(
            @PathVariable("id")  String videoId){

        return videoService.getVideo(videoId);

    }



    //delete video

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomeMessage> remove(
           @PathVariable(value = "id") String videoId){

        videoService.delete(videoId);

        CustomeMessage customeMessage=new CustomeMessage();
        customeMessage.setMessage("Video deleted successfully!!");
        customeMessage.setSuccess(true);

        return ResponseEntity.status(HttpStatus.OK).body(customeMessage);

    }

    //update

    @PutMapping("/{id}")
    public VideoDto updateVideo(
            @PathVariable("id") String videoId,
            @RequestBody VideoDto videoDto
    ){

        VideoDto updatedVideo =videoService.updateVideo(videoDto,videoId);
        return updatedVideo;

    }

    //search video

    @GetMapping("/search")
    public List<VideoDto> searchVideo(
           @RequestParam(value = "keyword",required = true,defaultValue = "") String keyword
    ){

        List<VideoDto> videoDtos = videoService.searchVideo(keyword);
        return videoDtos;
    }


}
