package com.mercadolibre.ipcontext.client;

import com.mercadolibre.ipcontext.dto.geographyapi.GeographyApiDto;
import com.mercadolibre.ipcontext.exception.ClientApiErrorException;
import com.mercadolibre.ipcontext.util.Utils;
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
    private final Integer port;
    private final String path;
    private final String apikey;
    private final WebClient webClient;

    @Autowired
    public GeographyApiClient(@Value("${geography-api.scheme}") String schema,
                              @Value("${geography-api.host}") String host,
                              @Value("${geography-api.port}") Integer port,
                              @Value("${geography-api.path}") String path,
                              @Value("${geography-api.apikey}") String apikey,
                              WebClient webClient) {
        this.schema = schema;
        this.host = host;
        this.port = port;
        this.path = path;
        this.apikey = apikey;
        this.webClient = webClient;
    }

    public GeographyApiDto getGeographyInfo(String countryCode) {
        log.info("GeographyApi service call begins.");
        log.info("Country code for get information -> {}", countryCode);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(schema)
                        .host(host)
                        .port(port)
                        .path(path)
                        .build(countryCode)
                )
                .header("apikey", apikey)
                .retrieve()
                .bodyToMono(GeographyApiDto[].class)
                .doOnSuccess(response -> {
                    if (response.length != 1) {
                        log.error("GeographyAPI status code is OK, but body response has an error.");
                        throw new ClientApiErrorException("GeoAPI - Error in body response -> " + Utils.convertToJson(response));
                    } else {
                        log.info("GeographyAPI OK body response -> {}", Utils.convertToJson(response));
                    }
                })
                .onErrorMap(error -> !(error instanceof ClientApiErrorException),
                        error -> new ClientApiErrorException("GeoAPI error -> " + error.getMessage()))
                .map(response -> response[0])
                .block();
    }

}
