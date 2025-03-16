package com.mercadolibre.ipcontext.service;

import com.mercadolibre.ipcontext.dto.ipaddressblacklist.AddIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.GetIpBlacklistDto;

public interface IpBlacklistService {
    GetIpBlacklistDto banIpAddress(AddIpBlacklistDto addIpBlacklistDto);

    Boolean ipAddressIsBaned(String ipAddress);
}
