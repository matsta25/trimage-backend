package com.matsta25.trimagebackend.controller;


import com.matsta25.trimagebackend.dto.JsonResponseDto;
import com.matsta25.trimagebackend.exception.ImageNotFoundException;
import com.matsta25.trimagebackend.service.TrimageService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/trimage")
public class TrimageController {

    final TrimageService trimageService;
    public SimpMessagingTemplate template;

    private String FILE_PATH_ROOT = "static/photos/";

    public TrimageController(TrimageService trimageService, SimpMessagingTemplate template) {
        this.trimageService = trimageService;
        this.template = template;
    }

    @PostMapping("/upload-photo")
    public ResponseEntity<JsonResponseDto> uploadPhoto(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return trimageService.uploadPhoto(multipartFile);
    }

    @PostMapping("/render")
    public ResponseEntity<String> render(@RequestBody() String fileName) throws IOException {
        return trimageService.render(fileName);
    }

    @GetMapping("/photos/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(FILE_PATH_ROOT+filename));
        } catch (IOException e) {
            throw new ImageNotFoundException();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
