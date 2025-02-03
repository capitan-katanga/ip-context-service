package com.mercadolibre.ipcontext.service.impl;

import com.mercadolibre.ipcontext.client.FixerApiClient;
import com.mercadolibre.ipcontext.client.GeographyApiClient;
import com.mercadolibre.ipcontext.client.IpApiClient;
import com.mercadolibre.ipcontext.dto.geographyapi.GeographyApiDto;
import com.mercadolibre.ipcontext.dto.ipcontext.IpContextResponseDto;
import com.mercadolibre.ipcontext.exception.IpAddressIsBannedException;
import com.mercadolibre.ipcontext.mapper.MapperCountryDto;
import com.mercadolibre.ipcontext.service.IpBlacklistService;
import com.mercadolibre.ipcontext.service.IpContextService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class IpContextServiceImpl implements IpContextService {

    private final IpApiClient ipApiClient;
    private final GeographyApiClient geographyApiClient;
    private final FixerApiClient fixerApiClient;
    private final IpBlacklistService ipBlacklistService;
    private final MapperCountryDto mapperCountryDto;

    @Autowired
    public IpContextServiceImpl(IpApiClient ipApiClient, GeographyApiClient geographyApiClient, FixerApiClient fixerApiClient, IpBlacklistService ipBlacklistService, MapperCountryDto mapperCountryDto) {
        this.ipApiClient = ipApiClient;
        this.geographyApiClient = geographyApiClient;
        this.fixerApiClient = fixerApiClient;
        this.ipBlacklistService = ipBlacklistService;
        this.mapperCountryDto = mapperCountryDto;
    }

    public IpContextResponseDto getIpContext(String ipAddress) {
        if (ipBlacklistService.ipAddressIsBaned(ipAddress)) {
            throw new IpAddressIsBannedException("The ip address: " + ipAddress + " is banned.");
        }
        var responseIpApi = ipApiClient.getIpApi(ipAddress);
        var responseGeoApi = geographyApiClient.getGeographyInfo(responseIpApi.countryCode());
        var currencyCodes = responseGeoApi.currencies().stream()
                .map(GeographyApiDto.Currency::code)
                .toList();
        var responseFixApi = fixerApiClient.getFixer(currencyCodes, "USD");

        return mapperCountryDto.toCountryInformationDto(responseIpApi, responseGeoApi, responseFixApi);
    }

}
