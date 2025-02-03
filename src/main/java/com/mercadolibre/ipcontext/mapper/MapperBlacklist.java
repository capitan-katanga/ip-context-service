package com.mercadolibre.ipcontext.mapper;

import com.mercadolibre.ipcontext.dto.ipaddressblacklist.AddIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.GetIpBlacklistDto;
import com.mercadolibre.ipcontext.entity.IpAddressBlacklist;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MapperBlacklist {

    public GetIpBlacklistDto toGetIpBlacklistDto(IpAddressBlacklist ipAddressBlacklistModel) {
        return GetIpBlacklistDto.builder()
                .id(ipAddressBlacklistModel.getId())
                .ipAddress(ipAddressBlacklistModel.getIpAddress())
                .banDateActivated(ipAddressBlacklistModel.getBanDateActivated())
                .build();
    }

    public IpAddressBlacklist toIpAddressBlacklist(AddIpBlacklistDto addIpBlacklistDto) {
        return IpAddressBlacklist.builder()
                .ipAddress(addIpBlacklistDto.ipAddress())
                .banDateActivated(LocalDateTime.now())
                .build();
    }
}
