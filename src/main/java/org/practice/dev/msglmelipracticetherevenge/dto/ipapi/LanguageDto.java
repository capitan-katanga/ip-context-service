package org.practice.dev.msglmelipracticetherevenge.dto.ipapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LanguageDto {
    private String code;
    private String name;
    @JsonProperty("native")
    private String mynative;
}
