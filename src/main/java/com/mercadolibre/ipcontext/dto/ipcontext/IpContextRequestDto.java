package com.mercadolibre.ipcontext.dto.ipcontext;

import com.mercadolibre.ipcontext.validator.IpAddress;
import lombok.Builder;


@Builder
public record IpContextRequestDto(@IpAddress(message = "It is not a valid ip address")
                                String ipAddress) {
}
