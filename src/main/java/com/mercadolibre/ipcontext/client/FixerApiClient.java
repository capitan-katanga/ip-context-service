package com.mercadolibre.ipcontext.client;

import com.mercadolibre.ipcontext.dto.fixerapi.FixerApiDto;
import com.mercadolibre.ipcontext.exception.ClientApiErrorException;
import com.mercadolibre.ipcontext.util.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Log4j2
@Component
public class FixerApiClient {

    private final String scheme;
    private final String host;
    private final Integer port;
    private final String path;
    private final String apikey;
    private final WebClient webClient;

    @Autowired
    public FixerApiClient(@Value("${fixer-api.scheme}") String scheme,
                          @Value("${fixer-api.host}") String host,
                          @Value("${fixer-api.port}") Integer port,
                          @Value("${fixer-api.path}") String path,
                          @Value("${fixer-api.apikey}") String apikey,
                          WebClient webClient) {
        this.scheme = scheme;
        this.host = host;
        this.path = path;
        this.apikey = apikey;
        this.port = port;
        this.webClient = webClient;
    }

    public FixerApiDto getFixer(List<String> symbols, String base) {
        log.info("FixerAPI service call begins.");
        log.info("Base currency -> {}", base);
        var symbolsFormatted = String.join(",", symbols);
        log.info("Symbols to get rates -> {}", symbolsFormatted);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(scheme)
                        .host(host)
                        .port(port)
                        .path(path)
                        .queryParam("symbols", symbolsFormatted)
                        .queryParam("base", base)
                        .build())
                .header("apikey", apikey)
                .retrieve()
                .bodyToMono(FixerApiDto.class)
                .doOnSuccess(
                        response -> {
                            if (!response.success()) {
                                log.error("FixerAPI status code is OK, there was an error.");
                                throw new ClientApiErrorException("FixerAPI error - Response no success -> " + Utils.convertToJson(response));
                            } else {
                                log.info("FixerAPI OK body response -> {}", Utils.convertToJson(response));
                            }
                        })
                .onErrorMap(error -> !(error instanceof ClientApiErrorException),
                        error -> new ClientApiErrorException("FixerAPI error -> " + error.getMessage()))
                .block();
    }

}
