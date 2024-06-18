package tech.codelabs.imageuploadingaws.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import tech.codelabs.imageuploadingaws.collection.Image;

public interface ImageRepository extends MongoRepository<Image, String> {

}
