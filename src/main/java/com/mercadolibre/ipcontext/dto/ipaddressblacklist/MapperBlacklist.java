package com.mercadolibre.ipcontext.dto.ipaddressblacklist;

import com.mercadolibre.ipcontext.entity.IpAddressBlacklist;
import org.springframework.stereotype.Component;

@Component
public class MapperBlacklist {

    public GetIpBlacklistDto toGetIpBlacklistDto(IpAddressBlacklist ipAddressBlacklistModel) {
        return GetIpBlacklistDto.builder()
                .id(ipAddressBlacklistModel.getId())
                .ipAddress(ipAddressBlacklistModel.getIpAddress())
                .banDateActivated(ipAddressBlacklistModel.getBanDateActivated())
                .build();
    }
}
