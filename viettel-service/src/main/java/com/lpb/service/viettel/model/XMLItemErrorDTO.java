package com.lpb.service.viettel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class XMLItemErrorDTO {
    @JsonProperty("ECODE")
    private String eCode;
    @JsonProperty("EDESC")
    private String eDesc;
    @JsonProperty("REFCODE")
    private String refCode;
    @JsonProperty("REFDESC")
    private String refDesc;

    private String errorMessage;

}
