package org.practice.dev.msglmelipracticetherevenge.client;

import lombok.Data;
import org.practice.dev.msglmelipracticetherevenge.dto.countrylayer.CountrylayerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Data
@ConfigurationProperties(prefix = "countrylayer")
@Component
public class CountrylayerClient {
    private String path;
    private String accessKey;

    private final RestTemplate restTemplate;

    public CountrylayerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private String getFullUrl() {
        return path + "%s/%s?access_key=" + accessKey;
    }

    public CountrylayerDto getCountryInformationByCountryCode(String alphaCode) {
        return restTemplate.getForObject(String.format(getFullUrl(), "alpha", alphaCode), CountrylayerDto.class);
    }
}
