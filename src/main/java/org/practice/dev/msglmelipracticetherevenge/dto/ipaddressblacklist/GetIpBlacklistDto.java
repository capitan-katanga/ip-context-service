package org.practice.dev.msglmelipracticetherevenge.dto.ipaddressblacklist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetIpBlacklistDto {
    private Integer id;
    private String ipAddress;
    private Timestamp banDateActivated;
    private Timestamp banDateDeactivate;
    private Boolean banActivate;
}
