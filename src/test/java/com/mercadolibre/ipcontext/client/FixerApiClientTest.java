package com.mercadolibre.ipcontext.client;


import com.mercadolibre.ipcontext.dto.fixerapi.FixerApiDto;
import com.mercadolibre.ipcontext.exception.ClientApiErrorException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.mercadolibre.ipcontext.util.Utils.convertToJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class FixerApiClientTest {

    private static MockWebServer mockBackEnd;
    private FixerApiClient fixerApiClient;

    @Value("${fixer-api.scheme}")
    private String scheme;
    @Value("${fixer-api.host}")
    private String host;
    @Value("${fixer-api.path}")
    private String path;
    @Value("${fixer-api.apikey}")
    private String apikey;
    @Autowired
    private WebClient webClient;


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
        var port = mockBackEnd.getPort();
        fixerApiClient = new FixerApiClient(scheme, host, port, path,
                apikey,
                webClient);
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
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(convertToJson(mockFixerDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var response = fixerApiClient.getFixer(List.of("ARS"), "USD");
        assertEquals(mockFixerDto, response);
    }

    @Test
    void getFixerWithTwoRate() {
        var mockFixerDto = FixerApiDto.builder()
                .success(true)
                .timestamp(1738355356L)
                .base("USD")
                .date(LocalDate.now())
                .rates(Map.of("ARS", 1000D,
                        "JPY", 155.23D))
                .build();
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(convertToJson(mockFixerDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var response = fixerApiClient.getFixer(List.of("ARS", "JPY"), "USD");
        assertEquals(mockFixerDto, response);
    }

    @Test
    void getFixerNotSuccess() {
        var mockFixerDto = FixerApiDto.builder()
                .success(false)
                .timestamp(1738355356L)
                .base("USD")
                .date(LocalDate.now())
                .rates(Map.of("ARS", 1000D))
                .build();

        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(convertToJson(mockFixerDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var exception = assertThrows(ClientApiErrorException.class, () ->
                fixerApiClient.getFixer(List.of("ARS"), "USD")
        );

        assertEquals("FixerAPI error - Response no success -> " + convertToJson(mockFixerDto),
                exception.getMessage());
    }

    @Test
    void getFixerNotSuccessRuntimeException() {
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var exception = assertThrows(RuntimeException.class, () ->
                fixerApiClient.getFixer(List.of("ARS"), "USD")
        );

        assertThat(exception.getMessage(), containsString("FixerAPI error -> 500 Internal Server Error from GET"));
    }

}