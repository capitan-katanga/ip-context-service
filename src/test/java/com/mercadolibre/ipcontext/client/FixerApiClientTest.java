package com.mercadolibre.ipcontext.client;


import com.mercadolibre.ipcontext.config.WebClientConfig;
import com.mercadolibre.ipcontext.dto.fixerapi.FixerApiDto;
import com.mercadolibre.ipcontext.util.Utils;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class FixerApiClientTest {

    private static MockWebServer mockBackEnd;
    public FixerApiClient fixerApiClient;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        var scheme = "http";
        var host = "localhost";
        var path = "fixer/latest";
        var apikey = "yZbci6tDYm2Huog33iR7icBM0Z5zXAWF";
        var port = mockBackEnd.getPort();
        WebClient webClient;
        try {
            webClient = new WebClientConfig(1000, 1000).webClient();
            fixerApiClient = new FixerApiClient(scheme, host, port, path, apikey, webClient);
        } catch (SSLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getFixerWithOneRate() {
        var mockFixerDto = FixerApiDto.builder()
                .success(true)
                .timestamp(1738355356L)
                .base("USD")
                .date(LocalDate.now())
                .rates(Map.of("ARS", 1000D))
                .build();
        var mockResponse = new MockResponse().setResponseCode(HttpStatus.OK.value())
                .setBody(Utils.convertToJson(mockFixerDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var response = fixerApiClient.getFixer(List.of("ARS"), "USD");
        assertEquals(mockFixerDto, response);
    }

}