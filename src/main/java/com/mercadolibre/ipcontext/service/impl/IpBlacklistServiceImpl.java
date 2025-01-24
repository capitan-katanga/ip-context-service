package com.mercadolibre.ipcontext.service.impl;

import com.mercadolibre.ipcontext.service.IpBlacklistService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.GetIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.MapperBlacklist;
import com.mercadolibre.ipcontext.exception.IpAddressIsBannedException;
import com.mercadolibre.ipcontext.model.IpAddressBlacklist;
import com.mercadolibre.ipcontext.repository.IpAddressBlacklistRepo;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Log4j2
@Service
@AllArgsConstructor
public class IpBlacklistServiceImpl implements IpBlacklistService {
    private final IpAddressBlacklistRepo ipAddressBlacklistRepository;
    private final MapperBlacklist mapperBlacklist;

    @Override
    public GetIpBlacklistDto banIpAddress(String ipAddress) {
        if (ipAddressIsBaned(ipAddress)) {
            throw new IpAddressIsBannedException("The ip address: " + ipAddress + " is already banned.");
        }
        IpAddressBlacklist ipAddressBlacklistModel = IpAddressBlacklist.builder()
                .ipAddress(ipAddress)
                .banDateActivated(Timestamp.from(Instant.now()))
                .banActivate(Boolean.TRUE)
                .build();
        ipAddressBlacklistRepository.save(ipAddressBlacklistModel);
        return mapperBlacklist.toGetIpBlacklistDto(ipAddressBlacklistModel);

    }

    @Override
    public void unbanIpAddress(String ipAddress) {

    }

    @Override
    public Boolean ipAddressIsBaned(String ipAddress) {
        return ipAddressBlacklistRepository.findByIpAddressAndBanActivate(ipAddress, Boolean.TRUE).isPresent();
    }
}
