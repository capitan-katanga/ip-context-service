package org.practice.dev.msglmelipracticetherevenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private Timestamp banDateActivated;
    private Timestamp banDateDeactivate;
    @Column(nullable = false)
    private Boolean banActivate;
}
