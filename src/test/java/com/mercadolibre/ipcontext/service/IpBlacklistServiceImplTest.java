package com.mercadolibre.ipcontext.service;

import com.mercadolibre.ipcontext.exception.IpAddressIsBannedException;
import com.mercadolibre.ipcontext.mapper.MapperBlacklist;
import com.mercadolibre.ipcontext.repository.IpAddressBlacklistRepo;
import com.mercadolibre.ipcontext.service.impl.IpBlacklistServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mercadolibre.ipcontext.util.DataMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class IpBlacklistServiceImplTest {

    @Mock
    private IpAddressBlacklistRepo ipAddressBlacklistRepo;
    @Spy
    private MapperBlacklist mapperBlacklist;
    @InjectMocks
    private IpBlacklistServiceImpl ipBlacklistService;

    @Test
    void banIpSuccess() {
        var ipAddressBlacklistMock = ipAddressBlacklistMock();
        var addIpBlacklistDtoMock = addIpBlacklistDtoMock();

        when(ipAddressBlacklistRepo.findByIpAddress(IP_ADDRESS))
                .thenReturn(Optional.empty());

        when(ipAddressBlacklistRepo.save(any()))
                .thenReturn(ipAddressBlacklistMock);

        var getIpBlacklistDtoExcepted = mapperBlacklist.toGetIpBlacklistDto(ipAddressBlacklistMock);
        var getIpBlacklistDto = ipBlacklistService.banIpAddress(addIpBlacklistDtoMock);

        assertEquals(getIpBlacklistDtoExcepted, getIpBlacklistDto);
    }

    @Test
    void banIpBaned() {
        var ipAddressBlacklistMock = ipAddressBlacklistMock();
        var addIpBlacklistDtoMock = addIpBlacklistDtoMock();

        when(ipAddressBlacklistRepo.findByIpAddress(IP_ADDRESS))
                .thenReturn(Optional.of(ipAddressBlacklistMock));

        var exception = assertThrows(IpAddressIsBannedException.class, () ->
                ipBlacklistService.banIpAddress(addIpBlacklistDtoMock));

        assertEquals("The ip address: " + IP_ADDRESS + " is already banned.",
                exception.getMessage());
    }

}
