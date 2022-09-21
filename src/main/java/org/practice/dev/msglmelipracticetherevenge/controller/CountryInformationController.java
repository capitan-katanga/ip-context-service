package org.practice.dev.msglmelipracticetherevenge.controller;

import lombok.AllArgsConstructor;
import org.practice.dev.msglmelipracticetherevenge.dto.countryinformation.CountryInformationDto;
import org.practice.dev.msglmelipracticetherevenge.service.CountryInformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class CountryInformationController {

    private final CountryInformationService countryInformationService;

    @GetMapping("/getIpInfo/{ipAddress}")
    public ResponseEntity<CountryInformationDto> getIpInfo(@PathVariable("ipAddress") String ipAddress) {
        return new ResponseEntity<>(countryInformationService.getCountryInformation(ipAddress), HttpStatus.OK);
    }
}
