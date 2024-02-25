package com.projext.controller.video;

import com.projext.DirectoryPath;
import com.projext.domain.file.FileStore;
import com.projext.domain.upload.UploadForm;
import com.projext.domain.video.Video;
import com.projext.web.video.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class VideoController {

    private final DirectoryPath directoryPath;
    private final VideoService videoService;
    private final FileStore fileStore;
    private static final String DIRECTORY_PATH = DirectoryPath.DIRECTORY_PATH;

    public VideoController(DirectoryPath directoryPath, VideoService videoService, FileStore fileStore) {
        this.directoryPath = directoryPath;
        this.videoService = videoService;
        this.fileStore = fileStore;
    }

    /* 비디오 게시판 */

    // 비디오 업로드 폼을 보여주는 페이지
    @GetMapping("/Video/videoUpload")
    public String showVideoUploadForm(Model model){
        model.addAttribute("uploadForm", new UploadForm());
        return "/Video/videoUpload";
    }

    // 비디오 업로드 처리
    @PostMapping("/Video/videoUpload")
    public String uploadVideo(@ModelAttribute UploadForm form, RedirectAttributes redirectAttributes) throws IOException {
        String filePath = String.valueOf(fileStore.storeFile(form.getVideoFile()));
        Video video = new Video();
        video.setVideoFilePath(filePath);
        videoService.uploadVideo(video);

        redirectAttributes.addFlashAttribute("message", "동영상이 성공적으로 업로드 되었습니다.");
        return "redirect:/Video/watchVideo";
    }

    // 비디오 리스트 출력
    @GetMapping("/Video/watchVideo")
    public String showAllVideos(Model model){
        List<Video> video = getVideoFromFolder(directoryPath.VIDEO_PATH);
        // 모든 비디오를 가져옴
        model.addAttribute("video", video);
        // 목록을 모델에 추가
        return "/Video/watchVideo";
    }

    // 비디오 시청 페이지
    @GetMapping("/Video/{videoId}")
    public String watchVideo(@PathVariable Long videoId, Model model){
        Video video = videoService.VideoById(videoId);
        model.addAttribute("video", video);
        log.info("watchVideo");
        return "/Video/watchVideo";
    }

    // 특정 폴더에서 비디오 파일을 가져와서 Video 객체로 변환하는 메서드
    private List<Video> getVideoFromFolder(String videoPath){
        List<Video> videos = new ArrayList<>();
        File folder = new File(videoPath);
        File[] files = folder.listFiles();

        if(files != null){
            for (File file : files) {
                if(file.isFile() && isVideoFile(file.getName())){
                    Video video = new Video();
                    video.setUploadName(file.getName());
                    video.setVideoFilePath(file.getPath());
                    videos.add(video);
                }
            }
        }
        return videos;
    }

    // 파일이 동영상 파일인지 확인하는 메서드
    private boolean isVideoFile(String fileName) {
        return fileName.toLowerCase().endsWith(".mp4");
    }
}
