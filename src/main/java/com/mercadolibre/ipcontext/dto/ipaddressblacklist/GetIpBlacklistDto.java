package com.mercadolibre.ipcontext.dto.ipaddressblacklist;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetIpBlacklistDto(Integer id, String ipAddress, LocalDateTime banDateActivated) {
}
