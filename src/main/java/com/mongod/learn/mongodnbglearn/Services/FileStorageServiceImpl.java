package com.mongod.learn.mongodnbglearn.Services;

import com.mongod.learn.mongodnbglearn.PropertiesClass;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path location;

    public FileStorageServiceImpl(PropertiesClass propertiesClass) {
        this.location = Paths.get(propertiesClass.getLocation())
                .toAbsolutePath()
                .normalize();
    }

    @Override
    @PostConstruct
    public void init() throws IOException {
//        try {
//            Files.createDirectories(this.location);
//        } catch (Exception e) {
//            throw new IOException("failed to create directory");
//        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            assert filename != null;
            Path destFile = this.location.resolve(filename);
            Files.copy(file.getInputStream(), destFile, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public Resource loadFile(String filename) {
        Resource resource;
        try{
            Path file = this.location.resolve(filename).normalize();
            resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new IOException("file not found");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] downloadFIle(String filename) throws IOException {
        Path fullPath = this.location.resolve(filename).normalize();
        return Files.readAllBytes(fullPath.toAbsolutePath());
    }

    @Override
    public File getFile(String filename) throws IOException {
        String fullPath = this.location.resolve(filename)
                .normalize()
                .toAbsolutePath()
                .toString();
        return new File(fullPath);
    }
}
