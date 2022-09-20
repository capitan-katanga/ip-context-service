package org.practice.dev.msglmelipracticetherevenge.client;

import lombok.Data;
import org.practice.dev.msglmelipracticetherevenge.dto.geographyapi.GeographyApiDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Data
@ConfigurationProperties(prefix = "geographyapi")
@Component
public class GeographyApiClient {
    private String path;
    private String apikey;

    private final RestTemplate restTemplate;

    public GeographyApiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private HttpHeaders getHeaderWithApikey() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("apikey", apikey);
        return httpHeaders;
    }

    public GeographyApiDto getCountryInformationByCountryCode(String code) {
        String url = path + "country/code/" + code;
        HttpEntity<Void> httpEntity = new HttpEntity<>(getHeaderWithApikey());
        ResponseEntity<GeographyApiDto[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, GeographyApiDto[].class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new RuntimeException("Response in Geo endpoint is not OK \n " + responseEntity.getStatusCode());
        if (responseEntity.getBody() == null || responseEntity.getBody().length != 1)
            throw new RuntimeException("Error in response request: " + Arrays.toString(responseEntity.getBody()));
        return responseEntity.getBody()[0];
    }


}
