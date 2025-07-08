package com.elearn.app.services;

import com.elearn.app.dtos.VideoDto;

import java.util.List;

public interface VideoService {

    //create
    VideoDto insert(VideoDto videoDto);

    //get:all
    List<VideoDto> getAllVideo();

    //get single
    VideoDto getVideo(String videoId);

    //delete
    void delete(String videoId);

    //update
    VideoDto updateVideo(VideoDto videoDto,String videoId);

    //search by videoTitleOrVideoDesc

    List<VideoDto> searchVideo(String keyword);


}
