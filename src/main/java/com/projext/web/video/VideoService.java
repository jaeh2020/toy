package com.projext.web.video;

import com.projext.domain.video.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private VideoRepository videoRepository;


    // 모든 비디오 목록 조회
    public List<Video> AllVideo(){
        return videoRepository.findAll();
    }

    // 비디오 ID로 비디오 조회
    public Video VideoById(Long id){
        return videoRepository.findById(id).orElse(null);
    }

    // 비디오 업로드
    public void uploadVideo(Video video){
        videoRepository.save(video);
    }

    // 비디오 수정
    public void updateVideo(Long id, Video updateVideo){
        Video video = videoRepository.findById(id).orElse(null);
        if(video!= null){
            video.setTitle(updateVideo.getTitle());
            video.setUrl(updateVideo.getUrl());
            videoRepository.save(video);
        }
    }

    // 비디오 삭제
    public void deleteVideo(Long id){
        videoRepository.deleteById(id);
    }
}
