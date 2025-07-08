package com.elearn.app.services;

import com.elearn.app.dtos.VideoDto;
import com.elearn.app.entities.Video;
import com.elearn.app.exceptions.ResourseNotFoundException;
import com.elearn.app.repositories.VideoRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VideoServiceImp implements VideoService{

    private VideoRepo videoRepo;
    private ModelMapper modelMapper;

    public VideoServiceImp(VideoRepo videoRepo, ModelMapper modelMapper) {
        this.videoRepo = videoRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public VideoDto insert(VideoDto videoDto) {

        //setting videoId
        String videoId= UUID.randomUUID().toString();
        videoDto.setVideoId(videoId);
        Video savedVideo = videoRepo.save(modelMapper.map(videoDto, Video.class));

        return modelMapper.map(savedVideo, VideoDto.class);
    }

    @Override
    public List<VideoDto> getAllVideo() {

        List<Video> all = videoRepo.findAll();
        List<VideoDto> list = all.stream().map((video) -> modelMapper.map(video, VideoDto.class)).toList();
        return list;
    }

    @Override
    public VideoDto getVideo(String videoId) {
        Video video = videoRepo.findById(videoId).orElseThrow(() -> new ResourseNotFoundException("Video not Available!"));
        return modelMapper.map(video, VideoDto.class);
    }

    @Override
    public void delete(String videoId) {

        Video video = videoRepo.findById(videoId).orElseThrow(() -> new ResourseNotFoundException("video not available!!"));

        videoRepo.delete(video);

    }

    @Override
    public VideoDto updateVideo(VideoDto videoDto, String videoId) {
        Video video = videoRepo.findById(videoId).orElseThrow(() -> new ResourseNotFoundException("video not available!!"));
        video.setVideoTitle(videoDto.getVideoTitle());
        video.setVideoDesc(videoDto.getVideoDesc());
        video.setVideoFilePath(videoDto.getVideoFilePath());
        video.setContentType(videoDto.getContentType());
        Video savedVideo = videoRepo.save(video);
        return modelMapper.map(savedVideo, VideoDto.class);



    }

    @Override
    public List<VideoDto> searchVideo(String keyword) {
        List<Video> videos= videoRepo.findByVideoTitleContainingIgnoreCaseOrVideoDescContainingIgnoreCase(keyword, keyword);
        List<VideoDto> list = videos.stream().map((video) -> modelMapper.map(video, VideoDto.class)).toList();
        return list;
    }
}
