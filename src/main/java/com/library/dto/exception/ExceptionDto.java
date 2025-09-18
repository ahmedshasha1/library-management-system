package com.library.dto.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class ExceptionDto {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "YYYY - MM - DD  hh:mm:ss")
    private LocalDateTime time;

    private BundleMessage bundleMessage;

    public ExceptionDto(HttpStatus status,BundleMessage bundleMessage){
        time = LocalDateTime.now();
        this.status = status;
        this.bundleMessage = bundleMessage;

    }
}
