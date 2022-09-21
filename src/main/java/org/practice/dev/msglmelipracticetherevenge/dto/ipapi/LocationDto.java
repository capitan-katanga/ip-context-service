package org.practice.dev.msglmelipracticetherevenge.dto.ipapi;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LocationDto {
    private int geoname_id;
    private String capital;
    private ArrayList<LanguageDto> languages;
    private String country_flag;
    private String country_flag_emoji;
    private String country_flag_emoji_unicode;
    private String calling_code;
    private boolean is_eu;
}
