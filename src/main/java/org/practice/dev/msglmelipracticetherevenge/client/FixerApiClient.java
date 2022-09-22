package org.practice.dev.msglmelipracticetherevenge.client;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.practice.dev.msglmelipracticetherevenge.dto.fixerapi.FixerApiDto;
import org.practice.dev.msglmelipracticetherevenge.exception.ClientRequestErrorException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@Log4j2
@Data
@ConfigurationProperties(prefix = "fixerapi")
@Component
public class FixerApiClient {
    private String host;
    private String apikey;

    private final RestTemplate restTemplate;

    public FixerApiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private HttpHeaders getHeaderWithApikey() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("apikey", apikey);
        return httpHeaders;
    }

    public FixerApiDto getExchangeRateBasedOnDollar(ArrayList<String> symbols) {
        String symbolsFormatted = String.join(",", symbols);
        log.info("List of currencies to request from fixapi: " + symbolsFormatted);
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(host).path("latest")
                .queryParam("symbols", symbolsFormatted)
                .queryParam("base", "USD").build();
        log.info("Uri fixapi: " + uriComponents.toUriString());
        HttpEntity<Void> httpEntity = new HttpEntity<>(getHeaderWithApikey());
        ResponseEntity<FixerApiDto> responseEntity =
                restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, FixerApiDto.class);
        log.info("Body response fixapi: " + responseEntity.getBody());
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Status code fixapi: " + responseEntity.getStatusCode());
            log.error("Body response fixapi: " + responseEntity.getBody());
            throw new ClientRequestErrorException("Response in Fixer endpoint is not OK \n " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }


}
