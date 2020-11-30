package com.matsta25.trimagebackend.controller;


import com.matsta25.trimagebackend.dto.JsonResponseDto;
import com.matsta25.trimagebackend.service.TrimageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;

@CrossOrigin
@RestController
@RequestMapping("/api/trimage")
public class TrimageController {

    final TrimageService trimageService;
    public SimpMessagingTemplate template;

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
}
