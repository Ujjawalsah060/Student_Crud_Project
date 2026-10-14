package udSah.techGroup.DTO_Operation.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import udSah.techGroup.DTO_Operation.dto.ExceptionResponseDto;
import udSah.techGroup.DTO_Operation.dto.ValidationExceptionResponseDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionalHandler {


    // run time exception

    @ExceptionHandler(RuntimeException.class)

    public ResponseEntity<ExceptionResponseDto> handleRunTimeException(RuntimeException ex,
                                                                       HttpServletRequest request){

        ExceptionResponseDto responseDto = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );


        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseDto);
    }
// generic exception

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleGenericException(Exception ex,
                                                                       HttpServletRequest request){

        ExceptionResponseDto responseDto= new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()

        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseDto);

    }

// handle resourceNotFoundException

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                  HttpServletRequest request) {

        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );



        return ResponseEntity

                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);


    }

    // handleDuplicateResourceException

    @ExceptionHandler(DuplicateResourceException.class)

    public ResponseEntity<ExceptionResponseDto> handleDuplicateResourceException(DuplicateResourceException ex,
                                                                                 HttpServletRequest request){
        ExceptionResponseDto responseDto = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
                );


        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(responseDto);
    }

// validation exception

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponseDto> handleDMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(error.getField(), error.getDefaultMessage()));

        ValidationExceptionResponseDto exceptionResponse = new ValidationExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation Failed",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }




}
