package com.mercadolibre.ipcontext.dto.ipcontext;

import com.mercadolibre.ipcontext.customvalidator.IpAddress;
import lombok.Builder;


@Builder
public record IpContextRequestDto(@IpAddress(message = "It is not a valid ip address")
                                String ipAddress) {
}
