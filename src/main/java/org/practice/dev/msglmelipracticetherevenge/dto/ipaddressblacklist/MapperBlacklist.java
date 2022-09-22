package org.practice.dev.msglmelipracticetherevenge.dto.ipaddressblacklist;

import org.practice.dev.msglmelipracticetherevenge.model.IpAddressBlacklist;
import org.springframework.stereotype.Component;

@Component
public class MapperBlacklist {

    public GetIpBlacklistDto toGetIpBlacklistDto(IpAddressBlacklist ipAddressBlacklistModel) {
        return GetIpBlacklistDto.builder()
                .id(ipAddressBlacklistModel.getId())
                .ipAddress(ipAddressBlacklistModel.getIpAddress())
                .banDateActivated(ipAddressBlacklistModel.getBanDateActivated())
                .banDateDeactivate(ipAddressBlacklistModel.getBanDateDeactivate())
                .banActivate(ipAddressBlacklistModel.getBanActivate()).build();
    }
}
