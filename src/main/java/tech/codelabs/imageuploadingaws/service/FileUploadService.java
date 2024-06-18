package tech.codelabs.imageuploadingaws.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	
	public String upload(MultipartFile file) throws IOException;
	
	public void delete(String uniqueKey);

}
