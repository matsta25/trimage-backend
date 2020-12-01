package com.matsta25.trimagebackend.service;

import com.matsta25.trimagebackend.dto.JsonResponseDto;
import com.matsta25.trimagebackend.utlis.FileUtil;
import com.matsta25.trimagebackend.utlis.RenderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class TrimageService {

    public ResponseEntity<JsonResponseDto> uploadPhoto(MultipartFile multipartFile) throws IOException {

        String fileName = FileUtil.getInputUuidFileName();
        FileUtil.saveFile(fileName, multipartFile);

        HashMap<String, Object> map = new HashMap<>();
        map.put("filename", fileName);

        return new ResponseEntity<>(new JsonResponseDto(map), HttpStatus.OK);
    }

    public ResponseEntity<String> render(String fileName, String numberOfShapes, String mode) throws IOException {

        RenderUtil.render(fileName, numberOfShapes, mode);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
