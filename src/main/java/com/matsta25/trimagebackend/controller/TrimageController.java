package com.matsta25.trimagebackend.controller;


import com.matsta25.trimagebackend.service.TrimageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/trimage")
public class TrimageController {

    final TrimageService trimageService;

    public TrimageController(TrimageService trimageService) {
        this.trimageService = trimageService;
    }

    @PostMapping()
    public String run(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return trimageService.run(multipartFile);
    }
}
