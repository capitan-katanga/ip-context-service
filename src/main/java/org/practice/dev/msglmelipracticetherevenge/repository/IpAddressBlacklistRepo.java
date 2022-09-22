package org.practice.dev.msglmelipracticetherevenge.repository;

import org.practice.dev.msglmelipracticetherevenge.model.IpAddressBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IpAddressBlacklistRepo extends JpaRepository<IpAddressBlacklist, Integer> {
    Optional<IpAddressBlacklist> findByIpAddressAndBanActivate(String ipAddress, Boolean banActivate);
}
