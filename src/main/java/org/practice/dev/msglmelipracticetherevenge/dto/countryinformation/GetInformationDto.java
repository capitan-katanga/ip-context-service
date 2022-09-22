package org.practice.dev.msglmelipracticetherevenge.dto.countryinformation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.practice.dev.msglmelipracticetherevenge.customvalidator.IpAddress;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetInformationDto {
    @IpAddress(message = "It is not a valid ip address")
    private String ipAddress;
}
