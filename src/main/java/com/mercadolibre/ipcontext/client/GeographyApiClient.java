package com.mercadolibre.ipcontext.client;

import com.mercadolibre.ipcontext.dto.geographyapi.GeographyApiDto;
import com.mercadolibre.ipcontext.exception.ClientRequestErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Component
public class GeographyApiClient {

    private final String schema;
    private final String host;
    private final String path;
    private final String apikey;
    private final WebClient webClient;

    @Autowired
    public GeographyApiClient(@Value("${geography-api.scheme}") String schema,
                              @Value("${geography-api.host}") String host,
                              @Value("${geography-api.path}") String path,
                              @Value("${geography-api.apikey}") String apikey,
                              WebClient webClient) {
        this.schema = schema;
        this.host = host;
        this.path = path;
        this.apikey = apikey;
        this.webClient = webClient;
    }

    public GeographyApiDto getGeographyInfo(String countryCode) {
        var response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(schema)
                        .host(host)
                        .path(path)
                        .build(countryCode)
                )
                .header("apikey", apikey)
                .retrieve()
                .bodyToMono(GeographyApiDto[].class)
                .onErrorMap(
                        exception -> new ClientRequestErrorException("GeoAPI error: " + exception.getMessage())
                )
                .block();

        if (response != null && response.length != 1) {
            if (response[0] != null) {
                return response[0];
            }
        }
        throw new ClientRequestErrorException("GeoAPI - Error in body response -> ");
    }

}
