package org.practice.dev.msglmelipracticetherevenge.dto.ipapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LanguageDto {
    public String code;
    public String name;
    @JsonProperty("native")
    public String mynative;
}
