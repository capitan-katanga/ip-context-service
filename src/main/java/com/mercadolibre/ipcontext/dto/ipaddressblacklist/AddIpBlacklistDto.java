package com.mercadolibre.ipcontext.dto.ipaddressblacklist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.mercadolibre.ipcontext.customvalidator.IpAddress;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddIpBlacklistDto {
    @IpAddress(message = "It is not a valid ip address")
    private String ipAddress;
}
