package org.practice.dev.msglmelipracticetherevenge.dto.countryinformation;

import org.practice.dev.msglmelipracticetherevenge.dto.fixerapi.FixerApiDto;
import org.practice.dev.msglmelipracticetherevenge.dto.geographyapi.GeographyApiDto;
import org.practice.dev.msglmelipracticetherevenge.dto.ipapi.IpapiDto;
import org.springframework.stereotype.Component;

@Component
public class MapperCountryDto {
    public CountryInformationDto toCountryInformationDto(IpapiDto ipapiDto, GeographyApiDto geographyApiDto, FixerApiDto fixerApiDto) {
        return CountryInformationDto.builder()
                .ipAddress(ipapiDto.getIp())
                .countryName(ipapiDto.getCountry_name())
                .isoCode(geographyApiDto.getAlpha3code())
                .base(fixerApiDto.getBase())
                .localCurrencyAndRate(fixerApiDto.getRates()).build();
    }
}
