package org.practice.dev.msglmelipracticetherevenge.service;

import org.practice.dev.msglmelipracticetherevenge.dto.countryinformation.CountryInformationDto;

public interface CountryInformationService {

    CountryInformationDto getCountryInformation(String ipAddress);

}
