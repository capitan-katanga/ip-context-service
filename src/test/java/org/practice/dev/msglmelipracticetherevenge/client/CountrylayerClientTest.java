package org.practice.dev.msglmelipracticetherevenge.client;

import org.junit.jupiter.api.Test;
import org.practice.dev.msglmelipracticetherevenge.dto.countrylayer.CountrylayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CountrylayerClientTest {

    @Autowired
    CountrylayerClient countrylayerClient;

    @Test
    void getInfoCountryByAlphaCodeTest() {
        CountrylayerDto countrylayerDto = countrylayerClient.getCountryInformationByCountryCode("AR");
        System.out.println(countrylayerDto.toString());
    }
}