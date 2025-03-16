package com.mercadolibre.ipcontext.client;

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
import java.util.List;

import static com.mercadolibre.ipcontext.util.DataMock.geographyApiDtoMock;
import static com.mercadolibre.ipcontext.util.Utils.convertToJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GeographyApiClientTest {

    private static MockWebServer mockBackEnd;
    private GeographyApiClient geographyApiClient;

    @Value("${geography-api.scheme}")
    private String scheme;
    @Value("${geography-api.host}")
    private String host;
    @Value("${geography-api.path}")
    private String path;
    @Value("${geography-api.apikey}")
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
        geographyApiClient = new GeographyApiClient(scheme, host, port, path,
                apikey,
                webClient);
    }

    @Test
    void getGeographyInfoSuccess() {
        var mockGeoDto = List.of(geographyApiDtoMock());
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(convertToJson(mockGeoDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var response = geographyApiClient.getGeographyInfo("AR");

        assertEquals(mockGeoDto.getFirst(), response);
    }

    @Test
    void getGeographyInternalServerError() {
        var mockGeoDto = List.of(geographyApiDtoMock());
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setBody(convertToJson(mockGeoDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var exception = assertThrows(ClientApiErrorException.class, () ->
                geographyApiClient.getGeographyInfo("AR"));

        assertThat(exception.getMessage(), containsString("GeoAPI error -> 500 Internal Server Error from GET"));
    }

    @Test
    void getGeographyTowElementsInBodyResponse() {
        var mockGeoDto = List.of(geographyApiDtoMock(), geographyApiDtoMock());
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(convertToJson(mockGeoDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var exception = assertThrows(ClientApiErrorException.class, () ->
                geographyApiClient.getGeographyInfo("AR"));

        assertEquals("GeoAPI - Error in body response -> " + convertToJson(mockGeoDto),
                exception.getMessage());
    }

}