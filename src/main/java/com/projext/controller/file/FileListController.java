package com.projext.controller.file;

import com.projext.DirectoryPath;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileListController {
    private static final int PAGE_SIZE = 10; // 한 페이지에 표시할 항목 수
    private static final String DIRECTORY_PATH = DirectoryPath.DIRECTORY_PATH;

    @GetMapping("/filelist")
    public String fileList(Model model) {
        File directory = new File(DIRECTORY_PATH);
        File[] files = directory.listFiles();
        List<String> filelList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    filelList.add(file.getName());
                }
            }
        }
        model.addAttribute("fileList", filelList);
        return "/Upload/fileList";
    }
    @GetMapping("/index")
    public String goHome(){
        return "index";
    }
}
