package tech.codelabs.imageuploadingaws.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface MetadataService {
    public String upload(MultipartFile file) throws IOException;
}