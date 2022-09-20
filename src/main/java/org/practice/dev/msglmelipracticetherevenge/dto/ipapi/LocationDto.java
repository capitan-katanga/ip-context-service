package org.practice.dev.msglmelipracticetherevenge.dto.ipapi;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LocationDto {
    public int geoname_id;
    public String capital;
    public ArrayList<LanguageDto> languages;
    public String country_flag;
    public String country_flag_emoji;
    public String country_flag_emoji_unicode;
    public String calling_code;
    public boolean is_eu;
}
