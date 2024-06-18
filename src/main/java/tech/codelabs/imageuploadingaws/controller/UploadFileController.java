package tech.codelabs.imageuploadingaws.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import tech.codelabs.imageuploadingaws.collection.Image;
import tech.codelabs.imageuploadingaws.service.FileUploadService;
import tech.codelabs.imageuploadingaws.service.ImageService;
import tech.codelabs.imageuploadingaws.service.MetadataService;

@RestController
@RequestMapping("/api/file")
@CrossOrigin("*")
public class UploadFileController {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${aws.s3.endpoint.url}")
    private String endpointUrl;
	
	@PostMapping
	public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String givenName, @RequestParam("tag") String tag ) throws IOException {
		
		String uniqueKey = fileUploadService.upload(file);
		String pathToImageNew = endpointUrl + "/" + uniqueKey;
		
		// your local date/time with no timezone information
		LocalDateTime localNow = LocalDateTime.now();
		// setting UTC as the timezone
//		ZonedDateTime zonedUTC = localNow.atZone(ZoneId.of("UTC"));
//		// converting to IST
//		ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
		
		Map<String, String> response = new HashMap<>();
		response.put("imageURL", pathToImageNew);
		response.put("fileName", uniqueKey);
		
		imageService.save(new Image(
				uniqueKey, 
				pathToImageNew, 
				file.getOriginalFilename(), 
				givenName, 
				file.getContentType(),
				file.getSize(),
				localNow,
				tag
				));
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/images")
	public List<Image> retreiveAllImages(){
		return imageService.getAllImages();
	}
	 
	@GetMapping("/{key}")
	public ResponseEntity<Image> retrieveSingleImage(@PathVariable String key){
		return ResponseEntity.ok(imageService.getImageByKey(key).get());
	}
	
	@DeleteMapping("/delete/{key}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String key) {
		fileUploadService.delete(key);
		imageService.deleteImage(key);
		return ResponseEntity.noContent().build();
	}
}