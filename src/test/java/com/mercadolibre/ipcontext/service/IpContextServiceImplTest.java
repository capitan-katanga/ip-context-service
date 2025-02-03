package com.mercadolibre.ipcontext.service;

import com.mercadolibre.ipcontext.client.FixerApiClient;
import com.mercadolibre.ipcontext.client.GeographyApiClient;
import com.mercadolibre.ipcontext.client.IpApiClient;
import com.mercadolibre.ipcontext.dto.geographyapi.GeographyApiDto;
import com.mercadolibre.ipcontext.exception.IpAddressIsBannedException;
import com.mercadolibre.ipcontext.mapper.MapperCountryDto;
import com.mercadolibre.ipcontext.service.impl.IpContextServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mercadolibre.ipcontext.util.DataMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class IpContextServiceImplTest {

    @Mock
    private IpApiClient ipApiClient;
    @Mock
    private GeographyApiClient geographyApiClient;
    @Mock
    private FixerApiClient fixerApiClient;
    @Mock
    private IpBlacklistService ipBlacklistService;
    @Spy
    private MapperCountryDto mapperCountryDto = new MapperCountryDto();

    @InjectMocks
    private IpContextServiceImpl ipContextService;


    @Test
    void getCountryInformation() {
        var ipAddress = IP_ADDRESS;
        var ipApiResponseMock = ipApiDtoMock();
        var geoApiResponseMock = geographyApiDtoMock();
        var fixApiResponseMock = fixerApiDtoMock();

        when(ipApiClient.getIpApi(ipAddress))
                .thenReturn(ipApiResponseMock);
        when(geographyApiClient.getGeographyInfo(ipApiResponseMock.countryCode()))
                .thenReturn(geoApiResponseMock);
        when(fixerApiClient.getFixer(geoApiResponseMock.currencies().stream()
                .map(GeographyApiDto.Currency::code)
                .toList(), "USD"))
                .thenReturn(fixApiResponseMock);
        when(ipBlacklistService.ipAddressIsBaned(ipAddress))
                .thenReturn(false);

        var response = ipContextService.getCountryInformation(ipAddress);
        var responseExpected = mapperCountryDto.toCountryInformationDto(ipApiResponseMock, geoApiResponseMock, fixApiResponseMock);

        assertEquals(responseExpected, response);

    }

    @Test
    void getCountryInformationWithIpAddressBanned() {
        var ipAddress = IP_ADDRESS;
        when(ipBlacklistService.ipAddressIsBaned(ipAddress))
                .thenReturn(true);

        var exception = assertThrows(IpAddressIsBannedException.class, () ->
                ipContextService.getCountryInformation(ipAddress));

        assertEquals("The ip address: " + ipAddress + " is banned.", exception.getMessage());

    }


}