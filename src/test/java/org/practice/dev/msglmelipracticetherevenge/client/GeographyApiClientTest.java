package org.practice.dev.msglmelipracticetherevenge.client;

import org.junit.jupiter.api.Test;
import org.practice.dev.msglmelipracticetherevenge.dto.geographyapi.GeographyApiDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeographyApiClientTest {

    @Autowired
    GeographyApiClient geographyApiClient;

    @Test
    void getGeoCountryByCode() {
        GeographyApiDto geographyApiDto = geographyApiClient.getCountryInformationByCountryCode("AR");
        System.out.println(geographyApiDto);
    }

}