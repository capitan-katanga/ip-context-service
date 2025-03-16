package com.mercadolibre.ipcontext.dto.ipcontext;

import lombok.Builder;

import java.util.Map;


@Builder
public record IpContextResponseDto(String ipAddress, String countryName, String isoCode, String base,
                                   Map<String, Double> localCurrencyAndRate) {

}
