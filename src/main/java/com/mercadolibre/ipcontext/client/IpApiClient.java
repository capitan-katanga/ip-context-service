package com.mercadolibre.ipcontext.client;

import com.mercadolibre.ipcontext.dto.ipapi.IpApiDto;
import com.mercadolibre.ipcontext.exception.ClientRequestErrorException;
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
    private final String path;
    private final String accessKey;
    private final WebClient webClient;

    @Autowired
    public IpApiClient(@Value("${ip-api.scheme}") String scheme,
                       @Value("${ip-api.host}") String host,
                       @Value("${ip-api.path}") String path,
                       @Value("${ip-api.access-key}") String accessKey,
                       WebClient webClient) {
        this.scheme = scheme;
        this.host = host;
        this.path = path;
        this.accessKey = accessKey;
        this.webClient = webClient;
    }

    public IpApiDto getIpApi(String ipAddress) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var uri = uriBuilder
                            .scheme(scheme)
                            .host(host)
                            .path(path)
                            .queryParam("access_key", accessKey)
                            .build(ipAddress);
                    log.info("IPAPI -> UriBuilder: {}", uri);
                    return uri;
                })
                .retrieve()
                .bodyToMono(IpApiDto.class)
                .doOnSuccess(
                        response -> log.info("IpApi body response -> {}", response))
                .onErrorMap(
                        exception -> new ClientRequestErrorException("IpApi error: " + exception.getMessage()))
                .block();
    }

}
