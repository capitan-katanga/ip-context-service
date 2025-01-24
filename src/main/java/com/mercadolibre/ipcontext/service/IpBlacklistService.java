package com.mercadolibre.ipcontext.service;

import com.mercadolibre.ipcontext.dto.ipaddressblacklist.GetIpBlacklistDto;

public interface IpBlacklistService {
    GetIpBlacklistDto banIpAddress(String ipAddress);
    void unbanIpAddress(String ipAddress);
    Boolean ipAddressIsBaned(String ipAddress);
}
