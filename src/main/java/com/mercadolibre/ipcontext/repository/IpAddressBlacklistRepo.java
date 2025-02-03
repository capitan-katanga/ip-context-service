package com.mercadolibre.ipcontext.repository;

import com.mercadolibre.ipcontext.entity.IpAddressBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IpAddressBlacklistRepo extends JpaRepository<IpAddressBlacklist, Integer> {
    Optional<IpAddressBlacklist> findByIpAddress(String ipAddress);
}
