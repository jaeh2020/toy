package com.projext.controller.upload;

import com.projext.DirectoryPath;
import com.projext.domain.file.FileStore;
import com.projext.domain.upload.Upload;
import com.projext.domain.upload.UploadFile;
import com.projext.domain.upload.UploadForm;
import com.projext.web.upload.UploadRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UploadController {
    private static final String DIRECTORY_PATH = DirectoryPath.DIRECTORY_PATH;
    private final UploadRepository uploadRepository;
    private final FileStore fileStore;

    @GetMapping("/Upload/blank")
    public String newUpload(@ModelAttribute UploadForm form, Model model) {
        model.addAttribute("uploadForm", form);
        return "/Upload/blank";
    }

    // blank 에서 submit정보가 전달되는 곳
    @PostMapping("/Upload/blank")
    public String saveUpload(@ModelAttribute UploadForm form, RedirectAttributes redirectAttributes) throws IOException {
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        Upload upload = new Upload();
        upload.setUploadName(form.getUploadName());
        upload.setAttachFile(attachFile);
        upload.setImageFiles(storeImageFiles);
        uploadRepository.save(upload);

        redirectAttributes.addAttribute("uploadId", upload.getUploadId());
        log.info("redirect:/upload/{uploadId}");
        return "redirect:/upload/{uploadId}";
    }

    @GetMapping("/upload/{uploadId}")
    public String upload(@PathVariable Long uploadId, Model model) {
        Upload upload = uploadRepository.findById(uploadId);
        model.addAttribute("upload", upload);
        log.info("upload={}", upload);
        log.info("upload GetMapping");
        return "/Upload/view";
    }

    // 이미지 다운로드
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource(("file:" + fileStore.getFullPath(filename)));
    }

    // 다운로드
    @GetMapping("/attach/{uploadId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long uploadId) throws MalformedURLException {
        Upload upload = uploadRepository.findById(uploadId);
        String storeFileName = upload.getAttachFile().getStoreFileName();
        String uploadFileName = upload.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encode = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachmentl filename=\"" + encode + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    // 리스트에서 파일 다운로드하기
    @GetMapping("/download/{fileName}")
    public void downloadFile(@RequestParam(defaultValue = "1") int page,
                             @PathVariable String fileName,
                             HttpServletResponse response) throws IOException {
        // 다운로드할 파일의 경로
        String filePath = DIRECTORY_PATH + fileName;

        // 파일이름으로 다운로드할 파일을 읽어옵니다.
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);

        // 파일 다운로드 설정
        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // 파일을 클라이언트에게 전송합니다.
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
    }
}
