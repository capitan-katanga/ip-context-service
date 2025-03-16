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

import static com.mercadolibre.ipcontext.util.DataMock.ipApiDtoMock;
import static com.mercadolibre.ipcontext.util.Utils.convertToJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class IpApiClientTest {

    private static MockWebServer mockBackEnd;
    private IpApiClient ipApiClient;

    @Value("${ip-api.scheme}")
    private String scheme;
    @Value("${ip-api.host}")
    private String host;
    @Value("${ip-api.path}")
    private String path;
    @Value("${ip-api.access-key}")
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
        ipApiClient = new IpApiClient(scheme, host, port, path,
                apikey,
                webClient);
    }

    @Test
    void getIpInfoSuccess() {
        var mockIpDto = ipApiDtoMock();
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(convertToJson(mockIpDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var response = ipApiClient.getIpApi("181.165.139.141");

        assertEquals(mockIpDto, response);
    }

    @Test
    void getIpInfoInternalServerError() {
        var mockIpDto = ipApiDtoMock();
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setBody(convertToJson(mockIpDto))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockBackEnd.enqueue(mockResponse);

        var exception = assertThrows(ClientApiErrorException.class, () ->
                ipApiClient.getIpApi("181.165.139.141"));

        assertThat(exception.getMessage(), containsString("IpAPI error -> 500 Internal Server Error from GET"));
    }

}