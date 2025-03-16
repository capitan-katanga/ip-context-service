package com.mercadolibre.ipcontext.dto.ipaddressblacklist;

import com.mercadolibre.ipcontext.validator.IpAddress;
import lombok.Builder;


@Builder
public record AddIpBlacklistDto(
        @IpAddress(message = "It is not a valid ip address") String ipAddress) {
}
