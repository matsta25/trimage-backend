package com.matsta25.trimagebackend.service;

import com.matsta25.trimagebackend.utlis.FileUtil;
import com.matsta25.trimagebackend.utlis.RenderUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class TrimageService {
    public String run(MultipartFile multipartFile) throws IOException {

        String fileName = FileUtil.getInputUuidFileName();
        FileUtil.saveFile(fileName, multipartFile);
        RenderUtil.render(fileName);

        return "ok";
    }

}
