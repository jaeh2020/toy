package com.projext.domain.upload;

import lombok.Data;

import java.util.List;

@Data
public class Upload {
    private Long uploadId;
    private String uploadName;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;
}
