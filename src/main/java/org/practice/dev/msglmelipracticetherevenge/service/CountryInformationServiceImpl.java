package org.practice.dev.msglmelipracticetherevenge.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.practice.dev.msglmelipracticetherevenge.client.FixerApiClient;
import org.practice.dev.msglmelipracticetherevenge.client.GeographyApiClient;
import org.practice.dev.msglmelipracticetherevenge.client.IpapiClient;
import org.practice.dev.msglmelipracticetherevenge.dto.countryinformation.CountryInformationDto;
import org.practice.dev.msglmelipracticetherevenge.dto.countryinformation.MapperCountryDto;
import org.practice.dev.msglmelipracticetherevenge.dto.fixerapi.FixerApiDto;
import org.practice.dev.msglmelipracticetherevenge.dto.geographyapi.GeographyApiDto;
import org.practice.dev.msglmelipracticetherevenge.dto.ipapi.IpapiDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Log4j2
@Service
@AllArgsConstructor
public class CountryInformationServiceImpl implements CountryInformationService {

    private final IpapiClient ipapiClient;
    private final GeographyApiClient geographyApiClient;
    private final FixerApiClient fixerApiClient;
    private MapperCountryDto mapperCountryDto;

    public CountryInformationDto getCountryInformation(String ipAddress) {
        IpapiDto ipapiDto = ipapiClient.getIpInformationByIpAddress(ipAddress);
        log.debug("Dto Ipapi: " + ipapiDto);
        GeographyApiDto geographyApiDto = geographyApiClient.getCountryInformationByCountryCode(ipapiDto.getCountry_code());
        log.debug("Dto Geography: " + geographyApiDto);
        FixerApiDto fixerApiDto = fixerApiClient.getExchangeRateBasedOnDollar((ArrayList<String>) geographyApiDto.getListCurrenciesCode());
        log.debug("Dto Fixerpi: " + fixerApiDto);
        return mapperCountryDto.toCountryInformationDto(ipapiDto, geographyApiDto, fixerApiDto);
    }

}
