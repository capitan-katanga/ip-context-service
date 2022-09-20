package org.practice.dev.msglmelipracticetherevenge.client;

import lombok.Data;
import org.practice.dev.msglmelipracticetherevenge.dto.ipapi.IpapiDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Data
@ConfigurationProperties(prefix = "ipapi")
@Component
public class IpapiClient {
    private String path;
    private String accessKey;
    private final RestTemplate restTemplate;

    public IpapiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private String getFullUrl() {
        return path + "%s?access_key=" + accessKey;
    }

    public IpapiDto getIpInformationByIpAddress(String ipAddress) {
        return restTemplate.getForObject(String.format(getFullUrl(), ipAddress), IpapiDto.class);
    }
}
