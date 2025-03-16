package com.mercadolibre.ipcontext.service.impl;

import com.mercadolibre.ipcontext.dto.ipaddressblacklist.AddIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.GetIpBlacklistDto;
import com.mercadolibre.ipcontext.mapper.MapperBlacklist;
import com.mercadolibre.ipcontext.exception.IpAddressIsBannedException;
import com.mercadolibre.ipcontext.repository.IpAddressBlacklistRepo;
import com.mercadolibre.ipcontext.service.IpBlacklistService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            log.info("The IP is already banned");
            throw new IpAddressIsBannedException("The ip address: " + ipAddress + " is already banned.");
        }
        log.info("IP is not banned");
        var ipBlackListEntity = mapperBlacklist.toIpAddressBlacklist(addIpBlacklistDto);
        var ipBlackListEntitySaved = ipAddressBlacklistRepository.save(ipBlackListEntity);
        return mapperBlacklist.toGetIpBlacklistDto(ipBlackListEntitySaved);
    }

    @Override
    public Boolean ipAddressIsBaned(String ipAddress) {
        log.info("Find if the IP is banned -> {}", ipAddress);
        return ipAddressBlacklistRepository.findByIpAddress(ipAddress).isPresent();
    }

}
