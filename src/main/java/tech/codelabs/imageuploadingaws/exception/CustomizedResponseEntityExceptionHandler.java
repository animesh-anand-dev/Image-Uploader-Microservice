package tech.codelabs.imageuploadingaws.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler {
	
	// This method handles all Exceptions and returns the Error Details
		@ExceptionHandler(Exception.class)
		public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
			ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
					ex.getMessage(), request.getDescription(false));
			
			return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
		}
}
