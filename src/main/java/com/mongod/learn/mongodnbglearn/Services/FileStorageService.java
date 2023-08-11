package com.mongod.learn.mongodnbglearn.Services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileStorageService {
    void init() throws IOException;
    String saveFile(MultipartFile file);
    Resource loadFile(String filename);

    byte[] downloadFIle(String filename) throws IOException;

    File getFile(String filename) throws IOException;
}
