package com.projext.domain.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadForm {
    private Long uploadId;
    private String uploadName;
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;

    private MultipartFile videoFile;
}
