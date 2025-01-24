package com.mercadolibre.ipcontext.repository;

import com.mercadolibre.ipcontext.model.IpAddressBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IpAddressBlacklistRepo extends JpaRepository<IpAddressBlacklist, Integer> {
    Optional<IpAddressBlacklist> findByIpAddressAndBanActivate(String ipAddress, Boolean banActivate);
}
