package org.practice.dev.msglmelipracticetherevenge.dto.ipapi;

import lombok.Data;

@Data
public class IpapiDto {
    public String ip;
    public String type;
    public String continent_code;
    public String continent_name;
    public String country_code;
    public String country_name;
    public String region_code;
    public String region_name;
    public String city;
    public String zip;
    public double latitude;
    public double longitude;
    public LocationDto location;
}
