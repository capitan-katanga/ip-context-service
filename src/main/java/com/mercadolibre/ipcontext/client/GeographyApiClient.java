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
        return webClient.get()
                .uri(uriBuilder -> {
                    var uri = uriBuilder
                            .scheme(schema)
                            .host(host)
                            .path(path)
                            .build(countryCode);
                    log.info("GeoApi -> UriBuilder: {}", uri);
                    return uri;
                })
                .header("apikey", apikey)
                .retrieve()
                .bodyToMono(GeographyApiDto.class)
                .doOnSuccess(
                        response -> log.info("GeoApi body response: {}", response.toString()))
                .onErrorMap(
                        exception -> new ClientRequestErrorException("GeoApi error: " + exception.getMessage()))
                .block();
    }

}
