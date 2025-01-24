package com.mercadolibre.ipcontext.service;

import com.mercadolibre.ipcontext.dto.ipcontext.IpContextResponseDto;

public interface IpContextService {

    IpContextResponseDto getCountryInformation(String ipAddress);

}
