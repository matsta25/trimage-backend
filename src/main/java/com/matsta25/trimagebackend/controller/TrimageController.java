package com.matsta25.trimagebackend.controller;


import com.matsta25.trimagebackend.service.TrimageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/trimage")
public class TrimageController {

    final TrimageService trimageService;

    public TrimageController(TrimageService trimageService) {
        this.trimageService = trimageService;
    }

    @PostMapping()
    public ResponseEntity<String> run(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return trimageService.run(multipartFile);
    }
}
