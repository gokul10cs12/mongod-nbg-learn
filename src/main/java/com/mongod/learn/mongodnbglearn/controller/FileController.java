package com.mongod.learn.mongodnbglearn.controller;

import com.mongod.learn.mongodnbglearn.Services.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
      Resource resource = fileStorageService.loadFile(filename);

      return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" +
              resource.getFilename() + "\"").body(resource);
    }

    @GetMapping("/byte-download/{filename}")
    public ResponseEntity<byte[]> downloadFileByte(@PathVariable String filename) throws IOException {
        byte[] file = fileStorageService.downloadFIle(filename);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @GetMapping("/downalods/{filename}")
    public void downladFile (HttpServletRequest request, HttpServletResponse response, @PathVariable String filename) throws IOException {
        File file = fileStorageService.getFile(filename);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +filename);
        response.setHeader("Content-Length", Long.toString(file.length()));
        Files.copy(Path.of(file.getAbsolutePath()),response.getOutputStream());

    }
}
