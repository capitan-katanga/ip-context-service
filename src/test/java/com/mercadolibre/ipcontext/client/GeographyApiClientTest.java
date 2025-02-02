package com.mercadolibre.ipcontext.client;

import com.mercadolibre.ipcontext.dto.geographyapi.GeographyApiDto;
import com.mercadolibre.ipcontext.exception.ClientRequestErrorException;
import com.mercadolibre.ipcontext.util.Utils;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GeographyApiClientTest {

    private static MockWebServer mockBackEnd;
    public GeographyApiClient geographyApiClient;

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
                .setBody(Utils.convertToJson(mockGeoDto))
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
                .setBody(Utils.convertToJson(mockGeoDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var exception = assertThrows(ClientRequestErrorException.class, () ->
                geographyApiClient.getGeographyInfo("AR"));

        assertThat(exception.getMessage(), containsString("GeoAPI error: 500 Internal Server Error from GET"));
    }

    @Test
    void getGeographyTowElementsInBodyResponse() {
        var mockGeoDto = List.of(geographyApiDtoMock(), geographyApiDtoMock());
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(Utils.convertToJson(mockGeoDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var exception = assertThrows(ClientRequestErrorException.class, () ->
                geographyApiClient.getGeographyInfo("AR"));

        assertEquals("GeoAPI - Error in body response -> " + Utils.convertToJson(mockGeoDto),
                exception.getMessage());
    }


    private GeographyApiDto geographyApiDtoMock() {
        return GeographyApiDto.builder()
                .name("Argentina")
                .alpha2code("AR")
                .alpha3code("ARG")
                .capital("Buenos Aires")
                .altSpellings(List.of("AR", "Argentine Republic", "República Argentina"))
                .region("Americas")
                .subregion("South America")
                .population(43590400L)
                .latitude(-34.0)
                .longitude(-64.0)
                .demonym("Argentinean")
                .area(2780400.0)
                .gini(44.5)
                .timezones(List.of("UTC-03:00"))
                .borders(List.of("BOL", "BRA", "CHL", "PRY", "URY"))
                .nativeName("Argentina")
                .numericCode("032")
                .flag("http://assets.promptapi.com/flags/AR.svg")
                .topLevelDomains(List.of(".ar"))
                .callingCodes(List.of("54"))
                .currencies(List.of(GeographyApiDto.Currency.builder()
                        .name("Argentine peso")
                        .code("ARS")
                        .symbol("$")
                        .build()))
                .languages(List.of(GeographyApiDto.Language.builder()
                                .name("Spanish")
                                .nativeName("Español")
                                .iso6391("es")
                                .iso6392("spa")
                                .build(),
                        GeographyApiDto.Language.builder()
                                .name("Guaraní")
                                .nativeName("Avañe'ẽ")
                                .iso6391("gn")
                                .iso6392("grn")
                                .build()))
                .regionalBlocs(List.of(GeographyApiDto.RegionalBloc.builder()
                        .acronym("USAN")
                        .name("Union of South American Nations")
                        .build()))
                .build();

    }

}