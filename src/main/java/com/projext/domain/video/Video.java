package com.projext.domain.video;

import com.projext.domain.upload.UploadFile;
import lombok.Data;

import java.util.List;

@Data
public class Video {
    private String title;
    private String Url;
    private Long uploadId;
    private List<Video> videoList;
    private String uploadName;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;
    private String videoFilePath;
}
