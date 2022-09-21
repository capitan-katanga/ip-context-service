package org.practice.dev.msglmelipracticetherevenge.service;

import org.junit.jupiter.api.Test;
import org.practice.dev.msglmelipracticetherevenge.dto.countryinformation.CountryInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CountryInformationServiceImplTest {

    @Autowired
    CountryInformationServiceImpl countryInformationService;

    @Test
    void getCountryInformation() {
        CountryInformationDto countryInformationDto = countryInformationService.getCountryInformation("102.215.147.255");
        System.out.println(countryInformationDto);
    }
}