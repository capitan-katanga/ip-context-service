package org.practice.dev.msglmelipracticetherevenge.client;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.practice.dev.msglmelipracticetherevenge.dto.geographyapi.GeographyApiDto;
import org.practice.dev.msglmelipracticetherevenge.exception.ClientRequestErrorException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Log4j2
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
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Status code GeographyApi: " + responseEntity.getStatusCode());
            log.error("Body response GeographyApi: " + Arrays.toString(responseEntity.getBody()));
            throw new ClientRequestErrorException("Response in Geo endpoint is not OK \n " + responseEntity.getStatusCode());
        }
        if (responseEntity.getBody() == null || responseEntity.getBody().length != 1) {
            log.error("Status code GeographyApi: " + responseEntity.getStatusCode());
            throw new ClientRequestErrorException("Error in response request: " + Arrays.toString(responseEntity.getBody()));
        }
        return responseEntity.getBody()[0];
    }


}
