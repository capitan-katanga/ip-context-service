package org.practice.dev.msglmelipracticetherevenge.dto.ipapi;

import lombok.Data;

@Data
public class IpapiDto {
    private String ip;
    private String type;
    private String continent_code;
    private String continent_name;
    private String country_code;
    private String country_name;
    private String region_code;
    private String region_name;
    private String city;
    private String zip;
    private double latitude;
    private double longitude;
    private LocationDto location;
}
