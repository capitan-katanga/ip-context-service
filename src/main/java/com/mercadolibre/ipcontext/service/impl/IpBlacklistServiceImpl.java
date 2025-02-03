package com.mercadolibre.ipcontext.service.impl;

import com.mercadolibre.ipcontext.dto.ipaddressblacklist.AddIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.GetIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.MapperBlacklist;
import com.mercadolibre.ipcontext.entity.IpAddressBlacklist;
import com.mercadolibre.ipcontext.exception.IpAddressIsBannedException;
import com.mercadolibre.ipcontext.repository.IpAddressBlacklistRepo;
import com.mercadolibre.ipcontext.service.IpBlacklistService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Log4j2
@Service
public class IpBlacklistServiceImpl implements IpBlacklistService {

    private final IpAddressBlacklistRepo ipAddressBlacklistRepository;
    private final MapperBlacklist mapperBlacklist;

    @Autowired
    public IpBlacklistServiceImpl(IpAddressBlacklistRepo ipAddressBlacklistRepository, MapperBlacklist mapperBlacklist) {
        this.ipAddressBlacklistRepository = ipAddressBlacklistRepository;
        this.mapperBlacklist = mapperBlacklist;
    }

    @Override
    public GetIpBlacklistDto banIpAddress(AddIpBlacklistDto addIpBlacklistDto) {
        var ipAddress = addIpBlacklistDto.ipAddress();
        
        if (ipAddressIsBaned(ipAddress)) {
            throw new IpAddressIsBannedException("The ip address: " + ipAddress + " is already banned.");
        }

        var ipBlackListEntity = IpAddressBlacklist.builder()
                .ipAddress(ipAddress)
                .banDateActivated(LocalDateTime.now())
                .build();

        var ipBlackListEntitySaved = ipAddressBlacklistRepository.save(ipBlackListEntity);
        return mapperBlacklist.toGetIpBlacklistDto(ipBlackListEntitySaved);
    }

    @Override
    public Boolean ipAddressIsBaned(String ipAddress) {
        return ipAddressBlacklistRepository.findByIpAddress(ipAddress).isPresent();
    }

}
