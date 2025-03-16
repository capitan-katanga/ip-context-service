package com.mercadolibre.ipcontext.client;

import com.mercadolibre.ipcontext.dto.ipapi.IpApiDto;
import com.mercadolibre.ipcontext.exception.ClientApiErrorException;
import com.mercadolibre.ipcontext.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@Component
public class IpApiClient {

    private final String scheme;
    private final String host;
    private final Integer port;
    private final String path;
    private final String accessKey;
    private final WebClient webClient;

    @Autowired
    public IpApiClient(@Value("${ip-api.scheme}") String scheme,
                       @Value("${ip-api.host}") String host,
                       @Value("${ip-api.port}") Integer port,
                       @Value("${ip-api.path}") String path,
                       @Value("${ip-api.access-key}") String accessKey,
                       WebClient webClient) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.path = path;
        this.accessKey = accessKey;
        this.webClient = webClient;
    }

    public IpApiDto getIpApi(String ipAddress) {
        log.info("IpAPI service call begins.");
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(scheme)
                        .host(host)
                        .port(port)
                        .path(path)
                        .queryParam("access_key", accessKey)
                        .build(ipAddress))
                .retrieve()
                .bodyToMono(IpApiDto.class)
                .doOnSuccess(
                        response -> log.info("IpAPI OK body response -> {}", Utils.convertToJson(response))
                )
                .onErrorMap(
                        exception -> new ClientApiErrorException("IpAPI error -> " + exception.getMessage())
                )
                .block();
    }

}
