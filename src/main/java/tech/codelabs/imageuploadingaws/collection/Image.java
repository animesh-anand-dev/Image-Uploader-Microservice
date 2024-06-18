package tech.codelabs.imageuploadingaws.collection;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

@Document(collection="image_data")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Image {
	
	@Id
	private String uniqueId;
	private String publicURL;
	private String originalName;
	private String givenName;
	private String contentType;
	private Long size;
	private LocalDateTime uploadDate;
	private String tag;
	
	public Image() {
		super();
	}
	

	public Image(String uniqueId, String publicURL, String originalName, String givenName, String contentType,
			Long size, LocalDateTime uploadDate, String tag) {
		super();
		this.uniqueId = uniqueId;
		this.publicURL = publicURL;
		this.originalName = originalName;
		this.givenName = givenName;
		this.contentType = contentType;
		this.size = size;
		this.uploadDate = uploadDate;
		this.tag = tag;
	}



	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getPublicURL() {
		return publicURL;
	}

	public void setPublicURL(String publicURL) {
		this.publicURL = publicURL;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


	@Override
	public String toString() {
		return "Image [uniqueId=" + uniqueId + ", publicURL=" + publicURL + ", originalName=" + originalName
				+ ", givenName=" + givenName + ", contentType=" + contentType + ", size=" + size + ", uploadDate="
				+ uploadDate + ", tag=" + tag + "]";
	}
	
	
}
