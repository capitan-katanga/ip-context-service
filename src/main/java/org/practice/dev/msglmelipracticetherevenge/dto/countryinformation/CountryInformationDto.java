package org.practice.dev.msglmelipracticetherevenge.dto.countryinformation;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class CountryInformationDto {
    private String ipAddress;
    private String countryName;
    private String isoCode;
    private String base;
    private HashMap<String, Double> localCurrencyAndRate;
}
