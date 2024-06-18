package tech.codelabs.imageuploadingaws.service;

import java.io.IOException;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {
	
	@Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    
    @Value("${aws.s3.endpoint.url}")
    private String endpointUrl;

	@Override
	public String upload(MultipartFile file) throws IOException {
		
		if (file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file");
		
		String path = String.format("%s", bucketName);
		String key = UUID.randomUUID().toString();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String fileName = key + "." + extension;
		
		ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
		
		amazonS3.putObject(path, fileName, file.getInputStream(), metadata);
		amazonS3.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
		
		
		String pathToImage = amazonS3.getUrl(bucketName, fileName).toString();
//		String pathToImageNew = endpointUrl + "/" + fileName;
		
		return fileName;
	}

	@Override
	public void delete(String uniqueKey) {
		amazonS3.deleteObject(bucketName, uniqueKey);
	}

}
