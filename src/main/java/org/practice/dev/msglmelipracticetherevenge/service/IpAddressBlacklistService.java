package org.practice.dev.msglmelipracticetherevenge.service;

import org.practice.dev.msglmelipracticetherevenge.dto.ipaddressblacklist.GetIpBlacklistDto;

public interface IpAddressBlacklistService {
    GetIpBlacklistDto banIpAddress(String ipAddress);
    void unbanIpAddress(String ipAddress);
    Boolean ipAddressIsBaned(String ipAddress);
}
