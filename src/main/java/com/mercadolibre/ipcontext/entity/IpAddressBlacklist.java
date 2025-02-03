package com.mercadolibre.ipcontext.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BLACKLISTS")
@Entity
public class IpAddressBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String ipAddress;
    @Column(nullable = false)
    private LocalDateTime banDateActivated;
}
