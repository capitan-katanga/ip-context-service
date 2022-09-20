package org.practice.dev.msglmelipracticetherevenge.client;

import lombok.Data;
import org.practice.dev.msglmelipracticetherevenge.dto.fixerapi.FixerApiDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@Data
@ConfigurationProperties(prefix = "fixerapi")
@Component
public class FixerApiClient {
    private String path;
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
        String symbolsFormatted = symbols.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "");
        UriComponents uriParameters = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(path)
                .path("latest")
                .queryParam("symbols", symbolsFormatted)
                .queryParam("base", "USD").build();
        HttpEntity<Void> httpEntity = new HttpEntity<>(getHeaderWithApikey());
        ResponseEntity<FixerApiDto> responseEntity = restTemplate.exchange(uriParameters.toUriString(), HttpMethod.GET, httpEntity, FixerApiDto.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new RuntimeException("Response in Fixer endpoint is not OK \n " + responseEntity.getStatusCode());

        return responseEntity.getBody();
    }


}
