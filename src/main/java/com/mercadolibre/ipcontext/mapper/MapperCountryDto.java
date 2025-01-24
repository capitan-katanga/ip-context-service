package com.mercadolibre.ipcontext.mapper;

import com.mercadolibre.ipcontext.dto.fixerapi.FixerApiDto;
import com.mercadolibre.ipcontext.dto.geographyapi.GeographyApiDto;
import com.mercadolibre.ipcontext.dto.ipapi.IpApiDto;
import com.mercadolibre.ipcontext.dto.ipcontext.IpContextResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MapperCountryDto {
    public IpContextResponseDto toCountryInformationDto(IpApiDto ipApiDto, GeographyApiDto geographyApiDto, FixerApiDto fixerApiDto) {
        return IpContextResponseDto.builder()
                .ipAddress(ipApiDto.ip())
                .countryName(ipApiDto.countryName())
                .isoCode(geographyApiDto.alpha3code())
                .base(fixerApiDto.base())
                .localCurrencyAndRate(fixerApiDto.rates())
                .build();
    }
}
