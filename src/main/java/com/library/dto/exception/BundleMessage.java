package com.library.dto.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class BundleMessage {

    @JsonProperty(value="message_ar")
    private String messageAr;
    @JsonProperty(value="message_en")
    private String messgeEn;

    public BundleMessage(String messageAr, String messgeEn) {
        this.messageAr = messageAr;
        this.messgeEn = messgeEn;
    }
}
