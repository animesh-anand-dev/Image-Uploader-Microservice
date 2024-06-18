package tech.codelabs.imageuploadingaws.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import tech.codelabs.imageuploadingaws.collection.Image;
import tech.codelabs.imageuploadingaws.repository.ImageRepository;

@Service
public class ImageService {
	
	@Autowired
	private ImageRepository imageRepository;
	
	public Image save(Image image) {
		return imageRepository.save(image);
	}
	
	public List<Image> getAllImages(){
		return imageRepository.findAll();
	}
	
	public void deleteImage(String id) {
		imageRepository.deleteById(id);
	}
	
	public Optional<Image> getImageByKey(String id) {
		return imageRepository.findById(id);
	}
}
