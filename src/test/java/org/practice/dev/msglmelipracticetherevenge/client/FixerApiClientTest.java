package org.practice.dev.msglmelipracticetherevenge.client;

import org.junit.jupiter.api.Test;
import org.practice.dev.msglmelipracticetherevenge.dto.fixerapi.FixerApiDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FixerApiClientTest {

    @Autowired
    FixerApiClient fixerApiClient;

    @Test
    void getFixerResponse() {
        ArrayList<String> symbols = new ArrayList<>(List.of("ARS", "BOB", "BRL"));
        FixerApiDto fixerApiDto = fixerApiClient.getExchangeRateBasedOnDollar(symbols);
        System.out.println(fixerApiDto);
    }

}