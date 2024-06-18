package tech.codelabs.imageuploadingaws.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import tech.codelabs.imageuploadingaws.configuration.AmazonConfig;

@Service
@Slf4j
public class MetadataServiceImpl implements MetadataService {

    @Autowired
    private AmazonS3Service amazonS3Service;
    
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
        

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize())); 

        var key = UUID.randomUUID();
		String path = String.format("%s", bucketName);
        String fileName = String.format("%s", file.getOriginalFilename()).replace(" ", "_");
  
        // Uploading file to s3
        PutObjectResult putObjectResult = amazonS3Service.upload(
                path,fileName, Optional.of(metadata), file.getInputStream());
        
        
        
        //String pathNew = String.format("%s/%s", endpointUrl, fileName);
        
        amazonS3.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
        //var generatePresignedUrl = amazonS3.generatePresignedUrl(bucketName, key.toString(), null);
		//return amazonS3Service.getResourceUrl("my-videos-bucket-05", key);
        String pathNew = amazonS3.getUrl(bucketName, fileName).toString();
        //return generatePresignedUrl.toString();
        
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        System.out.println(extension);
        
        return pathNew;

    }

}